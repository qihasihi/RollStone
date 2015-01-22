DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `update_number_pro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `update_number_pro`(
						p_tbl_name VARCHAR(100),
						p_ref_name VARCHAR(100),
				                p_topic_id VARCHAR(100),
						p_columnsName VARCHAR(1000),	
						p_type INT,				
						OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(100000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET tmp_sql=CONCAT('UPDATE ',p_tbl_name,' SET ',p_columnsName);	
	IF p_type IS NULL OR p_type =1 THEN
	SET tmp_sql=CONCAT(tmp_sql,"=(CASE WHEN ",p_columnsName," IS NULL THEN 0 ELSE ",p_columnsName," END)+1 WHERE 1=1");
	END IF;
	IF p_type IS NOT NULL AND p_type=2 THEN
		SET tmp_sql=CONCAT(tmp_sql,"=(CASE WHEN ",p_columnsName," IS NULL THEN 0 ELSE ",p_columnsName," END)-1 WHERE 1=1");
	END IF;
	
	IF p_topic_id IS NOT NULL then
		SET tmp_sql=CONCAT(tmp_sql," AND ",p_ref_name,"='",p_topic_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;	
END $$

DELIMITER ;
