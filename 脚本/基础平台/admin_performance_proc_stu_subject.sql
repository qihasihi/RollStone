DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `admin_performance_proc_stu_subject`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `admin_performance_proc_stu_subject`(p_school_id INT,p_grade_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql = CONCAT("SELECT s.subject_id,s.subject_name
						FROM tp_course_info tc,tp_j_course_class tj,tp_task_info ta,tp_task_allot_info tll,subject_info s
						WHERE tc.`COURSE_ID`=tj.`course_id`
						AND ta.`TASK_ID`=tll.`task_id`
						AND tc.`COURSE_ID`=ta.`COURSE_ID`
						AND tj.`subject_id`=s.subject_id
						AND (ta.`TASK_TYPE`=4 OR ta.`TASK_TYPE`=5)
						AND (tll.`user_type_id`=tj.`class_id` OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id = tj.`class_id`))
						AND tc.`dc_school_id`=",p_school_id,"
						AND tj.`grade_id`=",p_grade_id,"
						GROUP BY s.subject_id");
	END IF;
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
    END$$

DELIMITER ;