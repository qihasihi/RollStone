DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `role_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `role_proc_delete`(
				            p_role_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from role_info where 1=1";
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ROLE_ID=",p_role_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
