DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_related_proc_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_related_proc_list`(p_courseid bigint)
BEGIN
	declare tmp_sql varchar(20000) default '';
	if p_courseid is not null then
		set tmp_sql = concat(tmp_sql,"SELECT *");
		set tmp_sql = concat(tmp_sql," FROM tp_course_info tc LEFT JOIN tp_j_course_related_info tj ON tc.COURSE_ID=tj.RELATED_COURSE_ID");
		set tmp_sql = concat(tmp_sql," WHERE tj.COURSE_ID=",p_courseid);
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
	end if;
END $$

DELIMITER ;
