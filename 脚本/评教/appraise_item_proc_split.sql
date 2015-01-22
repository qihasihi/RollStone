DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `appraise_item_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `appraise_item_proc_split`(
					  p_page_size INT(10),
					   p_current_page INT(10),
					  p_ref INT,
				          p_item_id INT,
				          p_name VARCHAR(1000),
				          p_year_id INT,
				          p_target_identity_type INT,
					  p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(50) DEFAULT 'pj_appraise_item_info u'; 
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_target_identity_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TARGET_IDENTITY_TYPE=",p_target_identity_type);
	END IF;
	
	IF p_item_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ITEM_ID=",p_item_id);
	END IF;
	
	IF p_year_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.YEAR_ID=",p_year_id);
	END IF;
	
	IF p_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.NAME='",p_name,"'");
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY  REF");
	
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
