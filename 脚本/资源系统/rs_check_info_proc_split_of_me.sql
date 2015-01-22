DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_check_info_proc_split_of_me`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_check_info_proc_split_of_me`(
				          p_user_id VARCHAR(50),
				          p_value_id VARCHAR(1000),
				          p_ref VARCHAR(1000),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,IFNULL(tea.TEACHER_NAME,IFNULL(stu.STU_NAME,u.USER_NAME)) realname ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_check_info u INNER JOIN user_info u ON u.ref=u.USER_ID'; 
	
	SET tmp_tbl_name=CONCAT(tmp_tbl_name," LEFT JOIN teacher_info tea ON tea.USER_ID=u.REF ");
	SET tmp_tbl_name=CONCAT(tmp_tbl_name," LEFT JOIN student_info stu ON stu.USER_ID=u.REF ");
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID='",p_user_id,"'");
	END IF;
	
	
	IF p_value_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.VALUE_ID='",p_value_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
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
