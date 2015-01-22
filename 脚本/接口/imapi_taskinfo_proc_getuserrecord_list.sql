DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_taskinfo_proc_getuserrecord_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_taskinfo_proc_getuserrecord_list`(p_task_id BIGINT,p_class_id INT,p_is_vir INT,p_user_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql = CONCAT(tmp_sql,"select * from (SELECT DISTINCT stu.stu_name stuname,u.ett_user_id jid,tr.answer_content replydetail,tr.reply_attach replyattach,tr.reply_attach_type replyattachtype,TIMESTAMPDIFF(SECOND,tr.c_time,NOW()) replydate,tr.c_time
					FROM tp_ques_answer_record tr LEFT JOIN user_info u ON tr.user_id=u.ref left join student_info stu on u.ref=stu.user_id");
	IF p_is_vir = 0 THEN
		SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN j_class_user jc ON u.ref=jc.user_id");
	END IF;
	IF p_is_vir = 1 THEN
		SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN tp_j_virtual_class_student jv ON jv.user_id=u.user_id");
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," WHERE tr.task_id=",p_task_id);
	IF p_is_vir = 0 THEN
		SET tmp_sql = CONCAT(tmp_sql," AND jc.class_id=",p_class_id);
	END IF;
	IF p_is_vir = 1 THEN
		SET tmp_sql = CONCAT(tmp_sql," AND jv.class_id=",p_class_id);
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," ORDER BY ");
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," IF(u.user_id=",p_user_id,",1,u.user_id),");
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," tr.c_time");
	SET tmp_sql = CONCAT(tmp_sql,") a");
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
END $$

DELIMITER ;
