DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_ques_answer_record_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_ques_answer_record_proc_delete`(
					    p_ref INT,
					    p_ques_id bigint,
				            p_ques_parent_id BIGINT,
				            p_right_answer INT,
				            p_user_id varchar(50),
				            p_task_id BIGINT,
				            p_course_id BIGINT,
				            p_task_type INT, 
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_ques_answer_record where 1=1";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID=",p_task_id);
	END IF;
	
	IF p_ques_parent_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUES_PARENT_ID=",p_ques_parent_id);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUES_ID=",p_ques_id);
	END IF;
	
	
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_TYPE=",p_task_type);
	END IF;
	
	IF p_right_answer IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RIGHT_ANSWER=",p_right_answer);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
