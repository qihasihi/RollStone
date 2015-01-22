DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activitysite_proc_ck`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activitysite_proc_ck`(p_siteid int)
BEGIN
	declare tmp_sql varchar(3000) default '';
	DECLARE tmp_current_sql varchar(1000) default '';
	DECLARE tmp_old_sql varchar(1000) default '';
	
	
	if p_siteid is not null then
		set tmp_current_sql =concat(tmp_current_sql,"SELECT DISTINCT ab.at_name
			FROM
			(SELECT a.REF,a.AT_NAME,a.BEGIN_TIME,a.END_TIME,aj.site_id
			FROM at_activity_info a LEFT JOIN at_j_siteactivity_info aj ON a.REF = aj.ACTIVITY_ID) aa LEFT JOIN
			(SELECT b.REF,b.AT_NAME,b.BEGIN_TIME,bj.SITE_ID
			FROM at_activity_info b LEFT JOIN at_j_siteactivity_info bj ON b.REF = bj.ACTIVITY_ID) ab
			ON aa.site_id = ab.site_id
			WHERE (ab.begin_time <=aa.end_time AND ab.begin_time >=aa.begin_time) 
			AND aa.ref<>ab.ref
			AND aa.site_id = ",p_siteid," AND aa.ref=a.ref	
			ORDER BY ab.begin_time DESC LIMIT 0,1");
			
		set tmp_old_sql = concat(tmp_old_sql,"SELECT DISTINCT ab.ref
			FROM
			(SELECT a.REF,a.AT_NAME,a.BEGIN_TIME,a.END_TIME,aj.site_id
			FROM at_activity_info a LEFT JOIN at_j_siteactivity_info aj ON a.REF = aj.ACTIVITY_ID) aa LEFT JOIN
			(SELECT b.REF,b.AT_NAME,b.BEGIN_TIME,bj.SITE_ID
			FROM at_activity_info b LEFT JOIN at_j_siteactivity_info bj ON b.REF = bj.ACTIVITY_ID) ab
			ON aa.site_id = ab.site_id
			WHERE (ab.begin_time <=aa.end_time AND ab.begin_time >=aa.begin_time) 
			AND aa.ref<>ab.ref
			AND aa.site_id = ",p_siteid," AND aa.ref=a.ref	
			ORDER BY ab.begin_time DESC LIMIT 0,1");
		
		set tmp_sql = concat("SELECT a.REF,a.AT_NAME,a.BEGIN_TIME,a.END_TIME,(",tmp_current_sql,") oldname,(",tmp_old_sql,") oldref FROM at_activity_info a left join at_j_siteactivity_info b on a.ref = b.activity_id where b.site_id = ",p_siteid);
		set @tmp_sql =tmp_sql;
		prepare stmt from @tmp_sql;
		execute stmt;
	end if;
END $$

DELIMITER ;
