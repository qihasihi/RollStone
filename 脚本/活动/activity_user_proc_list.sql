DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_user_proc_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_user_proc_list`(p_activityid varchar(60),p_attitude int)
BEGIN
	declare tmp_sql varchar(3000) default '';
	declare tmp_tblname varchar(1000) default '';
	declare tmp_condition varchar(1000) default '';
	declare tmp_column varchar(1000) default '';
	if p_activityid is not null then
		set tmp_tblname = concat(tmp_tblname," from at_j_activityuser_info at left join teacher_info t on at.user_id = t.user_id left join student_info s on at.user_id=s.user_id");
		set tmp_column = concat(tmp_column," at.user_id,ifnull(t.teacher_name,s.stu_name) realname");
		set tmp_condition = concat(tmp_condition," where at.activity_id='",p_activityid,"'");
		
	end if;
	if p_attitude is not null then
		set tmp_condition = concat(tmp_condition," and at.attitude = ",p_attitude);
	end if;
	SET tmp_sql = CONCAT("select",tmp_column,tmp_tblname,tmp_condition);
	set @tmp_sql = tmp_sql;
	prepare stmt from @tmp_sql;
	execute stmt;
END $$

DELIMITER ;
