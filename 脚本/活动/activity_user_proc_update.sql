DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_user_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_user_proc_update`(p_activityid varchar(60),p_userid varchar(50),p_attitude int,out affect_row int)
BEGIN
	declare tmp_sql varchar(1000) default 'update at_j_activityuser_info';
	
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row = 0;
	IF p_userid IS NULL THEN
		SET affect_row = 0;
	end if;
	set tmp_sql = concat(tmp_sql," set attitude = ",p_attitude," where activity_id='",p_activityid,"' and user_id='",p_userid,"'");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
