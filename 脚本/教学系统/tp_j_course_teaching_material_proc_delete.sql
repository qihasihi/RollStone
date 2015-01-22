DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_teaching_material_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_teaching_material_proc_delete`(
				            p_ref BIGINT,
				            p_course_id BIGINT,
				            p_teaching_material_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_j_course_teaching_material where 1=1";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	
	IF p_teaching_material_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TEACHING_MATERIAL_ID=",p_teaching_material_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
