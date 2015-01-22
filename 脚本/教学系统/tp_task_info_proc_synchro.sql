DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_info_proc_synchro`(
				            p_task_id BIGINT,
				            p_task_value_id BIGINT,
				            p_task_type INT,
				            p_task_name VARCHAR(1000),
				            p_task_remark VARCHAR(1000),
				            p_course_id BIGINT,
				            p_c_user_id VARCHAR(1000),
				            p_cloud_status INT,
				            p_criteria	int,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_task_info where task_id=",p_task_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_sumCount >0 THEN
		CALL tp_task_info_proc_update(
				            p_task_id ,
				            p_task_name ,
				            p_task_value_id ,
				            p_task_type ,
				            p_task_remark ,
				            p_c_user_id ,
				            p_course_id ,
				            p_cloud_status,
				            1 ,		
				           affect_row 
				          );
	  ELSE
		CALL tp_task_info_proc_add(
				            p_task_id ,
				            p_task_value_id ,
				            p_task_type ,
				            p_task_name ,
				            p_task_remark ,
				            p_course_id ,
				            p_c_user_id ,
				            p_cloud_status ,
				            p_criteria	,
							 affect_row 
					);
	END IF;	
	
END $$

DELIMITER ;
