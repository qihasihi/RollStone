DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `job_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `job_info_proc_delete`(p_job_id INT,
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from job_info where 1=1";
	
	IF p_job_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and JOB_ID=",p_job_id);
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
