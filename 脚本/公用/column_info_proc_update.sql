DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `column_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `column_info_proc_update`(
				          p_m_user_id VARCHAR(1000),
				          p_column_name VARCHAR(1000),
				          p_column_id INT,
				          p_path VARCHAR(1000),
				          p_ref VARCHAR(1000),
				          p_styleclassid VARCHAR(100),
				          p_fnid VARCHAR(100),
				          p_remark VARCHAR(4000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE column_info set m_time=NOW()';
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_column_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COLUMN_NAME='",p_column_name,"'");
	END IF;
	
	IF p_column_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COLUMN_ID=",p_column_id);
	END IF;
	IF p_styleclassid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",styleclassid='",p_styleclassid,"'");
	END IF;
	IF p_fnid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",fn_id='",p_fnid,"'");
	END IF;
	IF p_remark IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",remark='",p_remark,"'");
	END IF;
	
	IF p_path IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PATH='",p_path,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF='",p_ref,"'");
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
