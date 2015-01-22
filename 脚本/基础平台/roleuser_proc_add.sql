DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `roleuser_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `roleuser_proc_add`(
				            p_ref VARCHAR(50),
				            p_user_id VARCHAR(50),
				            p_role_id INT,
				             p_grade_id int,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_role_user (";
	
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_grade_id,",");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	else
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"UUID(),");
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
