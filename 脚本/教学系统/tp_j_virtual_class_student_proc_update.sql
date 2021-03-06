DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_virtual_class_student_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_virtual_class_student_proc_update`(
				          p_ref INT,
				          p_virtual_class_id INT,
				          p_c_user_id VARCHAR(1000),
				          p_user_id VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_j_virtual_class_student set m_time=NOW()';
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF=",p_ref);
	END IF;
	
	IF p_virtual_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",VIRTUAL_CLASS_ID=",p_virtual_class_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID='",p_user_id,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
