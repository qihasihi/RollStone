DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `stu_view_mic_video_logs_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `stu_view_mic_video_logs_proc_update`(
				          p_ref INT,
				          p_user_id INT,
				          p_mic_video_id BIGINT,
				          p_task_id BIGINT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE stu_view_mic_video_logs set m_time=NOW()';
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	IF p_mic_video_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND MIC_VIDEO_ID=",p_mic_video_id);
	END IF;
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TASK_ID=",p_task_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
