DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dictionary_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dictionary_info_proc_delete`(
				            p_ref VARCHAR(50),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="delete from dictionary_info where 1=1";
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
