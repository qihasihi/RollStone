DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `role_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `role_proc_add`(
				            p_role_name VARCHAR(1000),
				            p_flag int,
				            p_isadmin int,
				            p_remark varchar(500),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO role_info (";
	
	IF p_role_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ROLE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_role_name,"',");
	END IF;
	
	IF p_flag IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"flag,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_flag,",");
	END IF;
	
	IF p_isadmin IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"is_admin,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_isadmin,",");
	END IF;
	
	IF p_remark IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"remark,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_remark,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
