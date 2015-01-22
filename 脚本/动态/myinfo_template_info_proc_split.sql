DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `myinfo_template_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `myinfo_template_info_proc_split`(
				          p_template_name VARCHAR(1000),
				          p_template_id INT,
				          p_template_searator VARCHAR(1000),
				          p_enable INT,
				          p_template_content VARCHAR(1000),
				          p_template_url VARCHAR(1000),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'myinfo_template_info u'; 
	
	
	IF p_template_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TEMPLATE_NAME='",p_template_name,"'");
	END IF;
	
	
	IF p_template_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TEMPLATE_ID=",p_template_id);
	END IF;
	
	IF p_template_searator IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TEMPLATE_SEARATOR='",p_template_searator,"'");
	END IF;
	
	IF p_enable IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ENABLE=",p_enable);
	END IF;
	
	IF p_template_content IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TEMPLATE_CONTENT='",p_template_content,"'");
	END IF;
	
	IF p_template_url IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TEMPLATE_URL='",p_template_url,"'");
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
