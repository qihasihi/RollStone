DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dictionary_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dictionary_info_proc_add`(
				            p_ref VARCHAR(1000),
				            p_dictionary_name VARCHAR(1000),
				            p_dictionary_value VARCHAR(1000),
				            p_dictionary_type VARCHAR(1000),
				            p_dictionary_description VARCHAR(1000),
				              p_order_idx INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO dictionary_info (";
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ORDER_IDX,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_order_idx,",");
	END IF;
	
	
	IF p_dictionary_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DICTIONARY_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_dictionary_type,"',");
	END IF;
	
	IF p_dictionary_description IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DICTIONARY_DESCRIPTION,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_dictionary_description,"',");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	IF p_dictionary_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DICTIONARY_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_dictionary_name,"',");
	END IF;
	
	IF p_dictionary_value IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DICTIONARY_VALUE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_dictionary_value,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
