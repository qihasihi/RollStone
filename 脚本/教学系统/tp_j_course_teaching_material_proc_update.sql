DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_teaching_material_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_teaching_material_proc_update`(
				          p_ref BIGINT,
				          p_teaching_material_id INT,
				          p_course_id BIGINT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_j_course_teaching_material set m_time=NOW()';	
	
	IF p_teaching_material_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TEACHING_MATERIAL_ID=",p_teaching_material_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	IF p_ref IS NOT NULL THEN
		set tmp_sql=CONCAT(tmp_sql," AND ref=",p_ref);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
