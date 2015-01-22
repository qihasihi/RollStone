DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_add`(
				            p_user_id BIGINT,
				            p_attendanceNum INT,
				            p_smilingNum INT,
				            p_violationDisNum INT,
				            p_group_id BIGINT,				         
				            p_course_id BIGINT,	
				            p_subjectid INT,	
				             p_classid INT,
				            p_dcschoolid BIGINT,
				            p_classtype INT,	
				            p_task_score INT	,
				            p_comment_score   INT,
				            p_group_score INT,
				            p_course_total_score BIGINT,
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_stu_score (";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	IF p_task_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_score,",");
	END IF;
	IF p_comment_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"comment_score,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_score,",");
	END IF;
	IF p_subjectid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"subject_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subjectid,",");
	END IF;
	
	IF p_attendanceNum IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"attendance_Num,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_attendanceNum,",");
	END IF;
	
	IF p_smilingNum IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"Smiling_Num,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_smilingNum,",");
	END IF;
	
	IF p_violationDisNum IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"Violation_Dis_Num,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_violationDisNum,",");
	END IF;
	IF p_group_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"group_score,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_group_score,",");
	END IF;
	
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"group_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_group_id,",");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"course_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	IF p_classid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"class_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_classid,",");
	END IF;
	IF p_dcschoolid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dcschoolid,",");
	END IF;
	
	IF p_classtype IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"class_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_classtype,",");
	END IF;
	IF p_course_total_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"course_total_score,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_total_score,",");
	END IF;
	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"CTIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SELECT LAST_INSERT_ID() INTO @LastInertId ;
	SELECT dc_type INTO @dcType FROM class_info WHERE class_id=p_classid AND dc_school_id=p_dcschoolid;
	IF @dcType<>3 THEN
		UPDATE tp_stu_score SET submit_flag=1 WHERE ref=@LastInertId;
	END IF;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
