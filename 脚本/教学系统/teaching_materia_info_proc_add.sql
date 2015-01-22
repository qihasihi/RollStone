DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teaching_materia_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teaching_materia_info_proc_add`(
				            p_version_id INT,
				            p_material_name VARCHAR(1000),
				            p_remark VARCHAR(1000),
				            p_c_user_id VARCHAR(1000),
				            p_grade_id INT,
				            p_subject_id INT,
				            p_material_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO teaching_material_info (";
	
	IF p_version_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"VERSION_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_version_id,",");
	END IF;
	
	IF p_material_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MATERIAL_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_material_name,"',");
	END IF;
	
	IF p_remark IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REMARK,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_remark,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_grade_id,",");
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SUBJECT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_id,",");
	END IF;
	
	IF p_material_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MATERIAL_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_material_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
