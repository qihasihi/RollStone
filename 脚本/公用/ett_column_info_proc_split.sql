DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `ett_column_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `ett_column_info_proc_split`(
					p_ref INT,
					p_ett_column_id INT,
					p_ett_column_name VARCHAR(100),
					p_status INT,
					p_roletype INT,
					p_school_id INT,
					p_current_page INT(10),
					p_page_size INT(10),							
				          OUT sumCount INT
							)
BEGIN	
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'ett_column_info u'; 
	DECLARE p_sort_column VARCHAR(2000) DEFAULT 'ETT_COLUMN_ID ASC';
	IF p_ett_column_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ETT_COLUMN_ID=",p_ett_column_id);
	END IF;
	
	
	IF p_ett_column_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ett_column_name='",p_ett_column_name,"'");
	END IF;
	IF p_roletype IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.roletype=",p_roletype);
	END IF;
	IF p_school_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.school_id=",p_school_id);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.status=",p_status);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
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
