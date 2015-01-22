DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_info_proc_add`(
				            p_group_id bigint,
				            p_class_id INT,
				            p_class_type INT,
				            p_c_user_id int,
				            p_term_id VARCHAR(50),
				            p_group_name VARCHAR(100),
				            p_subject_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_group_info (";
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GROUP_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_group_id,",");
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_id,",");
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"subject_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_id,",");
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_type,",");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_c_user_id,",");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TERM_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_term_id,"',");
	END IF;
	
	IF p_group_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GROUP_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_group_name,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
