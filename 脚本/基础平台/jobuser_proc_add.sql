DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `jobuser_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `jobuser_proc_add`(
						p_user_id varchar(50),p_job_id int,out affect_row int)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
	
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row = 0;
	
	IF  p_user_id IS NOT NULL AND p_job_id IS NOT NULL THEN
		SET tmp_column_sql = 'user_id,job_id,c_time';
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',",p_job_id,",NOW()");
		
		SET tmp_sql =CONCAT("INSERT INTO j_user_job (",tmp_column_sql,") VALUES (",tmp_value_sql,")");
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
	    	
	    SET affect_row = 1;
	ELSE
	    
	    SET affect_row = 0;
	END IF;
END $$

DELIMITER ;
