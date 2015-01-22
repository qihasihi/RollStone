DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_paper_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_paper_info_proc_update`(
				          p_course_id INT,
				          p_use_count INT,
				          p_paper_name VARCHAR(1000),
				          p_cloud_status INT,
				          p_paper_id INT,
				          p_c_user_id VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_paper_info set m_time=NOW()';
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	IF p_use_count IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USE_COUNT=",p_use_count);
	END IF;
	
	IF p_paper_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAPER_NAME='",p_paper_name,"'");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAPER_ID=",p_paper_id);
	END IF;
	
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
