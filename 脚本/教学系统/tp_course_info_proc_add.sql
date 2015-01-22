DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_add`(
					    p_course_id bigint,
				            p_course_name VARCHAR(100),
				            p_introduction VARCHAR(500),
				            p_user_id int,
				            p_share_type INT,
				            p_course_level INT,
				            p_cloud_status INT,
				            p_course_status INT,
				            p_local_status INT,
				            p_teacher_name VARCHAR(100),
				            p_school_name VARCHAR(100),
				            p_quote_id BIGINT,
				            p_dc_school_id int,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET affect_row =0;
	
	SET tmp_sql="INSERT INTO tp_course_info (";
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCHOOL_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_school_name,"',");
	END IF;
	IF p_dc_school_id>0 THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dc_school_id,",");
	END IF;
 
	IF p_course_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_course_name,"',");
	END IF;
	
	IF p_introduction IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"INTRODUCTION,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_introduction,"',");
	END IF;
	
	IF p_share_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SHARE_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_share_type,",");
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_name,"',");
	END IF;
	
	IF p_course_level IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_LEVEL,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_level,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CUSER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLOUD_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_cloud_status,",");
	END IF;
	
	IF p_course_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_status,",");
	END IF;
	
	IF p_local_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"LOCAL_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_local_status,",");
	END IF;
	
	IF p_quote_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUOTE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_quote_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
