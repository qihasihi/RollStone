DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi115_proc_getupdatecourse_info`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `imapi115_proc_getupdatecourse_info`(p_courseid BIGINT,p_classid INT)
BEGIN
	DECLARE tmp_sql VARCHAR(2000) DEFAULT 'SELECT tj.class_id classid,concat(c.class_grade,c.class_name) classname,tj.begin_time begintime,tj.end_time endtime 
						FROM tp_j_course_class tj,class_info c
						where 1=1 and tj.class_id=c.class_id';
	IF p_courseid IS NOT NULL THEN 
		SET tmp_sql = CONCAT(tmp_sql," and tj.course_id=",p_courseid);
	END IF;
	IF p_classid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," ORDER BY IF(tj.class_id=",p_classid,",0,tj.class_id)");
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
    END$$

DELIMITER ;