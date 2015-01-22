DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `student_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `student_info_proc_delete`(
				             p_ref VARCHAR(50),
				             p_stu_id INT,
				             p_user_id varchar(50),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from student_info where 1=1";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ref='",p_ref,"'");
	END IF;
	
	IF p_stu_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and stu_id=",p_stu_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and user_id='",p_user_id,"'");
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
