DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_myinfo_user_info_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `j_myinfo_user_info_proc_add`(
				            p_msg_name VARCHAR(1000),
				            p_operate_id VARCHAR(1000),
				            p_msg_id INT,
				            p_user_ref VARCHAR(1000),
				            p_template_id INT,
				            p_my_date VARCHAR(1000),
				            p_class_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_myinfo_user_info (";
	
	IF p_msg_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MSG_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_msg_name,"',");
	END IF;
	
	IF p_operate_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"OPERATE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_operate_id,"',");
	END IF;
	
	IF p_msg_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MSG_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_msg_id,",");
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_id,",");
	END IF;
	
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_ref,"',");
	END IF;
	
	
	IF p_template_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEMPLATE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_template_id,",");
	END IF;
	
	IF p_my_date IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MY_DATA,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_my_date,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;