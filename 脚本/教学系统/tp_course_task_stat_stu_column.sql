DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `tp_course_task_stat_stu_column`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_course_task_stat_stu_column`(p_course_id BIGINT,
		p_subject_id INT,
		p_class_id INT,
		p_sel_type INT,
		p_group_id BIGINT,
		OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	SET tmp_sql = CONCAT("SELECT DISTINCT ta.order_idx,ta.`TASK_ID`,ta.task_type
				FROM tp_task_info ta,tp_task_allot_info tll,tp_course_info tc,tp_j_course_class tj,term_info tm
				WHERE tc.`COURSE_ID`=tj.`course_id`
				AND tc.`COURSE_ID`=ta.`COURSE_ID`
				AND ta.`TASK_ID`=tll.`task_id`
				AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date");
					
				IF p_sel_type IS NULL THEN 	
					SET tmp_sql =CONCAT(tmp_sql," AND (tll.`user_type_id`=",p_class_id," OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=",p_class_id,"))");
				ELSEIF p_sel_type=0 THEN
					SET tmp_sql =CONCAT(tmp_sql," AND tll.`user_type_id`=",p_class_id,"  and tll.user_type=0 ");
				ELSEIF p_sel_type=2 THEN
					SET tmp_sql =CONCAT(tmp_sql," AND ( tll.`user_type_id`=",p_class_id," OR tll.`user_type_id`=",p_group_id,") ");
				END IF;
				SET tmp_sql = CONCAT(tmp_sql," AND tj.`subject_id`=",p_subject_id," AND tc.`COURSE_ID`=",p_course_id," ORDER BY ta.c_time");
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	SET sumCount =1;
    END$$

DELIMITER ;