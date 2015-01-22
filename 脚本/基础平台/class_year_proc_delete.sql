DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_year_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_year_proc_delete`(
				            p_class_year_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="delete from class_year_info where 1=1";
	
	IF p_class_year_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and class_year_id=",p_class_year_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
