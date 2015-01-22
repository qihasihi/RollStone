DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_question_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_question_info_proc_delete`(
				            p_question_id INT,
				            p_course_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_j_course_question_info where 1=1";
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUESTION_ID=",p_question_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
