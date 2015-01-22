DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_site_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_site_proc_add`(p_siteids varchar(20000),
						p_activityid varchar(60),
						p_cuserid varchar(60),
						out affect_row int
						)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	declare idx int default '';
	declare total int default '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	if p_siteids is null or p_activityid is null then
		set affect_row = 0;
	end if;
	if p_cuserid is null then
		set affect_row = 0;
	end if;
	set total = get_split_string_total(p_siteids,'|');
	set idx = 0;
	set tmp_column_sql = concat("site_id,activity_id,c_time,c_user_id");
	while idx<total do
		set tmp_value_sql = concat(get_split_string(p_siteids,'|',idx+1),",'",p_activityid,"',NOW(),'",p_cuserid,"'");
		set tmp_sql = concat("insert into at_j_siteactivity_info(",tmp_column_sql,") values(",tmp_value_sql,")");
		set @tmp_sql = tmp_sql;
		prepare stmt from @tmp_sql;
		execute stmt;
		set affect_row = 1;
		set idx = idx+1;
	end while;
END $$

DELIMITER ;
