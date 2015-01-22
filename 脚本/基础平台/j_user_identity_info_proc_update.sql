DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_user_identity_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_user_identity_info_proc_update`(
				          p_m_user_id VARCHAR(1000),
				          p_identity_ref VARCHAR(1000),
				          p_ref VARCHAR(1000),
				          p_user_id VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_user_identity_info set m_time=NOW()';
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	
	IF p_identity_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IDENTITY_Name='",p_identity_name,"'");
	END IF;
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID='",p_user_id,"'");
	END IF;
	
		SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND REF='",p_ref,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
