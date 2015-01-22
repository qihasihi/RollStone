DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `synchro_log_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `synchro_log_info_proc_delete`(
				            p_ref INT,
				            p_error_msg VARCHAR(1000),
				            p_inter_type INT,
				            p_c_user_id VARCHAR(1000),
				            p_error_type INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from synchro_log_info where 1=1";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_error_msg IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ERROR_MSG='",p_error_msg,"'");
	END IF;
	
	IF p_inter_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and INTER_TYPE=",p_inter_type);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	IF p_error_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ERROR_TYPE=",p_error_type);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
