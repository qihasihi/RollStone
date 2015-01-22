DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `appraise_item_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `appraise_item_proc_add`(
				            p_item_id INT,
				            p_name VARCHAR(1000),
				            p_year_id INT,
				            p_is_title INT,
				            p_option_id INT,
				            p_target_identity_type INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO pj_appraise_item_info (";
	
	IF p_target_identity_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TARGET_IDENTITY_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_target_identity_type,",");
	END IF;
	
	
	IF p_option_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"OPTION_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_option_id,",");
	END IF;
	
	IF p_item_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ITEM_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_item_id,",");
	END IF;
	
	IF p_year_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"YEAR_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_year_id,",");
	END IF;
	
	
	IF p_is_title IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IS_TITLE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_title,",");
	END IF;
	
	IF p_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_name,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	
	SELECT @@IDENTITY INTO affect_row;	
	
END $$

DELIMITER ;
