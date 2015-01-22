DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_year_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_year_proc_update`(
				          p_class_year_id INT,
				          p_class_year_name VARCHAR(1000),
				          p_class_year_value VARCHAR(1000),
				          p_b_time VARCHAR(50),
				          p_e_time VARCHAR(50),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE class_year_info set m_time=NOW()';
	
	
	IF p_class_year_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_YEAR_NAME='",p_class_year_name,"'");
	END IF;
	
	IF p_class_year_value IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_YEAR_VALUE='",p_class_year_value,"'");
	END IF;
	
	IF p_b_time IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",B_TIME=str_to_date('",p_b_time,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	IF p_e_time IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",E_TIME=str_to_date('",p_e_time,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	IF p_class_year_id IS NOT NULL THEN
		SET tmp_sql =CONCAT(tmp_sql, " WHERE class_year_id=",p_class_year_id);  
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
