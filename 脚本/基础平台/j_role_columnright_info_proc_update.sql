DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_role_columnright_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_role_columnright_info_proc_update`(
				          p_m_user_id VARCHAR(1000),
				          p_role_id INT,
				          p_ref VARCHAR(1000),
				          p_column_right_id INT,
				          p_role_columnright_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_role_columnright_info set m_time=NOW()';
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ROLE_ID=",p_role_id);
	END IF;
	
	
	
	
	IF p_column_right_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COLUMN_RIGHT_ID=",p_column_right_id);
	END IF;
	
	IF p_role_columnright_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ROLE_COLUMNRIGHT_ID=",p_role_columnright_id);
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
