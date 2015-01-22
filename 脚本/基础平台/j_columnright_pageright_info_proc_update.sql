DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_columnright_pageright_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_columnright_pageright_info_proc_update`(
				          p_m_user_id VARCHAR(1000),
				          p_column_id INT,
				          p_ref VARCHAR(1000),
				          p_page_right_id INT,
				          p_column_right_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_columnright_pageright_info set m_time=NOW()';
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_column_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COLUMN_ID=",p_column_id);
	END IF;
	
	
	
	IF p_page_right_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAGE_RIGHT_ID=",p_page_right_id);
	END IF;
	
	
	IF p_column_right_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COLUMN_RIGHT_ID=",p_column_right_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND REF='",p_ref,"'");
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
