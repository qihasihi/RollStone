DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_role_columnright_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_role_columnright_info_proc_add`(
				            p_m_user_id VARCHAR(1000),
				            p_role_id INT,
				            p_ref VARCHAR(1000),
				            p_column_right_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_role_columnright_info (";
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"M_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_m_user_id,"',");
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ROLE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_role_id,",");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	
	IF p_column_right_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COLUMN_RIGHT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_column_right_id,",");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
