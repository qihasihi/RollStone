DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_proc_getclass`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_proc_getclass`(p_courseid bigint,p_teacherid int,p_type int)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql='SELECT c.CLASS_GRADE,c.CLASS_NAME,c.CLASS_ID
			FROM class_info c
			LEFT JOIN tp_j_course_class jc ON c.CLASS_ID=jc.class_id';
	IF p_type=2 THEN
		SET tmp_sql=CONCAT(tmp_sql," LEFT JOIN j_trusteeship_class jt ON jt.trust_class_id=jc.class_id");
	END IF;
	SET tmp_sql=CONCAT(tmp_sql," WHERE jc.course_id=",p_courseid);
	SET tmp_sql = CONCAT(tmp_sql," and jc.class_type=1");
	IF p_type=2 THEN
		SET tmp_sql=CONCAT(tmp_sql," and jt.trust_teacher_id=",p_teacherid);
	END IF;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
