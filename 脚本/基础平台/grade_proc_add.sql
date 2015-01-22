DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `grade_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `grade_proc_add`(
				            p_grade_name VARCHAR(1000),
				            p_grade_value VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO grade_info (";
	
	
	IF p_grade_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_grade_name,"',");
	END IF;
	
	IF p_grade_value IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE_VALUE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_grade_value,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
