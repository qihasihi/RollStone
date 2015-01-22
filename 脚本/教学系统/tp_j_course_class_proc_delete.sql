DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_proc_delete`(
				            p_ref INT,
				            p_course_id BIGINT,
				            p_class_id INT,
				            p_class_type INT,
				            p_term_id varchar(50),
				            p_user_id INT,
				            p_grade_id INT,
				            p_subject_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_j_course_class where 1=1";
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_ID=",p_class_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TERM_ID='",p_term_id,"'");
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_TYPE=",p_class_type);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
