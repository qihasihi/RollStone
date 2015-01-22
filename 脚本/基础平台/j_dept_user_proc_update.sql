DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_dept_user_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_dept_user_proc_update`(
				          p_ref VARCHAR(150),
				          p_dept_id INT,
				          p_role_id INT,
				          p_user_id VARCHAR(150),
				          p_type_id int,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_user_dept_info set m_time=NOW()';
	
	IF p_dept_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DEPT_ID=",p_dept_id);
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ROLE_ID=",p_role_id);
	else
		SET tmp_sql=CONCAT(tmp_sql,",ROLE_ID=NULL");
	END IF;
	
	
	
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND REF='",p_ref,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND USER_ID='",p_user_id,"'");
	END IF;
	
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND dept_id in (select dept_id from dept_info where type_id=",p_type_id," )");
	END IF;
	
	IF p_dept_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND DEPT_ID=",p_dept_id);
	END IF;
	
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
