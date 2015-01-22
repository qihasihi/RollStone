DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dept_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dept_proc_update`(
				          p_dept_id INT,
				          p_type_id INT,
				          p_dept_name VARCHAR(1000),
				          p_parent_id INT,
				          p_user_id INT,
				          p_grade VARCHAR(1000),
				          p_subject_id INT,
				          p_study_periods VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE dept_info set m_time=NOW()';
	
	
	
	IF p_dept_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DEPT_NAME='",p_dept_name,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	
	IF p_grade IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GRADE='",p_grade,"'");
	END IF;
	
	IF p_study_periods IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STUDY_PERIODS='",p_study_periods,"'");
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TYPE_ID=",p_type_id);
	END IF;
	
	IF p_parent_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PARENT_DEPT_ID=",p_parent_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	
	IF p_dept_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and DEPT_ID=",p_dept_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
