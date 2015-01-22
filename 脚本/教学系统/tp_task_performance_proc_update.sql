DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_performance_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_performance_proc_update`(
				            p_ref INT,
				            p_task_id bigint,
				            p_task_type INT,
				            p_course_id bigint,
				            p_group_id VARCHAR(50),
				            p_user_id VARCHAR(50),
				            p_is_right INT,
				            p_creteria_type int,
				            p_status INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_task_performance set c_time=now()';
	
	IF p_creteria_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CRETERIA_TYPE=",p_creteria_type);
	END IF;
	
	
	IF p_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STATUS=",p_status);
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GROUP_ID='",p_group_id,"'");
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_TYPE=",p_task_type);
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IS_RIGHT=",p_is_right);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID=",p_task_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
