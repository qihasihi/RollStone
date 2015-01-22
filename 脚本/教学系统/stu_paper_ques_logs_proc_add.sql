DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `stu_paper_ques_logs_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `stu_paper_ques_logs_proc_add`(
				            p_paper_id BIGINT,
				            p_ques_id BIGINT,
				            p_user_id INT,
				            p_score FLOAT,
				            p_is_right INT,
				            p_answer VARCHAR(1000),
				            p_is_marking INT,
				            p_AnnexName VARCHAR(100),
				            p_task_id BIGINT,
				            p_attach_type INT,
				            p_mark_comment VARCHAR(500),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO stu_paper_ques_logs (";
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_paper_id,",");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_id,",");
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IS_RIGHT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_right,",");
	END IF;
	IF p_is_marking IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"is_marking,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_marking,",");
	END IF;
	
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score,",");
	END IF;
	
	IF p_answer IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ANSWER,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_answer,"',");
	END IF;
	IF p_AnnexName IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"AnnexName,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_AnnexName,"',");
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ques_id,",");
	END IF;
	IF p_attach_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"attach_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_attach_type,",");
	END IF;
	IF p_mark_comment IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"mark_comment,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_mark_comment,",");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;