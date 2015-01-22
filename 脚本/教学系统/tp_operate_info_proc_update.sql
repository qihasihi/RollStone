DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_operate_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_operate_info_proc_update`(
				        p_operate_type INT,
				            p_course_id BIGINT,				           
				            p_ref BIGINT,
				            p_data_type INT,
				            p_c_user_id INT,
				            target_id BIGINT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_operate_info set m_time=NOW()';
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",OPERATE_TYPE=",p_operate_type);
	END IF;
	
	
	IF p_data_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DATA_TYPE=",p_data_type);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID=",target_id);
	END IF;
	
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_ID=",p_task_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	IF p_ref is not null then
		SET tmp_sql=CONCAT(tmp_sql," AND REF=",p_ref);
	end if;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
