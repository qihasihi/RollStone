DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `subject_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `subject_info_proc_delete`(
				            p_subject_id INT,
				            p_subject_type int,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(5000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from subject_info where 1=1";
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and SUBJECT_ID=",p_subject_id);
	END IF;
	
	if p_subject_type is not null then
		set tmp_sql=concat(tmp_sql," and subject_type=",p_subject_type);
	end if;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
