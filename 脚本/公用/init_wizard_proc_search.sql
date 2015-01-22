DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `init_wizard_proc_search`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `init_wizard_proc_search`()
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET tmp_sql = 'select * from init_wizard_info';
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;	
	EXECUTE stmt;
END $$

DELIMITER ;
