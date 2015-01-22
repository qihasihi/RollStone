DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_by_user_sort`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_proc_by_user_sort`(
							p_user_name varchar(50),
							p_user_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
	DECLARE tmp_sql_count VARCHAR(2000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " r.USER_ID,r.USER_NAME,r.SCHOOL_NAME,COUNT(*) RESNUM";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 and r.RES_STATUS=1';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_resource_info r'; 
	
	IF p_user_name IS NOT NULL THEN	
	    SET tmp_search_condition=CONCAT(tmp_search_condition," AND r.USER_NAME LIKE '",p_user_name,"%'");
	END IF;
	IF p_user_id IS NOT NULL THEN	
	    SET tmp_search_condition=CONCAT(tmp_search_condition," AND r.user_id =",p_user_id);
	END IF;
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," GROUP BY r.USER_ID,r.USER_NAME,r.SCHOOL_NAME");
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY RESNUM DESC");
	
	SET tmp_sql_count=CONCAT("SELECT count(*) into @tmp_sumCount FROM (",tmp_sql,")aa");
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET @tmp_sql=tmp_sql_count;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
