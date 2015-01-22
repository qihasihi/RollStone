DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_year_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_year_proc_add`(
				            p_class_year_name VARCHAR(1000),
				            p_class_year_value VARCHAR(1000),
				             p_b_time varchar(100),
				             p_e_time VARCHAR(100),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO class_year_info (";
	
	IF p_class_year_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_YEAR_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_class_year_name,"',");
	END IF;
	
	IF p_class_year_value IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_YEAR_VALUE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_class_year_value,"',");
	END IF;
	
	IF p_b_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"B_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_b_time,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	IF p_e_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"E_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_e_time,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
