DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_delete`(
				            p_school_name VARCHAR(1000),
				            p_teacher_name VARCHAR(1000),
				            p_cloud_status INT,
				            p_course_status INT,
				            p_user_id VARCHAR(1000),
				            p_local_status INT,
				            p_course_id BIGINT,
				            p_share_type INT,
				            p_course_level INT,
				            p_course_name VARCHAR(1000),				            
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_course_info where 1=1";
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_NAME='",p_course_name,"'");
	END IF;
	
	IF p_share_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and SHARE_TYPE=",p_share_type);
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TEACHER_NAME='",p_teacher_name,"'");
	END IF;
	
	IF p_course_level IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_LEVEL=",p_course_level);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	
	IF p_course_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_STATUS=",p_course_status);
	END IF;
	
	
	IF p_local_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and LOCAL_STATUS=",p_local_status);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
