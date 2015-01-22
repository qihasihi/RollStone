DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_taskinfo_proc_getWatch`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_taskinfo_proc_getWatch`(p_user_id int,p_video_id bigint)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET tmp_sql = CONCAT(tmp_sql,"select * from `stu_view_mic_video_logs` where user_id=",p_user_id," and mic_video_id=",p_video_id);
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
END $$

DELIMITER ;
