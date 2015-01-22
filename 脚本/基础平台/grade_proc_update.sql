DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `grade_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `grade_proc_update`(
				          p_grade_id INT,
				          p_grade_name VARCHAR(1000),
				          p_grade_value VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE grade_info set m_time=NOW()';
	
	
	IF p_grade_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GRADE_NAME='",p_grade_name,"'");
	END IF;
	
	IF p_grade_value IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GRADE_VALUE='",p_grade_value,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE GRADE_ID=",p_grade_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
