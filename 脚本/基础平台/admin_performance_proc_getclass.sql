DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `admin_performance_proc_getclass`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `admin_performance_proc_getclass`(p_school_id INT,p_grade_id INT,p_subject_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql = CONCAT("SELECT tj.`class_id`,c.`CLASS_NAME`
						FROM class_info c,tp_j_course_class tj,tp_course_info tc
						WHERE c.class_id = tj.class_id
						AND tj.course_id = tc.course_id
						AND tc.`LOCAL_STATUS`=1
						AND tj.`grade_id`=",p_grade_id,"
						AND tj.`subject_id`=",p_subject_id,"
						AND tc.`dc_school_id`=",p_school_id,"
						GROUP BY tj.`class_id`");
	END IF;
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
    END$$

DELIMITER ;