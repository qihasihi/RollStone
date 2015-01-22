DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_taskinfo_proc_gettask`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_taskinfo_proc_gettask`(p_task_id BIGINT,p_class_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET tmp_sql = CONCAT(tmp_sql,"select * from (SELECT IFNULL((SELECT 1 FROM tp_task_allot_info al WHERE al.e_time<NOW() AND al.task_id=ta.`TASK_ID` AND al.user_type_id=",p_class_id,"),0) isover,");
	SET tmp_sql = CONCAT(tmp_sql,"ta.`im_task_content` taskcontent,ta.`IM_TASK_ATTACH` ATTACHS,ta.IM_TASK_ANALYSIS taskanalysis");
	SET tmp_sql = CONCAT(tmp_sql," FROM tp_task_info ta");
	SET tmp_sql = CONCAT(tmp_sql," WHERE ta.`TASK_ID`=",p_task_id,")a");
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
END $$

DELIMITER ;
