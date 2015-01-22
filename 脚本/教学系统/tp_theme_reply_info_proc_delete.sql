DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_theme_reply_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_theme_reply_info_proc_delete`(
				            p_theme_id BIGINT,
				            p_user_id BIGINT,
				            p_reply_id BIGINT,
				            p_topic_id BIGINT ,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_theme_reply_info where 1=1";
	
	IF p_theme_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and THEME_ID=",p_theme_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	IF p_topic_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TOPIC_ID=",p_topic_id);
	END IF;
	
	IF p_reply_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REPLY_ID=",p_reply_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
