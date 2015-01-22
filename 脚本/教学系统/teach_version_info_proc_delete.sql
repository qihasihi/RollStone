DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teach_version_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teach_version_info_proc_delete`(
				            p_version_name VARCHAR(1000),
				            p_version_id INT,
				            p_remark VARCHAR(1000),
				            p_c_user_id VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from teach_version_info where 1=1";
	
	IF p_version_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and VERSION_NAME='",p_version_name,"'");
	END IF;
	
	IF p_version_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and VERSION_ID=",p_version_id);
	END IF;
	
	IF p_remark IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REMARK='",p_remark,"'");
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
