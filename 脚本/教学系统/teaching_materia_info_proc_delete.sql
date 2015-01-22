DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teaching_materia_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teaching_materia_info_proc_delete`(
				            p_version_id INT,
				            p_materia_name VARCHAR(1000),
				            p_remark VARCHAR(1000),
				            p_c_user_id VARCHAR(1000),
				            p_grade_id INT,
				            p_subject_id INT,
				            p_materia_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from teaching_materia_info where 1=1";
	
	IF p_version_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and VERSION_ID=",p_version_id);
	END IF;
	
	IF p_materia_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and MATERIA_NAME='",p_materia_name,"'");
	END IF;
	
	IF p_remark IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REMARK='",p_remark,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_materia_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and MATERIA_ID=",p_materia_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
