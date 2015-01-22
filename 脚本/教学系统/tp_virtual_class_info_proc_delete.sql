DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_virtual_class_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_virtual_class_info_proc_delete`(
				            p_virtual_class_name VARCHAR(1000),
				            p_virtual_class_id INT,
				            p_c_user_id VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_virtual_class_info where 1=1";
	
	IF p_virtual_class_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and VIRTUAL_CLASS_NAME='",p_virtual_class_name,"'");
	END IF;
	
	IF p_virtual_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and VIRTUAL_CLASS_ID=",p_virtual_class_id);
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
