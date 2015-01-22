DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_stu_post_detail`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_stu_post_detail`(p_topic_id BIGINT,p_class_id INT,p_is_vir INT,p_user_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql = CONCAT(tmp_sql,"SELECT DISTINCT theme.*,u.user_name,u.ett_user_id,TIMESTAMPDIFF(SECOND,theme.c_time,NOW()) replydate FROM tp_topic_theme_info theme left join user_info u on theme.c_user_id=u.user_id");
	IF p_is_vir =0 THEN
		SET tmp_sql = CONCAT(tmp_sql," left join j_class_user jc on u.ref=jc.user_id");
		SET tmp_sql = CONCAT(tmp_sql," AND jc.`CLASS_ID`=",p_class_id);
	END IF;
	IF p_is_vir = 1 THEN
		SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN tp_j_virtual_class_student jv ON jv.user_id=u.user_id");
		SET tmp_sql = CONCAT(tmp_sql," AND jv.class_id=",p_class_id);
	END IF;
	
	SET tmp_sql = CONCAT(tmp_sql," where theme.topic_id=",p_topic_id);
	
	IF p_is_vir =0 THEN
		SET tmp_sql = CONCAT(tmp_sql," AND jc.`CLASS_ID`=",p_class_id);
	END IF;
	IF p_is_vir = 1 THEN
		SET tmp_sql = CONCAT(tmp_sql," AND jv.class_id=",p_class_id);
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," ORDER BY ");
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," IF(u.user_id=",p_user_id,",1,u.user_id),");
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," theme.c_time desc");
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
END $$

DELIMITER ;
