DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `parent_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `parent_proc_add`(
				            
				            p_ref VARCHAR(50),
				            p_parent_name VARCHAR(100),
				            p_parent_sex VARCHAR(10),
				            p_parent_phone VARCHAR(1000),
				            p_user_id VARCHAR(50),
				            p_cuser_name VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO parent_info (";
	
	IF p_parent_sex IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PARENT_SEX,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_parent_sex,"',");
	END IF;
	
	IF p_cuser_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CUSER_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_cuser_name,"',");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	
	IF p_parent_phone IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PARENT_PHONE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_parent_phone,"',");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	IF p_parent_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PARENT_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_parent_name,"',");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
