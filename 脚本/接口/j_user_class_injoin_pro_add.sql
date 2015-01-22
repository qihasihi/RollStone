DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_user_class_injoin_pro_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `j_user_class_injoin_pro_add`(				          
				            p_user_id INT,
				            p_class_id INT,
				            p_dc_school_id INT,
				            p_allow_injoin INT,
				            p_operate_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_user_class_injoin (";
	
	IF p_user_id IS NOT NULL THEN	
		SET tmp_column_sql=CONCAT(tmp_column_sql,"user_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");	
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"class_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_id,",");
	END IF;
	
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dc_school_id,",");
	END IF;
	
	IF p_allow_injoin IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"allow_injoin,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_allow_injoin,",");
	END IF;
	IF p_operate_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"operate_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_operate_id,",");
	END IF;
	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;