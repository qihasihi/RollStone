DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_ques_team_rela_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_ques_team_rela_proc_add`(				         
				            p_order_id INT,				         
				            p_ques_id BIGINT,
				               p_team_id BIGINT,
				               p_ref BIGINT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_ques_team_rela (";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ref,",");
	END IF;
	
	IF p_order_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ORDER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_order_id,",");
	END IF;
	
	
	IF p_team_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEAM_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_team_id,",");
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ques_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
