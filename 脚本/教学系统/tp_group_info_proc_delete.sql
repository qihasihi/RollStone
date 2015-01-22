DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_info_proc_delete`(
				            p_group_id bigint,
				            p_class_id INT,
				            p_class_type INT,
				            p_c_user_id int,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_group_info where 1=1";
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and GROUP_ID=",p_group_id);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_ID=",p_class_id);
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_TYPE=",p_class_type);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID=",p_c_user_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
