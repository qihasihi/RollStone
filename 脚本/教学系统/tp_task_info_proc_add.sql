DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_info_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_task_info_proc_add`(
				            p_task_id BIGINT,
				            p_task_value_id BIGINT,
				            p_task_type INT,
				            p_task_name VARCHAR(1000),
				            p_task_remark VARCHAR(1000),
				            p_course_id BIGINT,
				            p_c_user_id VARCHAR(1000),
				            p_cloud_status INT,
				            p_criteria	INT,
				            p_order_idx INT,
				            p_ques_num	INT,
				            p_resource_type INT,
				            p_remote_type INT,
				            p_resource_name VARCHAR(1000),
				            p_im_task_attach VARCHAR(5000),
				            p_im_task_content VARCHAR(5000),
				            p_im_task_analysis VARCHAR(5000),
				            p_im_task_attachtype INT,
				            p_paper_id	 BIGINT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(50000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(100000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_task_info (";
	
	IF p_task_remark IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_REMARK,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_task_remark,"',");
	END IF;
	
	IF p_task_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_task_name,"',");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_id,",");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLOUD_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_cloud_status,",");
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_type,",");
	END IF;
	
	
	IF p_task_value_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_VALUE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_value_id,",");
	END IF;
	
	IF p_criteria IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"criteria,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_criteria,",");
	END IF;
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"order_idx,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_order_idx,",");
	END IF;
	
	IF p_ques_num IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ques_num,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ques_num,",");
	END IF;
	
	IF p_resource_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"resource_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_resource_type,",");
	END IF;
	
	IF p_remote_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"remote_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_remote_type,",");
	END IF;
	
	IF p_resource_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"resource_name,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_resource_name,"',");
	END IF;
	
	IF p_im_task_attach IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IM_TASK_ATTACH,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_im_task_attach,"',");
	END IF;
	
	IF p_im_task_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IM_TASK_CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_im_task_content,"',");
	END IF;
	
	IF p_im_task_analysis IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IM_TASK_ANALYSIS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_im_task_analysis,"',");
	END IF;
	
	IF p_im_task_attachtype IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IM_TASK_ATTACHTYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_im_task_attachtype,",");
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_paper_id,",");
	END IF;
	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;