DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `grade_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `grade_proc_delete`(
				            p_grade_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from grade_info where 1=1";
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and GRADE_ID=",p_grade_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
