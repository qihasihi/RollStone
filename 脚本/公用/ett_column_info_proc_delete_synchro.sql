DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `ett_column_info_proc_delete_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `ett_column_info_proc_delete_synchro`(
					p_ett_column_id INT,					
					p_status INT,
					p_roletype INT,
					OUT affect_row INT
							)
BEGIN
	
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(45000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(65000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	SET tmp_sql=CONCAT('DELETE FROM ett_column_info where 1=1 ');
	IF p_ett_column_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,' AND ett_column_id=',p_ett_column_id);
	END IF;
	IF p_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,' AND status=',p_status);
	END IF;
	IF p_roletype IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,' AND roletype=',p_roletype);
	END IF;
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET affect_row = 1;
	
END $$

DELIMITER ;
