DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_allot_gl_classid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_allot_gl_classid`(
					  p_task_id bigINT,
				          p_user_id INT
				          )
BEGIN
	
		SELECT class_id FROM j_class_user WHERE class_id IN (
		SELECT  user_type_id FROM tp_task_allot_info ta WHERE 
		user_type=0  AND task_id=p_task_id
		) AND user_id=(SELECT ref FROM user_info WHERE user_id=p_user_id)
		UNION  ALL 
		SELECT class_id FROM tp_j_group_student gs,tp_group_info g
		WHERE gs.group_id=g.group_id  AND gs.user_id=p_user_id
			AND gs.group_id IN (
				SELECT  user_type_id FROM tp_task_allot_info ta WHERE 
				user_type=2  AND task_id=p_task_id
			);
	
END $$

DELIMITER ;
