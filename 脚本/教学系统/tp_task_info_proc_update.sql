DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `tp_task_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_info_proc_update`(
				            p_task_id BIGINT,
				            p_task_name VARCHAR(1000),
				            p_task_value_id BIGINT,
				            p_task_type INT,
				            p_task_remark VARCHAR(1000),
				            p_c_user_id VARCHAR(1000),
				            p_course_id BIGINT,
				            p_cloud_status INT,
				            p_status INT,
				            p_order_idx INT,
				            p_ques_num	INT,
				             p_resource_type INT,
				            p_remote_type INT,
				            p_resource_name VARCHAR(1000),
				            p_paper_id BIGINT,
				            p_criteria INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_task_info set m_time=NOW()';
	
	IF p_task_remark IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_REMARK='",p_task_remark,"'");
	END IF;
	
	IF p_task_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_NAME='",p_task_name,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	
	IF p_criteria IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CRITERIA=",p_criteria);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_TYPE=",p_task_type);
	END IF;
	
	
	IF p_task_value_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_VALUE_ID=",p_task_value_id);
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STATUS=",p_status);
	END IF;
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",order_idx=",p_order_idx);
	END IF;
	
	IF p_ques_num IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ques_num=",p_ques_num);
	END IF;
	
	IF p_resource_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",resource_type=",p_resource_type);
		IF p_resource_type=1 THEN
			SET tmp_sql=CONCAT(tmp_sql,",remote_type=NULL");
		END IF;
	END IF;
	
	IF p_remote_type IS NOT NULL THEN
		IF p_resource_type=2 THEN
			SET tmp_sql=CONCAT(tmp_sql,",remote_type=",p_remote_type);
		END IF;		
	END IF;
	
	IF p_resource_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",resource_name='",p_resource_name,"'");
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",paper_id=",p_paper_id);
	END IF;
	
	
	
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID=",p_task_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
    END$$

DELIMITER ;