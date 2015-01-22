DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `jobuser_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `jobuser_proc_delete`(
				            p_ref int,p_user_id varchar(50),p_job_id int,out affect_row int)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	IF p_ref IS NULL and p_user_id is null and p_job_id is null THEN
		SET affect_row = 0;
	ELSE
		SET tmp_sql="delete from j_user_job where 1=1";
		
		if p_ref is not null then
			SET tmp_sql=concat(tmp_sql," and ref=",p_ref);
		end if;
		
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and user_id='",p_user_id,"'");
		END IF;
		
		IF p_job_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and job_id=",p_job_id);
		END IF;
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		
		SET affect_row = 1;
	END IF;
END $$

DELIMITER ;
