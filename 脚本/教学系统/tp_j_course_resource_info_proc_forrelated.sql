DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_resource_info_proc_forrelated`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_resource_info_proc_forrelated`(p_ref INT,
				          p_res_id BIGINT,
				          p_course_id VARCHAR(3000),
				          p_taskflag_course_id BIGINT,
					  p_resource_type INT,
					  p_resource_status INT,
					  p_user_type INT,
					  p_user_id	INT,
					  p_localstatus INT,
					  p_taskflag INT,
					  p_current_course_id BIGINT,
					  p_diff_type	 INT,
					  p_has_paper int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(500),
				          OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.ref,u.c_time ctime,u.operate_time,tc.course_name,tc.quote_id,u.course_id,u.resource_type ,r.*,ifnull(ifnull(t.teacher_name,s.stu_name),ui.user_name)real_name';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '  1=1';  
	DECLARE tmp_count_sql VARCHAR(2000) DEFAULT ' ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_j_course_resource_info u inner join rs_resource_info r on r.res_id=u.res_id
	left join user_info ui on r.user_id=ui.user_id
	inner join tp_course_info tc on tc.course_id=u.course_id 
	left join teacher_info t on t.user_id=ui.ref 
	left join student_info s on s.user_id=ui.ref
	 '; 
	 
	DECLARE idx INT DEFAULT '';
	DECLARE total INT DEFAULT '';
	IF p_taskflag_course_id IS NOT NULL THEN
		SET tmp_search_column=CONCAT(tmp_search_column,',(SELECT COUNT(*) FROM tp_task_allot_info ta,tp_task_info t WHERE ta.task_id=t.task_id AND t.course_id=',p_taskflag_course_id,' AND t.task_value_id=u.res_id)task_flag ');
 	END IF;
	
	IF p_current_course_id IS NOT NULL THEN
		SET tmp_search_column=CONCAT(tmp_search_column,",(SELECT COUNT(*) FROM tp_j_course_resource_info t WHERE t.course_id=",p_current_course_id," AND t.res_id=u.res_id)res_flag ");
	END IF;	 
	 
	IF p_course_id IS NOT NULL THEN
		SET total = get_split_string_total(p_course_id,'|');
		SET idx = 0;
		SET tmp_search_condition=CONCAT(tmp_search_condition," and(");
		WHILE idx<total DO
			SET tmp_search_condition=CONCAT(tmp_search_condition," u.COURSE_ID=",get_split_string(p_course_id,'|',idx+1));
			IF idx<total-1 THEN
				SET tmp_search_condition=CONCAT(tmp_search_condition," or ");
			END IF;
			SET idx = idx+1;
		END WHILE;
		SET tmp_search_condition=CONCAT(tmp_search_condition," )");
	END IF;
	
	IF p_diff_type>0  THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.diff_type=",p_diff_type);
	ELSEIF p_diff_type=0 THEN 
		SET tmp_search_condition=CONCAT(tmp_search_condition," and (r.diff_type=0 or r.diff_type is null)");	
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RES_ID=",p_res_id);
	END IF;
	
	IF p_resource_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.resource_type=",p_resource_type);
	END IF;
	
	IF p_resource_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.res_status=",p_resource_status);
	END IF;
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.user_type=",p_user_type);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.user_id=",p_user_id);
	END IF;
	
	IF p_localstatus IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.local_status=",p_localstatus);
	ELSE
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.local_status=1");
	END IF;
	
	IF p_taskflag IS NOT NULL AND p_taskflag_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and  not exists (SELECT 1 FROM tp_task_allot_info ta,tp_task_info t WHERE ta.task_id=t.task_id AND t.course_id=",p_taskflag_course_id," AND t.task_value_id=u.res_id) ");
	END IF;
	
	IF p_has_paper  IS NOT NULL THEN
		IF p_has_paper=0  THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and  not exists (SELECT 1 FROM j_mic_video_paper WHERE mic_video_id=u.res_id) ");
		ELSE 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and  exists (SELECT 1 FROM j_mic_video_paper WHERE mic_video_id=u.res_id) ");
		END IF;
	END IF;
	
	
	
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_sql=CONCAT("SELECT aa.*,
		IFNULL(d1.DICTIONARY_NAME,'--') RES_TYPE_NAME,
		IFNULL(d2.DICTIONARY_NAME,'--') FILE_TYPE_NAME FROM (",tmp_sql,") aa 
		LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=aa.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'  
		LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=aa.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE'");
	SET tmp_count_sql=tmp_sql;
		
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql," )t ");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
END $$

DELIMITER ;
