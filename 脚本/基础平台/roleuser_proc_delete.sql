DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `roleuser_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `roleuser_proc_delete`(
				            p_ref VARCHAR(1000),
				            p_user_id VARCHAR(1000),
				            p_role_id INT,
				            p_grade_id int,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from j_role_user where 1=1";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ROLE_ID=",p_role_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
