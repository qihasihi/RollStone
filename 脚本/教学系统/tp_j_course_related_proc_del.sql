DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_related_proc_del`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_related_proc_del`(p_courseid bigint,OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET affect_row = 0;
	IF p_courseid IS NOT NULL THEN
		set tmp_sql = concat(tmp_sql,"delete from tp_j_course_related_info");
		set tmp_sql = concat(tmp_sql," where course_id=",p_courseid);
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		SET affect_row = 1;
	END IF;
END $$

DELIMITER ;
