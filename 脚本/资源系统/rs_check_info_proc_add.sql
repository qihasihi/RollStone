DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_check_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_check_info_proc_add`(
				            p_user_id varchar(50),
				            p_value_id VARCHAR(1000),
				            p_ref VARCHAR(1000),
				            p_operateuserid VARCHAR(50),
				          p_operaterealname VARCHAR(100),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO rs_check_info (";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	
	IF p_value_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"VALUE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_value_id,"',");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	IF p_operateuserid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"operateuserid,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_operateuserid,"',");
	END IF;
	
	IF p_operaterealname IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"operaterealname,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_operaterealname,"',");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
