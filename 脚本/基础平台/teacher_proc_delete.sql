DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teacher_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teacher_proc_delete`(
				            p_teacher_id INT,
				            p_user_id varchar(50),
				            p_teacher_name VARCHAR(50),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	if p_teacher_id IS NOT NULL 
	or p_user_id IS NOT NULL 
	or p_teacher_name IS NOT NULL then
	
	SET affect_row = 0;
	SET tmp_sql="delete from teacher_info where 1=1";	
	
	IF p_teacher_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TEACHER_ID=",p_teacher_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and teacher_name='",p_teacher_name,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
	ELSE
		SET affect_row = 0;
	END IF;
	
END $$

DELIMITER ;
