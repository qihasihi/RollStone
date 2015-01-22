DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_teacher_course_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_teacher_course_info_proc_update`(
				          p_course_id varchar(50),
				          p_course_name VARCHAR(100),
				          p_term_id varchar(50),
				          p_available INT,
				          p_user_id varchar(50),
				          p_subject_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_teacher_course_info set m_time=NOW()';
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_NAME='",p_course_name,"'");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TERM_ID='",p_term_id,"'");
	END IF;
	
	IF p_available IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",AVAILABLE=",p_available);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",user_id='",p_user_id,"'");
	END IF;
	
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SUBJECT_ID=",p_subject_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID='",p_course_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
