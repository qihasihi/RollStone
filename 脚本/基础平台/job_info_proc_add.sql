DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `job_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `job_info_proc_add`(p_job_name VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO job_info (";
	
	IF p_job_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"JOB_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_job_name,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
