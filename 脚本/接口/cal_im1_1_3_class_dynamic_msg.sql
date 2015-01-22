DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_im1_1_3_class_dynamic_msg`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `cal_im1_1_3_class_dynamic_msg`(
				        p_class_id VARCHAR(100),
				        p_user_id INT
				       )
BEGIN
		SELECT mu.*,t.template_name,t.template_searator,t.template_content,t.template_url,
			(SELECT ett_user_id FROM user_info WHERE ref=mu.user_ref) ettuserid,
			(CASE WHEN (SELECT COUNT(teacher_id) FROM teacher_info WHERE user_id=mu.user_ref)>0 THEN 2 ELSE 1 END) userType
		 FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
			AND class_id=p_class_id
			 AND u.`TEMPLATE_ID`=18 
			/* AND c_time >
			 IFNULL((SELECT MAX(c_time)FROM j_myinfo_user_info WHERE class_id=u.class_id
			AND template_id=19
			AND user_ref=(SELECT ref FROM user_info WHERE user_id=p_user_id))			 
			 ,"2000-01-01"
			 ) */
		 ) mu,myinfo_template_info t 
		 WHERE t.template_id=mu.TEMPLATE_ID
			ORDER BY mu.C_TIME DESC;
	
   
END$$

DELIMITER ;