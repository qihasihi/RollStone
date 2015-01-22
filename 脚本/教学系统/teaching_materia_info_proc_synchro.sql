DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teaching_materia_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teaching_materia_info_proc_synchro`(
				            p_version_id INT,
				            p_material_name VARCHAR(1000),
				            p_remark VARCHAR(1000),
				            p_c_user_id VARCHAR(1000),
				            p_grade_id INT,
				            p_subject_id INT,
				            p_material_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM teaching_material_info where material_id=",p_material_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_sumCount <1 THEN
		CALL teaching_materia_info_proc_add(
				            p_version_id,
				            p_material_name ,
				            p_remark ,
				            p_c_user_id ,
				            p_grade_id ,
				            p_subject_id ,
				            p_material_id ,
							 affect_row 
							);
	  ELSE
		CALL teaching_materia_info_proc_update(
				          p_version_id ,
				          p_material_name ,
				          p_remark ,
				          p_c_user_id ,
				          p_grade_id ,
				          p_subject_id ,
				          p_material_id ,
				           affect_row 
				          );
	END IF;
	
	
END $$

DELIMITER ;
