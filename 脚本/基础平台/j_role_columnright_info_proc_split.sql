DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_role_columnright_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_role_columnright_info_proc_split`(
				          p_m_user_id VARCHAR(1000),
				          p_role_id INT,
				          p_ref VARCHAR(1000),
				          p_column_right_id INT,
				          p_role_columnright_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,cr.columnright_name,c.column_id,c.column_name ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ''; 
	SET tmp_tbl_name="(SELECT u.* FROM j_role_columnright_info u WHERE 1=1 ";
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.ROLE_ID=",p_role_id);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.REF='",p_ref,"'");
	END IF;
	
	
	IF p_column_right_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.COLUMN_RIGHT_ID=",p_column_right_id);
	END IF;
	
	IF p_role_columnright_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.ROLE_COLUMNRIGHT_ID=",p_role_columnright_id);
	END IF;
	set tmp_tbl_name=CONCAT(tmp_tbl_name,")u,columnright_info cr,column_info c WHERE u.column_right_id=cr.columnright_id and c.column_id=cr.column_id");
	
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
