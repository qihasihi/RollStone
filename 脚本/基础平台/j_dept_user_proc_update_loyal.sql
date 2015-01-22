DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_dept_user_proc_update_loyal`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_dept_user_proc_update_loyal`(
				          p_dept_id INT,
				          p_role_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_user_dept_info set m_time=NOW()';
	
	
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ROLE_ID = NULL");
	END IF;
	
	
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	
	
	IF p_dept_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND DEPT_ID=",p_dept_id);
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND ROLE_ID=",p_role_id);
	END IF;
	
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
