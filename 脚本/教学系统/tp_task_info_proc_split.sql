DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_task_info_proc_split`(
				          p_task_id BIGINT,
				          p_task_name VARCHAR(1000),
				          p_task_value_id BIGINT,
				          p_task_type INT,
				          p_c_user_id VARCHAR(1000),
				          p_course_id BIGINT,
				          p_cloud_status INT,
				          p_task_remark VARCHAR(1000),
				          p_ques_type	INT,
				          p_cloud_type INT,
				          p_select_type INT,
				          p_lonin_id	INT,
				          P_status	INT,
				          P_criteria	INT,
					p_current_page INT(10),
					p_page_size INT(10),
					p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,(
 SELECT SUM(
       CASE t.user_type 
      WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user cu WHERE cu.relation_type=''学生'' and cu.class_id=t.user_type_id)
      WHEN 1 THEN (SELECT COUNT(*) FROM tp_j_virtual_class_student vs WHERE vs.VIRTUAL_CLASS_ID=t.user_type_id)
      WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts WHERE ts.group_id=t.user_type_id) END)
 FROM tp_task_allot_info t WHERE t.task_id=u.task_id
 )total_count,(CASE u.task_type 
	WHEN 1 THEN (SELECT CONCAT(res_name,file_suffixname) FROM rs_resource_info r WHERE r.res_id=u.TASK_VALUE_ID)
	WHEN 2 THEN (SELECT topic_title FROM tp_topic_info t WHERE t.topic_id=u.TASK_VALUE_ID)
	WHEN 3 THEN (SELECT content FROM question_info q WHERE q.question_id=u.TASK_VALUE_ID) 
	WHEN 4 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=u.TASK_VALUE_ID)
	WHEN 5 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=u.TASK_VALUE_ID)
	WHEN 6 THEN (SELECT res_name FROM rs_resource_info r WHERE r.res_id=u.TASK_VALUE_ID)
	WHEN 7 THEN task_name
	WHEN 8 THEN task_name
	WHEN 9 THEN task_name
	WHEN 10 THEN task_name
	END
 )taskobjname,
 (select p.paper_name from paper_info p,tp_j_course_paper cp where p.paper_id=cp.paper_id and cp.course_id=u.course_id and p.paper_id=u.paper_id)paper_name,
 (SELECT MIN(b_time) FROM tp_task_allot_info ta WHERE ta.task_id=u.task_id)b_time,
 (SELECT MAX(e_time) FROM tp_task_allot_info ta WHERE ta.task_id=u.task_id)e_time,
  (SELECT 1 FROM tp_task_allot_info ta WHERE ta.task_id=u.task_id GROUP BY ta.task_id)flag,
  (select q.question_type from question_info q where q.question_id=u.task_value_id)question_type  ';  
	DECLARE tmp_search_condition VARCHAR(4000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_task_info u'; 
	
	IF p_select_type IS NULL OR  p_select_type<>2 THEN /*查询没有被操作人删除的*/
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND NOT EXISTS (SELECT t.ref FROM tp_operate_info t WHERE t.course_id=u.COURSE_ID AND t.target_id=u.TASK_ID and t.operate_type=1 ");		  
		IF p_lonin_id IS NOT NULL THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," AND c_user_id=",p_lonin_id);
		END IF;								
		SET tmp_search_condition=CONCAT(tmp_search_condition,")");
	END IF;
	
	/*查询自建任务是否已经被删除*/
	IF p_status IS NULL THEN
		IF p_select_type IS NOT NULL THEN
			IF p_select_type=1 THEN
				SET tmp_search_condition=CONCAT(tmp_search_condition,"  and u.STATUS<>2");
			
			END IF;
		END IF;
	END IF;
	
	
	IF p_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.STATUS=",p_status);
		IF p_select_type IS NOT NULL AND  p_select_type=2 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition,"  and not exists  (SELECT 1 FROM tp_task_allot_info ta WHERE ta.task_id=u.task_id) ");
		END IF;		
	END IF;
	
	
	IF p_task_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_NAME='",p_task_name,"'");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_ID=",p_task_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	IF p_task_remark IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_REMARK='",p_task_remark,"'");
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_cloud_type IS NOT NULL THEN
		IF p_cloud_type=1 THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS not in (3,4) ");
		END IF;
		
		IF p_cloud_type=-1 THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS in (3,4) ");
		END IF;
		
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_TYPE=",p_task_type);
	END IF;
	
	
	IF p_task_value_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_VALUE_ID=",p_task_value_id);
	END IF;
	
	IF P_criteria IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.criteria=",P_criteria);
	END IF;
	
	IF p_ques_type IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," INNER JOIN question_info q ON q.question_id=u.task_value_id ");
		SET tmp_search_condition=CONCAT(tmp_search_condition," and q.question_type=",p_ques_type);
	END IF;
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;