DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_site_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_site_proc_delete`(p_activityid varchar(60),OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	IF p_activityid IS NOT NULL THEN
		SET tmp_sql = CONCAT("delete from at_j_siteactivity_info where activity_id='",p_activityid,"'");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		
		SET affect_row = 1;
        ELSE
		SET affect_row = 0;
	END IF;
END $$

DELIMITER ;
