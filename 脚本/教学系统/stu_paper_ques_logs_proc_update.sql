DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `stu_paper_ques_logs_proc_update`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `stu_paper_ques_logs_proc_update`(
				          p_ref INT,
				          p_paper_id BIGINT,
				          p_ques_id BIGINT,
				          p_user_id INT,
				          p_score FLOAT,
				          p_answer VARCHAR(1000),
				          p_is_right INT,
				          p_is_marking INT,
				          p_AnnexName VARCHAR(5000),
				          p_task_id BIGINT,
				               p_attach_type INT,
				          p_mark_comment VARCHAR(500),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(100000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE stu_paper_ques_logs set c_time=c_time ';
	
	
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IS_RIGHT=",p_is_right);
	END IF;
	IF p_is_marking IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",is_marking=",p_is_marking);
	END IF;
	
	
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=",p_score);
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",task_id=",p_task_id);
	END IF;
	IF p_attach_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",attach_type=",p_attach_type);
	END IF;
	
	IF p_answer IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ANSWER='",p_answer,"'");
	END IF;
	IF p_AnnexName IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",AnnexName='",p_AnnexName,"'");
	END IF;
	IF p_mark_comment IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",mark_comment='",p_mark_comment,"'");
	END IF;
	
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PAPER_ID=",p_paper_id);
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUES_ID=",p_ques_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
    END$$

DELIMITER ;