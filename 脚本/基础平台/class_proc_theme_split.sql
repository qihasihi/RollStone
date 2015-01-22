DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_proc_theme_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_proc_theme_split`(
				         p_userref VARCHAR(100),
				         p_year VARCHAR(100),
				         p_pattern VARCHAR(100)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT 'select DISTINCT t.* from class_info t ';
	SET tmp_sql=CONCAT(tmp_sql,' where 1=1 ');
	IF p_userref IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND t.class_id in (select tu.class_id from j_class_user tu where tu.USER_ID='",p_userref,"') ");
	END IF;
	IF p_year IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND t.year='",p_year,"'");
	END IF;
	IF p_pattern IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND t.pattern='",p_pattern,"'");
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1; 
END $$

DELIMITER ;
