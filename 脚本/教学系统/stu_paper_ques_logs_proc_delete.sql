DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `stu_paper_ques_logs_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `stu_paper_ques_logs_proc_delete`(
				           
				            p_ref INT,
				            p_paper_id BIGINT,
				            p_user_id INT,
				            p_ques_id BIGINT,
				            p_task_id bigint,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from stu_paper_ques_logs where 1=1";
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PAPER_ID=",p_paper_id);
	END IF;
	
		
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID=",p_task_id);
	END IF;
	
	
	
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	
	IF p_answer IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ANSWER='",p_answer,"'");
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUES_ID=",p_ques_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
