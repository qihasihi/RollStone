DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_stu_task_remark_count`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `cal_stu_task_remark_count`(					
					p_user_id BIGINT
				          )
BEGIN
	
	SELECT c.`COURSE_ID`,c.course_name,
		(CASE ta.user_type WHEN 0 THEN ta.user_type_id
		  WHEN 2 THEN (SELECT DISTINCT class_id FROM tp_group_info WHERE group_id=ta.user_type_id)
		  ELSE 0
		  END) class_id
,COUNT(tk.`TASK_ID`)tkCount
	FROM tp_task_info tk,tp_course_info c
	,(SELECT DISTINCT task_id,ta.`user_type`,ta.`user_type_id` FROM tp_task_allot_info ta
		WHERE NOW() BETWEEN b_time AND e_time
		AND 
		(
			(
				 ta.`user_type`=0 AND ta.`user_type_id` IN (
					SELECT class_id FROM j_class_user cu
					WHERE cu.`USER_ID`=(SELECT ref FROM user_info WHERE user_id=p_user_id)
				)
			)
			OR
			(
				ta.user_type=2 AND ta.`user_type_id` IN (
					SELECT group_id FROM tp_j_group_student WHERE user_id=p_user_id
					 
				)
			)
		)
	)ta
	 WHERE tk.`COURSE_ID`=c.`COURSE_ID`  AND ta.task_id=tk.task_id 
	 AND NOT EXISTS(
		SELECT 1 FROM tp_task_performance tp WHERE tp.TASK_ID=tk.TASK_ID
			AND tp.CRETERIA_TYPE=tk.CRITERIA
			AND user_id=(SELECT ref FROM user_info WHERE user_id=p_user_id)
	 )
	GROUP BY tk.`COURSE_ID`;	
	
    END$$

DELIMITER ;