DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `tp_live_task_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_live_task_proc_split`(
				          p_task_id BIGINT,
				          p_task_value_id BIGINT,
				          p_task_type INT,
				          p_c_user_id VARCHAR(1000),
				          p_course_id BIGINT,
				          p_school_id INT,
					p_current_page INT(10),
					p_page_size INT(10),
					p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_count_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(20000) DEFAULT '  DISTINCT u.*,tc.course_name,s.name,sub.subject_name,
(CASE u.task_type 
	WHEN 1 AND u.remote_type IS NOT NULL  THEN  resource_name
	WHEN 1 AND u.remote_type IS NULL THEN  (SELECT CONCAT(res_name,file_suffixname) FROM rs_resource_info r WHERE r.res_id=u.TASK_VALUE_ID)
	WHEN 2 THEN (SELECT topic_title FROM tp_topic_info t WHERE t.topic_id=u.TASK_VALUE_ID)
	WHEN 3 THEN (SELECT content FROM question_info q WHERE q.question_id=u.TASK_VALUE_ID) 
	WHEN 4 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=u.TASK_VALUE_ID)
	WHEN 5 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=u.TASK_VALUE_ID)
	WHEN 6 THEN (SELECT res_name FROM rs_resource_info r  WHERE r.res_id=u.TASK_VALUE_ID)
	WHEN 7 THEN task_name
	WHEN 8 THEN task_name
	WHEN 9 THEN task_name
	WHEN 10 THEN task_name
	END
 )taskobjname,
 (SELECT MIN(b_time) FROM tp_task_allot_info ta WHERE ta.task_id=u.task_id)b_time,
 (SELECT MAX(e_time) FROM tp_task_allot_info ta WHERE ta.task_id=u.task_id)e_time ';  
	DECLARE tmp_search_condition VARCHAR(4000) DEFAULT ' u.course_id=tc.course_id AND ta.`task_id`=u.`TASK_ID`  
	AND cc.course_id=tc.`COURSE_ID`
	AND sub.`SUBJECT_ID`=cc.`subject_id`
	AND s.`SCHOOL_ID`=tc.`dc_school_id` '; 
	DECLARE tmp_tbl_name VARCHAR(4000) DEFAULT ' tp_task_info u,tp_task_allot_info ta,tp_course_info tc,tp_j_course_class cc,school_info s,subject_info sub '; 
	
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.dc_school_id=",p_school_id);
	END IF;
	
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_ID=",p_task_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_TYPE=",p_task_type);
	END IF;
	
	
	IF p_task_value_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_VALUE_ID=",p_task_value_id);
	END IF;
	
	
	
	
	
	
	SET tmp_sql=CONCAT("  SELECT  ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql,") t");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;