DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_user_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_user_proc_delete`(p_activityid VARCHAR(60),
								  p_userid varchar(60),
								  OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	IF p_activityid IS NOT NULL THEN
		SET tmp_sql = CONCAT("delete from at_j_activityuser_info where activity_id='",p_activityid,"'");
		
		if p_userid is not null then
			set tmp_sql = concat(tmp_sql," and user_id='",p_userid,"'");
		end if;
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		
		SET affect_row = 1;
        ELSE
		SET affect_row = 0;
	END IF;
END $$

DELIMITER ;
