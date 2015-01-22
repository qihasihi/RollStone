DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_remote_dotask`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_remote_dotask`(p_courseid bigint,p_remotetype int)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql = CONCAT(tmp_sql,"");
	SET tmp_sql = CONCAT(tmp_sql,"SELECT DISTINCT task.*");
	SET tmp_sql = CONCAT(tmp_sql," FROM tp_task_info task RIGHT JOIN tp_task_allot_info allot ON task.TASK_ID=allot.task_id");
	SET tmp_sql = CONCAT(tmp_sql," WHERE task.TASK_TYPE=1");
	SET tmp_sql = CONCAT(tmp_sql," AND task.RESOURCE_type=2");
	IF p_courseid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," AND allot.course_id=",p_courseid);		
	END IF;
	IF p_remotetype IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," AND task.REMOTE_TYPE=",p_remotetype);		
	END IF;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
END $$

DELIMITER ;
