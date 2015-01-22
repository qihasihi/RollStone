DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dept_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dept_proc_search_split`(
				          p_dept_id INT,
				          p_parent_id INT,
				          p_dept_name VARCHAR(1000),
				          p_grade VARCHAR(1000),
				          p_subject_id INT,
				          p_study_periods VARCHAR(1000),
				          p_user_id INT,
				          p_type_id int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' d.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(50) DEFAULT 'dept_info d'; 
	
	IF p_dept_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.DEPT_ID=",p_dept_id);
	END IF;
	
	IF p_dept_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.DEPT_NAME='",p_dept_name,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.USER_ID=",p_user_id);
	END IF;
	
	
	IF p_grade IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.GRADE='",p_grade,"'");
	END IF;
	
	IF p_study_periods IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.STUDY_PERIODS='",p_study_periods,"'");
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_parent_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.PARENT_DEPT_ID=",p_parent_id);
	END IF;
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and d.TYPE_ID=",p_type_id);
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
