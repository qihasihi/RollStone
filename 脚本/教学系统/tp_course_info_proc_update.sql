DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_update`(
				          p_course_id bigINT,
				          p_course_name VARCHAR(1000),
				          p_introduction VARCHAR(5000),
				          p_user_id int,
				          p_share_type INT,
				          p_course_level INT,
				          p_cloud_status INT,
				          p_course_status INT,
				          p_local_status INT,
				          p_teacher_name VARCHAR(1000),
				          p_school_name VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_course_info set m_time=NOW()';
	
	IF p_course_id IS NOT NULL THEN
	
		IF p_school_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SCHOOL_NAME='",p_school_name,"'");
		END IF;
		
		IF p_course_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",COURSE_NAME='",p_course_name,"'");
		END IF;
		
		IF p_introduction IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",INTRODUCTION='",p_introduction,"'");
		END IF;
		
		IF p_share_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SHARE_TYPE=",p_share_type);
		END IF;
		
		IF p_teacher_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",TEACHER_NAME='",p_teacher_name,"'");
		END IF;
		
		IF p_course_level IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",COURSE_LEVEL=",p_course_level);
		END IF;
		
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",CUSER_ID=",p_user_id);
		END IF;
		
		IF p_cloud_status IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",CLOUD_STATUS=",p_cloud_status);
		END IF;
		
		
		IF p_course_status IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",COURSE_STATUS=",p_course_status);
		END IF;
		
		
		IF p_local_status IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",LOCAL_STATUS=",p_local_status);
		END IF;
		
		SET tmp_sql =CONCAT(tmp_sql, " WHERE COURSE_ID=",p_course_id);  
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		SET affect_row = 1;
	end if;
END $$

DELIMITER ;
