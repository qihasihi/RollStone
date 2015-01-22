DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_performance_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_performance_proc_split`(
				          p_ref INT,
				          p_task_id bigint,
				          p_user_id VARCHAR(50),
				          p_course_id bigint,
				          p_task_type INT,
				          p_group_id bigint,
				          p_is_right INT,
				          p_creteria_type int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_task_performance u'; 
	
	IF p_creteria_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CRETERIA_TYPE=",p_creteria_type);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_ID=",p_task_id);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.GROUP_ID=",p_group_id);
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_TYPE=",p_task_type);
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.IS_RIGHT=",p_is_right);
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
	
END $$

DELIMITER ;
