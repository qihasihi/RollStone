DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teaching_material_get_subandgrad`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teaching_material_get_subandgrad`(p_in_resid VARCHAR(4000))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	
	SET tmp_sql = CONCAT("
SELECT DISTINCT tm.grade_id,tm.subject_id,cr.res_id,sub.SUBJECT_NAME,grd.GRADE_NAME FROM (
SELECT course_id,res_id FROM tp_j_course_resource_info  WHERE res_id IN (
",p_in_resid,"
)
) cr INNER JOIN tp_j_course_teaching_material jtm ON jtm.course_id=cr.course_id
      INNER JOIN teaching_material_info tm ON tm.material_id=jtm.teaching_material_id
      INNER JOIN subject_info sub ON sub.SUBJECT_ID=tm.subject_id
      INNER JOIN grade_info grd ON grd.GRADE_ID=tm.grade_id
	
	");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
END $$

DELIMITER ;
