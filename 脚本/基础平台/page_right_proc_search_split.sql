DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `page_right_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `page_right_proc_search_split`(
				          p_ref VARCHAR(1000),
				          p_page_right_id INT,
				          p_page_right_type int,
				          p_columnid	VARCHAR(100),
				          p_page_value VARCHAR(100),
				          p_pagename_value VARCHAR(1000),
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' p.*,col.column_name ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '  ';  
	DECLARE tmp_tbl_name VARCHAR(20000) DEFAULT 'page_right_info p'; 
	
	
	SET tmp_tbl_name="(SELECT p.* FROM page_right_info p WHERE 1=1 ";
	IF p_page_right_type IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and p.PAGE_RIGHT_TYPE=",p_page_right_type);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and p.REF='",p_ref,"'");
	END IF;
	IF p_columnid IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and p.column_id='",p_columnid,"'");
	END IF;	
	IF p_page_value IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and p.PAGE_VALUE='",p_page_value,"'");
	END IF;	
	  
	
	
	IF p_page_right_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and p.PAGE_RIGHT_ID=",p_page_right_id);
	END IF;
	
	IF p_pagename_value IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and (p.PAGE_NAME LIKE '%",p_pagename_value,"%'");
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," OR p.PAGE_VALUE LIKE '%",p_pagename_value,"%') ");
	END IF;
	set tmp_tbl_name=CONCAT(tmp_tbl_name," ) p,column_info col WHERE  p.column_id=col.column_id");
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name);	
	
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
