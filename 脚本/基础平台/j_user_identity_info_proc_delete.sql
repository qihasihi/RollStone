DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_user_identity_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_user_identity_info_proc_delete`(
				            p_m_user_id VARCHAR(1000),
				            p_identity_name VARCHAR(1000),
				            p_ref VARCHAR(1000),
				            p_user_id VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from j_user_identity_info where 1=1";
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	
	IF p_identity_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and IDENTITY_NAME='",p_identity_name,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
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
