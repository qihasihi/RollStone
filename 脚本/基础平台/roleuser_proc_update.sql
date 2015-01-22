DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `roleuser_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `roleuser_proc_update`(
				          
				          p_ref VARCHAR(1000),
				          p_user_id VARCHAR(1000),
				           p_role_id INT,
				          p_grade VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_role_user set m_time=NOW()';
	
	
	IF p_grade IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GRADE='",p_grade,"'");
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ROLE_ID=",p_role_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID='",p_user_id,"'");
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE REF='",p_ref,"'");  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
