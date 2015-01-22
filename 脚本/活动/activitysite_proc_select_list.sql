DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activitysite_proc_select_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activitysite_proc_select_list`(OUT totalNum int)
BEGIN
	declare tmp_sql varchar(1000) default '';
	set tmp_sql = 'SELECT s.ref,s.site_name,s.site_contain,(
	SELECT ac.begin_time FROM at_activity_info ac,at_j_siteactivity_info sa WHERE sa.activity_id=ac.ref AND s.ref=sa.site_id 
	ORDER BY begin_time DESC LIMIT 0,1
) begin_time,(
	SELECT ac.end_time FROM at_activity_info ac,at_j_siteactivity_info sa WHERE sa.activity_id=ac.ref AND s.ref=sa.site_id 
	ORDER BY begin_time DESC LIMIT 0,1
) end_time FROM at_site_info s ';
	set @tmp_sql = tmp_sql;
	prepare stmt from @tmp_sql;	
	execute stmt;
	
	
	SET tmp_sql=' select count(*) into @totalcount from at_site_info';
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET totalNum = @totalcount;
END $$

DELIMITER ;
