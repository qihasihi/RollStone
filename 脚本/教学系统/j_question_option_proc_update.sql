DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_question_option_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_question_option_proc_update`(
				             p_question_id bigINT,
				            p_score INT,
				            p_content VARCHAR(4000),
				            p_is_right INT,
				            p_option_type VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_question_option set m_time=NOW()';
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IS_RIGHT=",p_is_right);
	END IF;
	
	
	IF p_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CONTENT='",p_content,"'");
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=",p_score);
	END IF;
	
	IF p_option_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",OPTION_TYPE='",p_option_type,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUESTION_ID=",p_question_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
