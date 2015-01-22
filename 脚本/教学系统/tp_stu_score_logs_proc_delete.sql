DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_logs_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_logs_proc_delete`(
				            p_task_id BIGINT,
				            p_ref BIGINT,
				            p_user_id BIGINT,
				            p_class_id BIGINT,
				            p_jewel INT,
				            p_score INT,
				            p_course_id BIGINT,
				             p_dc_school_id BIGINT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_stu_score_logs where 1=1";
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID=",p_task_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_ID=",p_class_id);
	END IF;
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and dc_school_id=",p_dc_school_id);
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
