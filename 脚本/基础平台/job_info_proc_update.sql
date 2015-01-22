DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `job_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `job_info_proc_update`(
				          p_job_id INT,
				          p_job_name VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE job_info set m_time=NOW()';
	
	IF p_job_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",JOB_NAME='",p_job_name,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE JOB_ID=",p_job_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
