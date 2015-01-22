DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_question_bank_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_question_bank_proc_update`(
				            p_question_id VARCHAR(50),
				            p_parent_ques_id VARCHAR(50),
				            p_ques_name VARCHAR(1000),
				            p_ques_type INT,
				            p_is_right VARCHAR(2),
				            p_ques_answer VARCHAR(4000),
				            p_m_user_id varchar(50),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_question_bank set m_time=NOW()';
	
	IF p_parent_ques_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PARENT_QUES_ID='",p_parent_ques_id,"'");
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IS_RIGHT='",p_is_right,"'");
	END IF;
	
	IF p_ques_answer IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUES_ANSWER='",p_ques_answer,"'");
	END IF;
	
	
	IF p_ques_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUES_TYPE=",p_ques_type);
	END IF;
	
	
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_ques_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUES_NAME='",p_ques_name,"'");
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUESTION_ID='",p_question_id,"'");
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
