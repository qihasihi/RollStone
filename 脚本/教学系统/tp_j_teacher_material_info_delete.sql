DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_teacher_material_info_delete`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_j_teacher_material_info_delete`(p_user_id INT,
							p_subject_id INT ,
							p_grade_id INT,
							p_term_id VARCHAR(100),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_user_id IS NOT NULL 
	   AND p_subject_id IS NOT NULL
	   AND p_grade_id IS NOT NULL
	   AND p_term_id IS NOT NULL THEN
	   SET tmp_sql = CONCAT(tmp_sql,"delete from tp_j_teacher_material_info where user_id=",p_user_id," and subject_id=",p_subject_id," and term_id='",p_term_id,"' and grade_id=",p_grade_id);
	   SET @tmp_sql = tmp_sql;
	   PREPARE stmt FROM @tmp_sql;
	   EXECUTE stmt;	
	   SET affect_row = 1;
	END IF;
	
    END$$

DELIMITER ;