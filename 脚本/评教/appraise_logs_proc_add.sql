DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `appraise_logs_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `appraise_logs_proc_add`(
				            p_user_id INT,
				            p_item_id INT,
				            p_option_id INT,
				            p_score FLOAT,
				            p_target_user_ref VARCHAR(50),
				            p_year_id INT,
				            p_target_class_user_ref VARCHAR(50),
				            p_class_id INT,
							OUT affect_row int
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO pj_appraise_logs_info (";
	
	IF p_target_user_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TARGET_USER_REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_target_user_ref,"',");
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score,",");
	END IF;	
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_id,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	IF p_target_class_user_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TARGET_CLASS_USER_REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_target_class_user_ref,"',");
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
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
