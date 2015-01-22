DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `user_proc_delete`(
				            p_ref VARCHAR(1000),
				            p_user_id INT,
				            p_user_name VARCHAR(1000),
				            p_state_id int,
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from user_info where 1=1";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_NAME='",p_user_name,"'");
	END IF;
	
	IF p_state_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and STATE_ID=",p_state_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
