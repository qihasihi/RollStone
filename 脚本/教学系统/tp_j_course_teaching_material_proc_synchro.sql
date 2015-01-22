DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_teaching_material_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_teaching_material_proc_synchro`(
				            p_ref BIGINT,
				            p_course_id BIGINT,
				            p_teaching_material_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	set affect_row=1;
	
	SET tmp_sql=CONCAT("SELECT COUNT(ref) INTO @tmp_ref FROM tp_j_course_teaching_material where teaching_material_id=",p_teaching_material_id," AND course_id=",p_course_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_ref IS NOT NULL AND @tmp_ref <1 THEN

		CALL tp_j_course_teaching_material_proc_add(
				            null,
				            p_course_id ,
				            p_teaching_material_id ,
							 affect_row );
	END IF;
	
END $$

DELIMITER ;
