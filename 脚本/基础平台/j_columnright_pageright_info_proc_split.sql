DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_columnright_pageright_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_columnright_pageright_info_proc_split`(
				          p_m_user_id VARCHAR(1000),
				          p_column_id INT,
				          p_ref VARCHAR(1000),
				          p_page_right_id INT,
				          p_page_value VARCHAR(1000),
				          p_column_right_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'j_columnright_pageright_info u'; 
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_column_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COLUMN_ID=",p_column_id);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
	END IF;
	
	IF p_page_right_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAGE_RIGHT_ID=",p_page_right_id);
	END IF;
	IF p_page_value IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.page_right_id=(SELECT page_right_id FROM page_right_info where page_value='",p_page_value,"')");
	END IF;
	
	
	IF p_column_right_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COLUMN_RIGHT_ID=",p_column_right_id);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	set tmp_sql=CONCAT('SELECT t.*,pr.PAGE_NAME,pr.PAGE_VALUE,pr.REF prref,cr.columnright_name,cr.ref crref FROM (',tmp_sql,")");
	set tmp_sql=CONCAT(tmp_sql," t,columnright_info cr,page_right_info pr WHERE t.column_right_id=cr.columnright_id AND t.page_right_id=pr.PAGE_RIGHT_ID");
	
	
	SET @sql1=tmp_sql;   
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
