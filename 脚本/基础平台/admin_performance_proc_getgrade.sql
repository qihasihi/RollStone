DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `admin_performance_proc_getgrade`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `admin_performance_proc_getgrade`(p_school_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql = CONCAT("SELECT tj.`grade_id`,g.`GRADE_NAME`
						FROM class_info c,tp_j_course_class tj,tp_course_info tc,grade_info g
						WHERE c.class_id = tj.class_id
						AND tj.course_id = tc.course_id
						AND tj.`grade_id`=g.`GRADE_ID`
						AND tc.`LOCAL_STATUS`=1
						AND tc.`dc_school_id`=",p_school_id,"
						GROUP BY tj.`grade_id`");
	END IF;
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
    END$$

DELIMITER ;