DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `term_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `term_info_proc_update`(
				         
				          p_ref VARCHAR(50),
				          p_term_name VARCHAR(1000),
				          p_year varchar(50),
				           p_semester_begin_date varchar(50),
				          p_semester_end_date varchar(50),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE term_info set m_time=NOW()';
	
	IF p_semester_begin_date IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SEMESTER_BEGIN_DATE=str_to_date('",p_semester_begin_date,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	
	IF p_semester_end_date IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SEMESTER_END_DATE=str_to_date('",p_semester_end_date,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	
	
	IF p_term_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TERM_NAME='",p_term_name,"'");
	END IF;
	
	IF p_year IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",YEAR='",p_year,"'");
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
