DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_virtual_class_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_virtual_class_info_proc_split`(
				          p_virtual_class_id INT,
				          p_virtual_class_name VARCHAR(100),
				          p_c_user_id int,
				          p_status INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_virtual_class_info u'; 
	
	IF p_virtual_class_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.VIRTUAL_CLASS_NAME='",p_virtual_class_name,"'");
	END IF;
	
	IF p_virtual_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.VIRTUAL_CLASS_ID=",p_virtual_class_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID=",p_c_user_id);
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.STATUS=",p_status);
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
