DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_logs_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_logs_proc_update`(
				          p_task_id BIGINT,
				          p_ref BIGINT,
				          p_user_id BIGINT,
				          p_class_id BIGINT,
				          p_jewel INT,
				          p_score INT,
				          p_course_id BIGINT,
				          p_dc_school_id BIGINT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_stu_score_logs set c_time=c_time';
	
	
	IF p_jewel IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",JEWEL=",p_jewel);
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=",p_score);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND COURSE_ID=",p_course_id);
	END IF;
		IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND TASK_ID=",p_task_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND USER_ID=",p_user_id);
	END IF;
	
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND dc_school_id=",p_dc_school_id);
	END IF;
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND CLASS_ID=",p_class_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
