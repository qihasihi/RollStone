DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `term_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `term_info_proc_add`(
				            p_ref VARCHAR(50),
				            p_term_name VARCHAR(1000),
				            p_year varchar(50),
				            p_semester_begin_date VARCHAR(50),
				            p_semester_end_date VARCHAR(50),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row=1;
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO term_info (";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	else
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"UUID(),");
	END IF;
	
	IF p_term_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TERM_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_term_name,"',");
	END IF;
	
	IF p_year IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"YEAR,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_year,"',");
	END IF;
	
	IF p_semester_begin_date IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SEMESTER_BEGIN_DATE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_semester_begin_date,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	
	IF p_semester_end_date IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SEMESTER_END_DATE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_semester_end_date,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
