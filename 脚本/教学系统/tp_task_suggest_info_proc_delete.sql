DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_suggest_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_suggest_info_proc_delete`(
					     p_ref INT,
					     p_task_id varchar(50),
				             p_course_id varchar(50),
					     p_user_id varchar(50),
				             p_is_anonymous INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_task_suggest_info where 1=1";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_is_anonymous IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and IS_ANONYMOUS=",p_is_anonymous);
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID='",p_task_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID='",p_course_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
