DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `db_object_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `db_object_info_proc_delete`(
				            p_db_object_id INT,
				            p_c_user_id VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from db_object_info where 1=1";
	
	IF p_db_object_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and DB_OBJECT_ID=",p_db_object_id);
	END IF;
	
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
