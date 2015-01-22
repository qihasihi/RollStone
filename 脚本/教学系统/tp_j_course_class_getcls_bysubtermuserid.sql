DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_getcls_bysubtermuserid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_getcls_bysubtermuserid`(p_subject_id bigint,p_user_id INT,p_termid VARCHAR(100))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql=CONCAT('SELECT DISTINCT cls.class_grade,cls.year,cls.class_name from tp_j_course_class cc,class_info cls 
				WHERE cc.class_id=cls.class_id AND cc.class_type=1 AND cc.subject_id=',p_subject_id,' AND cc.term_id="',p_termid,'"
				AND cc.USER_ID=',p_user_id);	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
