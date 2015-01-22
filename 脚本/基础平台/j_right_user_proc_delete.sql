DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_right_user_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_right_user_proc_delete`(
				            p_user_id INT,
				            p_page_right_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from j_user_page_right where 1=1";
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	
	IF p_page_right_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and page_right_id=",p_page_right_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
END $$

DELIMITER ;
