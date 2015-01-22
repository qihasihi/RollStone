DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_group_student_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_group_student_proc_update`(
				          p_ref INT,
				          p_user_id int,
				          p_group_id BIGINT,
				          p_isleader INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET affect_row =0;
	
	SET tmp_sql ='UPDATE tp_j_group_student set m_time=NOW()';
	
	IF p_isleader IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ISLEADER=",p_isleader);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
		
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ref=",p_ref);
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and GROUP_ID=",p_group_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
