DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_proc_toupd`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_proc_toupd`(p_ref VARCHAR(60))
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	IF p_ref IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql,"select at.*,ifnull(t.teacher_name,s.stu_name) as username from at_activity_info at left join  teacher_info t on at.c_user_id = t.user_id left join student_info s on at.c_user_id=s.user_id where at.ref='",p_ref,"'");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
	END IF;
END $$

DELIMITER ;
