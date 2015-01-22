DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dept_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dept_proc_add`(
				            p_type_id INT,
				            p_dept_name VARCHAR(1000),
				            p_parent_id INT,
				            p_user_id INT,
				            p_grade VARCHAR(1000),
				            p_subject_id INT,
				            p_study_periods VARCHAR(1000),
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO dept_info (";
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TYPE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_type_id,",");
	END IF;
	
	IF p_dept_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DEPT_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_dept_name,"',");
	END IF;
	
	IF p_parent_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PARENT_DEPT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_parent_id,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	
	IF p_grade IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_grade,"',");
	END IF;
	
	IF p_study_periods IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STUDY_PERIODS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_study_periods,"',");
	END IF;
	
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SUBJECT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_id,",");
	END IF;
	
	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
