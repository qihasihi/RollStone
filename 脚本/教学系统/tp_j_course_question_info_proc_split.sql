DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_question_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_j_course_question_info_proc_split`(
					  p_ref BIGINT,	
					  p_userid INT,
				          p_question_id BIGINT,
				          p_course_id BIGINT,
				          p_ques_type INT,
				          p_ques_status INT,
				          p_localstatus	INT,
				          p_taskflag	INT,
				          p_course_name VARCHAR(100),
				          p_material_id INT,
				          p_grade_id INT,
				          p_subject_id INT,
				          p_paper_id BIGINT,
				          p_current_course_id BIGINT,
				          p_sel_date_type INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp__count_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' distinct u.ref,u.course_id,u.operate_time,tc.quote_id,q.*,(SELECT count(t.task_id) FROM tp_task_info t left join tp_task_allot_info al on t.task_id=al.task_id WHERE  e_time >now() and t.task_value_id=u.question_id AND t.course_id=u.course_id)flag ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' u.question_id=q.question_id and u.course_id=tc.course_id and  tc.course_id=tcm.course_id and tm.material_id=tcm.teaching_material_id and g.grade_id=tm.grade_id  ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_j_course_question_info u,question_info q,tp_course_info tc,tp_j_course_teaching_material tcm,teaching_material_info tm,grade_info g '; 
	
	
	IF p_paper_id IS NOT NULL AND p_current_course_id IS NOT NULL  THEN  
		SET tmp_search_column=CONCAT(tmp_search_column," ,(SELECT COUNT(*) FROM tp_j_course_paper cp,j_paper_question pq
	WHERE cp.course_id=",p_current_course_id," and cp.paper_id=pq.paper_id AND cp.paper_id=",p_paper_id," AND pq.question_id=u.question_id)paper_ques_flag");
	END IF;
	
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ref=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_ID=",p_question_id);
	END IF;
	
	IF p_ques_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and q.QUESTION_TYPE=",p_ques_type);
	END IF;
	
	IF p_ques_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and q.STATUS=",p_ques_status);
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.course_name like '%",p_course_name,"%'");
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_material_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tcm.teaching_material_id=",p_material_id);
	END IF;
	
	IF p_sel_date_type=1 AND  p_localstatus=2 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and  u.c_time > DATE_ADD(NOW(),INTERVAL -1 MONTH)  ");
	END IF;
	
	
	
	IF p_localstatus IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.LOCAL_STATUS=",p_localstatus);
	ELSE 
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.LOCAL_STATUS=1");
	END IF;
	
	IF p_taskflag IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and not exists (SELECT 1 FROM tp_task_allot_info ta,tp_task_info t WHERE e_time >now() and ta.task_id=t.task_id AND t.course_id=u.course_id AND t.task_value_id=u.question_id)");
	END IF;
	
	
	
	IF p_userid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND NOT EXISTS (SELECT target_id FROM tp_operate_info o WHERE o.course_id=u.course_id AND o.operate_type=3 AND o.c_user_id=",p_userid," and o.target_id =q.question_id)");
		
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET tmp__count_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp__count_sql,")t WHERE 1=1");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;