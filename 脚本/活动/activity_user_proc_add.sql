DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_user_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_user_proc_add`(p_userids VARCHAR(20000),
						p_activityid VARCHAR(60),
						p_cuserid VARCHAR(60),
						OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE idx INT DEFAULT '';
	DECLARE total INT DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	IF p_userids IS NULL OR p_activityid IS NULL THEN
		SET affect_row = 0;
	END IF;
	IF p_cuserid IS NULL THEN
		SET affect_row = 0;
	END IF;
	SET total = get_split_string_total(p_userids,'|');
	SET idx = 0;
	SET tmp_column_sql = CONCAT("user_id,activity_id,c_time,c_user_id");
	WHILE idx<total DO
		SET tmp_value_sql = CONCAT("'",get_split_string(p_userids,'|',idx+1),"','",p_activityid,"',NOW(),'",p_cuserid,"'");
		SET tmp_sql = CONCAT("insert into at_j_activityuser_info(",tmp_column_sql,") values(",tmp_value_sql,")");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		SET affect_row = 1;
		SET idx = idx+1;
	END WHILE;
END $$

DELIMITER ;
