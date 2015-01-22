DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_update`(
				           p_ref BIGINT,
				            p_user_id BIGINT,
				            p_attendanceNum INT,
				            p_smilingNum INT,
				            p_violationDisNum INT,
				            p_group_id BIGINT,				         
				            p_course_id BIGINT,	
				            p_subject_id INT,	
				             p_classid INT,
				            p_dcschoolid BIGINT,
				               p_classtype INT,	
				               p_task_score INT	,
				            p_comment_score   INT,		          
				            OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(100000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_stu_score set mtime=NOW()';
	
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	IF p_attendanceNum IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",attendance_Num=",p_attendanceNum);
	END IF;
	IF p_task_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",task_score=",p_task_score);
	END IF;
	IF p_comment_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",comment_score=",p_comment_score);
	END IF;
	
	
	IF p_smilingNum IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",smiling_Num=",p_smilingNum);
	END IF;
	
	IF p_violationDisNum IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",violation_Dis_Num=",p_violationDisNum);
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",group_id=",p_group_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ,course_id=",p_course_id);
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ,subject_id=",p_subject_id);
	END IF;
	IF p_classid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ,class_id=",p_classid);
	END IF;
	IF p_dcschoolid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ,dc_school_id=",p_dcschoolid);
	END IF;
		
	IF p_classtype IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ,class_type=",p_classtype);
	END IF;
		
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ref=",p_ref);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
