DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_getsub_byclstermid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_getsub_byclstermid`(p_class_id BIGINT,p_termid VARCHAR(100))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql=CONCAT('SELECT DISTINCT sub.subject_id,sub.subject_name,sub.lzx_subject_id,sub.subject_type from tp_j_course_class cc,subject_info sub 
				WHERE cc.subject_id=sub.subject_id AND cc.class_id=',p_class_id,' AND cc.term_id="',p_termid,'"');	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
