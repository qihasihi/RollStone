DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_virtual_class_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_virtual_class_info_proc_add`(
				            p_virtual_class_name VARCHAR(1000),
				            p_c_user_id int,
				            p_dc_school_id int,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET affect_row =0;
	
	SET tmp_sql="INSERT INTO tp_virtual_class_info (";
	
	IF p_virtual_class_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"VIRTUAL_CLASS_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_virtual_class_name,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_c_user_id,",");
	END IF;
	IF p_dc_school_id>0 THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dc_school_id,",");
		
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
