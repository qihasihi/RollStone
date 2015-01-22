DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_j_course_class_proc_add`(
				            p_course_id BIGINT,
				            p_class_id INT,
				            p_subject_id INT,
				            p_grade_id INT,
				            p_term_id VARCHAR(50),
				            p_begin_time VARCHAR(50),
				            p_end_time  VARCHAR(50),
				            p_class_type INT,
				            p_user_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(2000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(2000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(5000) DEFAULT '';
	
	SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_j_course_class (";
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_id,",");
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SUBJECT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_id,",");
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_grade_id,",");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TERM_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_term_id,"',");
	END IF;
	
	
	IF p_begin_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"BEGIN_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_begin_time,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	IF p_end_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"END_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_end_time,"','%Y-%m-%d %H:%i:%s'),");
	ELSE 
		SET tmp_column_sql=CONCAT(tmp_column_sql,"END_TIME,");
		SELECT DATE_ADD(p_begin_time,INTERVAL 7 DAY) INTO @end_time FROM DUAL 
		WHERE DATE_ADD(p_begin_time,INTERVAL 7 DAY)<(SELECT t.semester_end_date FROM term_info t WHERE NOW() BETWEEN t.semester_begin_date AND t.semester_end_date);
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",@end_time,"',");
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_type,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;