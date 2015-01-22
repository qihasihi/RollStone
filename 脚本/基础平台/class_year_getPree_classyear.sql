DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_year_getPree_classyear`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_year_getPree_classyear`(
				          p_classyear varchar(50)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	set tmp_sql=CONCAT(tmp_sql,'
		SELECT * FROM class_year_info WHERE class_year_id<
		(SELECT class_year_id FROM class_year_info WHERE 1=1 ');
		
	SET tmp_sql=CONCAT(tmp_sql,' AND class_year_value="',p_classyear,'"');	
	
	set tmp_sql=CONCAT(tmp_sql,')
		ORDER BY class_year_name DESC
		 LIMIT 0,1
	');
	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
END $$

DELIMITER ;
