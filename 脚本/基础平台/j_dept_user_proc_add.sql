DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_dept_user_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_dept_user_proc_add`(
				            p_ref VARCHAR(150),
				            p_dept_id INT,
				            p_role_id INT,
				            p_user_id VARCHAR(150),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_user_dept_info (";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	else
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"UUID(),");	
	END IF;
	
	IF p_dept_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DEPT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dept_id,",");
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ROLE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_role_id,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
