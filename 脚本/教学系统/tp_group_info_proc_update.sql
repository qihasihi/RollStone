DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_info_proc_update`(
				          p_group_id bigint,
				          p_class_id INT,
				          p_c_user_id int,
				          p_group_name VARCHAR(100),
				          p_subject_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET affect_row =0;
	
	SET tmp_sql ='UPDATE tp_group_info set m_time=NOW()';
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_ID=",p_class_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",subject_id=",p_subject_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID=",p_c_user_id);
	END IF;
	
	
	IF p_group_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GROUP_NAME='",p_group_name,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE GROUP_ID=",p_group_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
