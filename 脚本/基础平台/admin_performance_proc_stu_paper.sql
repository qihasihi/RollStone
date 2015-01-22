DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `admin_performance_proc_stu_paper`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `admin_performance_proc_stu_paper`(p_grade_id INT,p_subject_id INT,p_class_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(4000) DEFAULT '';
	SET tmp_sql = CONCAT("SELECT CONCAT(tc.`COURSE_NAME`,'任务',ta.`order_idx`) colname
				FROM tp_task_info ta,tp_task_allot_info tll,tp_task_performance per,tp_course_info tc,tp_j_course_class tj,term_info tm
				WHERE tc.`COURSE_ID`=tj.`course_id`
				AND tc.`COURSE_ID`=ta.`COURSE_ID`
				AND ta.`TASK_ID`=tll.`task_id`
				AND ta.task_id=per.task_id
				AND (ta.`TASK_TYPE`=4 OR ta.`TASK_TYPE`=5)
				AND tll.e_time<NOW()
				AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date
				AND per.c_time BETWEEN tm.semester_begin_date AND tm.semester_end_date 
				AND (tll.`user_type_id`=",p_class_id," OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=",p_class_id,"))
				AND tj.`grade_id`=",p_grade_id,"
				AND tj.`subject_id`=",p_subject_id," group by tc.course_id");
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
    END$$

DELIMITER ;