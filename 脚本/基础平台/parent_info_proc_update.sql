DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `parent_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `parent_info_proc_update`(
				          p_parent_sex VARCHAR(1000),
				          p_cuser_name VARCHAR(1000),
				          p_user_id INT,
				          p_parent_phone VARCHAR(1000),
				          p_ref VARCHAR(1000),
				          p_student_id INT,
				          p_parent_name VARCHAR(1000),
				          p_parent_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE parent_info set m_time=NOW()';
	
	IF p_parent_sex IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PARENT_SEX='",p_parent_sex,"'");
	END IF;
	
	IF p_cuser_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CUSER_NAME='",p_cuser_name,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	
	IF p_parent_phone IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PARENT_PHONE='",p_parent_phone,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF='",p_ref,"'");
	END IF;
	
	IF p_student_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STUDENT_ID=",p_student_id);
	END IF;
	
	IF p_parent_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PARENT_NAME='",p_parent_name,"'");
	END IF;
	
	
	IF p_parent_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PARENT_ID=",p_parent_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
