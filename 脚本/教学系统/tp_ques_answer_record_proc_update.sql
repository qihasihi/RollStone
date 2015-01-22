DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_ques_answer_record_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_ques_answer_record_proc_update`(
					p_ref INT,
					 p_ques_id BigINT,
					  p_ques_parent_id BIGINT,
					  p_answer_content VARCHAR(1000),
				          p_right_answer INT,
				          p_user_id varchar(50),
				          p_task_id bigINT,
				          p_course_id bigINT,
				          p_task_type INT,
				           p_group_id bigINT,
				            p_reply_attach VARCHAR(10000),
				            p_reply_attach_type INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_ques_answer_record set c_time=c_time';
	
	
	
	IF p_answer_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ANSWER_CONTENT='",p_answer_content,"'");
	END IF;
	IF p_reply_attach IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",reply_attach='",p_reply_attach,"'");
	END IF;
	
	 IF p_reply_attach_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",reply_attach_type=",p_reply_attach_type);
	END IF;
	
	
	
	IF p_ques_parent_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUES_PARENT_ID=",p_ques_parent_id);
	END IF;
	
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUES_ID=",p_ques_id);
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GROUP_ID=",p_group_id);
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_TYPE=",p_task_type);
	END IF;
	
	IF p_right_answer IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RIGHT_ANSWER=",p_right_answer);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID=",p_task_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
