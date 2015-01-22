DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_file_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_file_info_proc_split`(
				          p_file_size VARCHAR(1000),
				          p_ref VARCHAR(1000),
				          p_file_name VARCHAR(1000),
				          p_school_id INT,
				          p_res_id VARCHAR(1000),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_resource_file_info u'; 
	
	IF p_file_size IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.FILE_SIZE='",p_file_size,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
	END IF;
	
	IF p_file_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.FILE_NAME='",p_file_name,"'");
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SCHOOL_ID=",p_school_id);
	END IF;
	
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RES_ID='",p_res_id,"'");
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
