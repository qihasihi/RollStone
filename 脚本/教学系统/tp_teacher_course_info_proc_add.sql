DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_teacher_course_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_teacher_course_info_proc_add`(
				            p_course_id varchar(50),
				            p_course_name VARCHAR(1000),
				            p_term_id varchar(50),
				            p_available INT,
				            p_user_id varchar(50),
				            p_subject_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_teacher_course_info (";
	
	if p_course_id is not null then
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_course_id,"',");
	else
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"UUID(),");
	end if;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_course_name,"',");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TERM_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_term_id,"',");
	END IF;
	
	IF p_available IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"AVAILABLE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_available,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"user_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SUBJECT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
