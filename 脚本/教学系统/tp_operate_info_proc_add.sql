DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_operate_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_operate_info_proc_add`(
					   p_operate_type INT,
				            p_course_id BIGINT,				           
				            p_ref BIGINT,
				            p_data_type INT,
				            p_c_user_id INT,
				            target_id BIGINT,
				            p_reference_id bigint,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_operate_info (";
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"OPERATE_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_operate_type,",");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ref,",");
	END IF;
	
	IF p_data_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DATA_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_data_type,",");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_c_user_id,",");
	END IF;
	
	
	IF target_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TARGET_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,target_id,",");
	END IF;
	
	IF p_reference_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REFERENCE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_reference_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
