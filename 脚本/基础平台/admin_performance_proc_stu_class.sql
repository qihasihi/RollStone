DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `admin_performance_proc_stu_class`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `admin_performance_proc_stu_class`(p_school_id INT,p_grade_id INT,p_subject_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql = CONCAT("SELECT c.class_id,c.class_name
					FROM tp_course_info tc,tp_j_course_class tj,tp_task_info ta,tp_task_allot_info tll,class_info c
					WHERE tc.`COURSE_ID`=tj.`course_id`
					AND ta.`TASK_ID`=tll.`task_id`
					AND tc.`COURSE_ID`=ta.`COURSE_ID`
					AND tj.`class_id`=c.class_id
					AND (ta.`TASK_TYPE`=4 OR ta.`TASK_TYPE`=5)
					AND (tll.`user_type_id`=tj.`class_id` OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id = tj.`class_id`))
					AND tc.`dc_school_id`=",p_school_id,"
					AND tj.`grade_id`=",p_grade_id,"
					AND tj.`subject_id`=",p_subject_id,"
					GROUP BY c.class_id");
	END IF;
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
    END$$

DELIMITER ;