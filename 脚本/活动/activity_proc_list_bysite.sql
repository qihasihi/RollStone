DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_proc_list_bysite`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_proc_list_bysite`(p_siteid int)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT '';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' at_activity_info ac left join at_j_siteactivity_info j on ac.ref = j.activity_id';
	
	if p_siteid is not null then
		set tmp_search_col = concat(tmp_search_col," ac.at_name,ac.begin_time,ac.end_time ");
		set tmp_condition = concat(tmp_condition," and j.site_id = ",p_siteid);
	end if;
	set tmp_sql = concat("select",tmp_search_col," where ",tmp_condition," from ",tmp_tblname);
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END $$

DELIMITER ;
