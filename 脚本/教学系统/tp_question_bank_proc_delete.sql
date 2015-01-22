DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_question_bank_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_question_bank_proc_delete`(
				             p_question_id VARCHAR(50),	
				          p_parent_ques_id VARCHAR(50),
				          p_c_user_id VARCHAR(50),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_question_bank where 1=1";
	
	IF p_parent_ques_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PARENT_QUES_ID='",p_parent_ques_id,"'");
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUESTION_ID='",p_question_id,"'");
	END IF;
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
