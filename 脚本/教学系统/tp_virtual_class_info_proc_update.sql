DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_virtual_class_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_virtual_class_info_proc_update`(
					p_virtual_class_id INT,
				          p_virtual_class_name VARCHAR(1000),
				          p_c_user_id VARCHAR(1000),
				          p_status int,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_virtual_class_info set m_time=NOW()';
	
	IF p_virtual_class_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",VIRTUAL_CLASS_NAME='",p_virtual_class_name,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID=",p_c_user_id);
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STATUS=",p_status);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE VIRTUAL_CLASS_ID=",p_virtual_class_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
