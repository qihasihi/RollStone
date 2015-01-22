DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_suggest_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_suggest_info_proc_update`(
				          p_user_id INT,
				          p_is_anonymous INT,
				          p_task_id INT,
				          p_suggest_content VARCHAR(1000),
				          p_ref INT,
				          p_course_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_task_suggest_info set m_time=NOW()';
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	IF p_is_anonymous IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IS_ANONYMOUS=",p_is_anonymous);
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_ID=",p_task_id);
	END IF;
	
	
	IF p_suggest_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SUGGEST_CONTENT='",p_suggest_content,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
