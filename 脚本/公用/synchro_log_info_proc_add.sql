DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `synchro_log_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `synchro_log_info_proc_add`(
				            p_ref INT,
				            p_error_msg VARCHAR(1000),
				            p_inter_type INT,
				            p_c_user_id VARCHAR(1000),
				            p_error_type INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO synchro_log_info (";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ref,",");
	END IF;
	
	IF p_error_msg IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ERROR_MSG,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_error_msg,"',");
	END IF;
	
	IF p_inter_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"INTER_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_inter_type,",");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	
	IF p_error_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ERROR_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_error_type,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
