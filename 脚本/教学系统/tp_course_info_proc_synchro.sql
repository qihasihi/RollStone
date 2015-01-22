DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_synchro`(
				            p_course_id bigint,
				            p_course_name VARCHAR(1000),
				            p_introduction VARCHAR(1000),
				            p_user_id int,
				            p_share_type INT,
				            p_course_level INT,
				            p_cloud_status INT,
				            p_course_status INT,
				            p_local_status INT,
				            p_teacher_name VARCHAR(1000),
				            p_school_name VARCHAR(1000),
				            p_quote_id BIGINT,
					    OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(20000);
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_course_info where course_id=",p_course_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF @tmp_sumCount>0 THEN
		call  tp_course_info_proc_update(p_course_id ,
				          p_course_name ,
				          p_introduction ,
				          p_user_id ,
				          p_share_type ,
				          p_course_level ,
				          p_cloud_status ,
				          p_course_status ,
				          p_local_status ,
				          p_teacher_name,
				          p_school_name ,
				          affect_row);
	    ELSE
		call tp_course_info_proc_add(p_course_id,
					p_course_name,
					p_introduction,
					p_user_id,
					p_share_type,
					p_course_level,
					p_cloud_status,
					p_course_status,
					p_local_status,
					p_teacher_name,
					p_school_name,
					p_quote_id,
					affect_row);
	END IF;
	
	
	
END $$

DELIMITER ;
