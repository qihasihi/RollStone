DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activitysite_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activitysite_proc_add`(p_site_name VARCHAR(1000),
								p_site_address VARCHAR(1000),
							        p_site_contain INT,
							        p_c_user_id VARCHAR(60),					       
				                                p_baseinfo VARCHAR(1000), 
				                                OUT affect_row INT )
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO at_site_info (";
	
	IF p_site_contain IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SITE_CONTAIN,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_site_contain,",");
	END IF;
	
	IF p_baseinfo IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"BASEINFO,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_baseinfo,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	
	
	IF p_site_address IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SITE_ADDRESS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_site_address,"',");
	END IF;
	
	IF p_site_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SITE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_site_name,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
END $$

DELIMITER ;
