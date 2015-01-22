DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_complete_criteria_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_complete_criteria_proc_delete`(
				            p_ref INT,
				            p_task_id VARCHAR(1000),
				            p_task_type INT,
				             p_creteria_type VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_task_complete_criteria where 1=1";
	
	IF p_creteria_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CRETERIA_TYPE='",p_creteria_type,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_TYPE=",p_task_type);
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TASK_ID='",p_task_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
