DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `question_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `question_info_proc_update`(
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
	DECLARE tmp_sql VARCHAR(65000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE question_info set c_time=c_time ';
	
	IF p_c_user_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_NAME='",p_c_user_name,"'");
	END IF;
	
	IF p_extension IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",extension=",p_extension);
	END IF;
	
	IF p_exam_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",EXAM_TYPE=",p_exam_type);
	END IF;
	
	IF p_city IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CITY='",p_city,"'");
	END IF;
	
	IF p_paper_type_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAPER_TYPE_ID=",p_paper_type_id);
	END IF;
	
	IF p_province IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PROVINCE='",p_province,"'");
	END IF;
	
	IF p_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CONTENT='",p_content,"'");
	END IF;
	
	IF p_exam_subject_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",EXAM_SUBJECT_TYPE=",p_exam_subject_type);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	IF p_exam_year IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",EXAM_YEAR='",p_exam_year,"'");
	END IF;
	
	IF p_use_count IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USE_COUNT=",p_use_count);
	END IF;
	
	IF p_question_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUESTION_TYPE=",p_question_type);
	END IF;
	
	IF p_grade IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GRADE='",p_grade,"'");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STATUS=",p_status);
	END IF;
	
	IF p_question_level IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUESTION_LEVEL=",p_question_level);
	END IF;
	
	
	IF p_analysis IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ANALYSIS='",p_analysis,"'");
	END IF;
	
	IF p_axam_area IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",AXAM_AREA='",p_axam_area,"'");
	END IF;
	
	IF p_correct_answer IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",correct_answer='",p_correct_answer,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUESTION_ID=",p_question_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
