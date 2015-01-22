DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_site_proc_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_site_proc_list`(p_activityid varchar(60))
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET tmp_sql = 'select s.* from at_j_siteactivity_info j left join at_site_info s on j.site_id = s.ref';
	set tmp_sql = concat(tmp_sql," where j.activity_id='",p_activityid,"'");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;	
	EXECUTE stmt;
END $$

DELIMITER ;
