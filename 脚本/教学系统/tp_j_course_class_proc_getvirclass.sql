DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_proc_getvirclass`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_proc_getvirclass`(p_courseid bigint,p_teacherid INT,p_type INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql='SELECT c.VIRTUAL_CLASS_NAME,c.VIRTUAL_CLASS_ID,c.c_user_id
			FROM tp_virtual_class_info c
			LEFT JOIN tp_j_course_class jc ON c.virtual_class_id=jc.class_id';
	if p_type=2 then
		set tmp_sql=concat(tmp_sql," LEFT JOIN j_trusteeship_class jt ON jt.trust_class_id=jc.class_id");
	end if;
	SET tmp_sql=CONCAT(tmp_sql," WHERE jc.course_id=",p_courseid);
	SET tmp_sql = CONCAT(tmp_sql," and jc.class_type=2");
	IF p_type=2 THEN
		SET tmp_sql=CONCAT(tmp_sql," and jt.trust_teacher_id=",p_teacherid);
	END IF;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1; 
END $$

DELIMITER ;
