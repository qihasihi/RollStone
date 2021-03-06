DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_right_user_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_right_user_proc_update`(
				          p_ref INT,
				          p_user_id INT,
				          p_page_right_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_user_page_right set m_time=NOW()';
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	
	IF p_page_right_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAGE_RIGHT_ID=",p_page_right_id);
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
