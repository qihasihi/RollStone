DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dictionary_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dictionary_info_proc_update`(
				          p_ref VARCHAR(1000),
				          p_dictionary_name VARCHAR(1000),
				          p_dictionary_value VARCHAR(1000),
				          p_dictionary_type VARCHAR(1000),
				          p_dictionary_description VARCHAR(1000),
				          p_order_idx INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE dictionary_info set m_time=NOW()';
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ORDER_IDX=",p_order_idx);
	END IF;
	
	
	IF p_dictionary_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DICTIONARY_TYPE='",p_dictionary_type,"'");
	END IF; 
	
	IF p_dictionary_description IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DICTIONARY_DESCRIPTION='",p_dictionary_description,"'"); 
	END IF;
	
	IF p_dictionary_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DICTIONARY_NAME='",p_dictionary_name,"'");
	END IF;
	
	IF p_dictionary_value IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DICTIONARY_VALUE='",p_dictionary_value,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE REF='",p_ref,"'");  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
