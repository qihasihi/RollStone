DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_ques_answer_record_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_ques_answer_record_proc_add`(
				            p_ques_id BIGINT,
				            p_ques_parent_id BIGINT,
				            p_user_id VARCHAR(50),
				            p_task_id BIGINT,
				            p_course_id BIGINT,
				            p_group_id BIGINT,
				            p_task_type INT,
				            p_right_answer INT,
				            p_answer_content VARCHAR(1000),
				            p_to_userid	VARCHAR(50),
				            p_to_realname VARCHAR(100),
				            p_report_user_id VARCHAR(100),
				            p_reply_attach VARCHAR(10000),
				            p_reply_attach_type INT,
					    OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_ques_answer_record (";
	
	IF p_to_userid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TO_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_to_userid,"',");
	END IF;
	
	IF p_to_realname IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TO_REAL_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_to_realname,"',");
	END IF;
	
	IF p_report_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REPLY_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_report_user_id,"',");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	IF p_answer_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ANSWER_CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_answer_content,"',");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_id,",");
	END IF;
	
	IF p_ques_parent_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUES_PARENT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ques_parent_id,",");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ques_id,",");
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GROUP_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_group_id,",");
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_type,",");
	END IF;
	
	IF p_right_answer IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RIGHT_ANSWER,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_right_answer,",");
	END IF;
	
	IF p_reply_attach IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REPLY_ATTACH,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_reply_attach,"',");
	END IF;
	
	IF p_reply_attach_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REPLY_ATTACH_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_reply_attach_type,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
