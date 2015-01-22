DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `question_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `question_info_proc_add`(
					p_question_id BIGINT,
					p_question_type INT,
					p_paper_type_id INT,
					p_question_level INT,
					p_status INT,
					p_cloud_status INT,
					p_content VARCHAR(30000),
					p_analysis VARCHAR(30000),
					p_exam_type INT,
					p_exam_subject_type INT,
					p_exam_year VARCHAR(1000),
					p_axam_area VARCHAR(1000),
        				p_province VARCHAR(1000),
				        p_city VARCHAR(1000),
				        p_grade VARCHAR(1000),
				        p_use_count INT,
				        p_c_user_id VARCHAR(1000),
					p_c_user_name VARCHAR(1000),
					p_correct_answer VARCHAR(1000),
					p_extension INT,
					OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(45000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(65000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO question_info (";
	
	IF p_c_user_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_name,"',");
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUESTION_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_question_id,",");
	END IF;
	IF p_extension IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"extension,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_extension,",");
	END IF;
	
	IF p_exam_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"EXAM_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_exam_type,",");
	END IF;
	
	
	IF p_city IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CITY,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_city,"',");
	END IF;
	
	IF p_paper_type_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_TYPE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_paper_type_id,",");
	END IF;
	
	IF p_province IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PROVINCE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_province,"',");
	END IF;
	
	IF p_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_content,"',");
	END IF;
	
	IF p_exam_subject_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"EXAM_SUBJECT_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_exam_subject_type,",");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	IF p_exam_year IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"EXAM_YEAR,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_exam_year,"',");
	END IF;
	
	IF p_use_count IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USE_COUNT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_use_count,",");
	END IF;
	
	IF p_question_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUESTION_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_question_type,",");
	END IF;
	
	IF p_grade IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_grade,"',");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLOUD_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_cloud_status,",");
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_status,",");
	END IF;
	
	IF p_question_level IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUESTION_LEVEL,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_question_level,",");
	END IF;
	
	
	IF p_analysis IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ANALYSIS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_analysis,"',");
	END IF;
	
	IF p_axam_area IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"AXAM_AREA,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_axam_area,"',");
	END IF;
	IF p_correct_answer IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CORRECT_ANSWER,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_correct_answer,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
