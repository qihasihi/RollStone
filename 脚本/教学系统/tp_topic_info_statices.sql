DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_info_statices`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_topic_info_statices`(
				      p_cls_id BIGINT,
				      p_topic_id BIGINT,
				      p_clstypeid BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET tmp_sql=CONCAT('
		SELECT u.user_id,IF(s.STU_NO IS NULL,"--",s.STU_NO) STU_NO,IF(s.STU_NAME IS NULL,IF(t.TEACHER_NAME IS NULL,u.USER_NAME,t.TEACHER_NAME),s.STU_NAME) realname,
		(SELECT COUNT(theme_id) FROM tp_topic_theme_info th WHERE th.topic_id=',p_topic_id,' AND th.c_user_id=u.user_id) themecount,
		(SELECT COUNT(reply_id) FROM tp_theme_reply_info rt WHERE rt.topic_id=',p_topic_id,' AND rt.user_id=u.user_id) replycount
		FROM (');
		
		IF p_clstypeid=2 THEN
			SET tmp_sql=CONCAT(tmp_sql,'SELECT DISTINCT user_id FROM tp_j_virtual_class_student WHERE virtual_class_id=',p_cls_id);
		    ELSE    
			SET tmp_sql=CONCAT(tmp_sql,'SELECT DISTINCT u.user_id FROM j_class_user cu,user_info u WHERE u.ref=cu.user_id AND cu.class_id=',p_cls_id," AND relation_type='Ñ§Éú'");		    
		END IF;
		
		SET tmp_sql=CONCAT(tmp_sql,' UNION ALL 
			SELECT cuser_id FROM tp_course_info where course_id=(SELECT course_id from tp_topic_info where topic_id=',p_topic_id,')
		');
		
		SET tmp_sql=CONCAT(tmp_sql,'
		) tmp INNER JOIN user_info u ON u.user_id=tmp.user_id
		      LEFT JOIN teacher_info t ON t.user_id=u.ref
		      LEFT JOIN student_info s ON s.user_id=u.ref
		      WHERE u.STATE_ID=0
		      ');
		SET tmp_sql=CONCAT(tmp_sql,'ORDER BY IF(s.STU_NO IS NULL,2,1) ASC
	
		');
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
	
END $$

DELIMITER ;
