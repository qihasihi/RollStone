DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `role_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `role_proc_update`(
				          
				          p_role_id INT,
				          p_role_name VARCHAR(1000),
				          p_flag	int,
				          p_isadmin	int,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE role_info set m_time=NOW()';
	
	IF p_role_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ROLE_NAME='",p_role_name,"'");
	END IF;
	
	IF p_flag IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",flag=",p_flag);
	END IF;
	
	IF p_isadmin IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",is_admin=",p_isadmin);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ROLE_ID=",p_role_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
