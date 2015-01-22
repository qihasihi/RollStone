DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `question_info_proc_synchro`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `question_info_proc_synchro`(
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
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM question_info where question_id=",p_question_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_sumCount >0 THEN
		CALL question_info_proc_update(
				        p_question_id ,
					p_question_type ,
					p_paper_type_id ,
					p_question_level ,
					p_status ,
					p_cloud_status ,
					p_content ,
					p_analysis ,
					p_exam_type ,
					p_exam_subject_type ,
					p_exam_year,
					p_axam_area ,
        				p_province ,
				        p_city ,
				        p_grade ,
				        p_use_count ,
				        p_c_user_id ,
					p_c_user_name,
					p_correct_answer ,
					p_extension,
				        affect_row 
				          );
	  ELSE
		CALL question_info_proc_add(
					p_question_id ,
					p_question_type ,
					p_paper_type_id ,
					p_question_level,
					p_status ,
					p_cloud_status ,
					p_content ,
					p_analysis ,
					p_exam_type ,
					p_exam_subject_type ,
					p_exam_year ,
					p_axam_area ,
        				p_province ,
				        p_city ,
				        p_grade ,
				        p_use_count,
				        p_c_user_id ,
					p_c_user_name ,
					p_correct_answer ,
					p_extension,
					affect_row );
	END IF;
	
    END$$

DELIMITER ;