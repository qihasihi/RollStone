DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_complete_criteria_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_complete_criteria_proc_add`(
				             p_task_id VARCHAR(1000),
				            p_task_type INT,
				           p_creteria_type VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_task_complete_criteria (";
	
	IF p_creteria_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CRETERIA_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_creteria_type,"',");
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_type,",");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_task_id,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
