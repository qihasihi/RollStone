DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `identity_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `identity_info_proc_split`(
				          p_m_user_id VARCHAR(1000),
				          p_role_id INT,
				          p_identity_name VARCHAR(1000),
				          p_ref VARCHAR(1000),
				          p_isadmin int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,r.role_name,r.is_admin ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'identity_info u '; 
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ROLE_ID=",p_role_id);
	END IF;
	
	
	IF p_identity_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.IDENTITY_NAME='",p_identity_name,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
	END IF;
	
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM (SELECT u.* FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
        else 
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY CASE role_id WHEN 6 THEN 1 WHEN 5 THEN 2 WHEN 11 THEN 3 WHEN 12 THEN 4 WHEN 9 THEN 5 WHEN 10 THEN 6
		WHEN 13 THEN 7 WHEN 14 THEN 8 WHEN 17 THEN 9 WHEN 18 THEN 10 END ");			
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	set tmp_sql=CONCAT(tmp_sql,") u,role_info r WHERE u.role_id=r.role_id ");
	
	IF p_isadmin IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and r.is_admin=",p_isadmin);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name,",role_info r WHERE ",tmp_search_condition," AND u.role_id=r.role_id");
	IF p_isadmin IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and r.is_admin=",p_isadmin);
	END IF;
	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
