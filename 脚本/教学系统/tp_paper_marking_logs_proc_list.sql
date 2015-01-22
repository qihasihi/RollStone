DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_paper_marking_logs_proc_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_paper_marking_logs_proc_list`(p_paperid BIGINT,p_quesid BIGINT,p_classid BIGINT,p_task_id BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql = CONCAT(tmp_sql,"");
	SET tmp_sql = CONCAT(tmp_sql,"SELECT sp.*,s.stu_name");
	SET tmp_sql = CONCAT(tmp_sql," FROM stu_paper_ques_logs sp LEFT JOIN user_info u ON sp.user_id=u.user_id");
	SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN student_info s ON u.ref=s.user_id WHERE 1=1");
	IF p_paperid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," AND  sp.paper_id=",p_paperid);
	END IF;
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," AND  sp.task_id=",p_task_id);
	END IF;
	IF p_quesid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," AND sp.ques_id=",p_quesid);
	END IF;
	IF p_classid IS NOT NULL THEN
	   SET tmp_sql=CONCAT(tmp_sql," AND
		sp.user_id IN (
			SELECT u.user_id FROM j_class_user cu,user_info u WHERE u.ref=cu.`USER_ID` AND cu.class_id=",p_classid," AND cu.`RELATION_TYPE`='Ñ§Éú'
		)	   
	   ");
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
END $$

DELIMITER ;
