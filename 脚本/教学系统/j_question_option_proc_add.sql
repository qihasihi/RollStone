DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_question_option_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_question_option_proc_add`(
				            p_question_id bigint,
				            p_score INT,
				            p_content VARCHAR(4000),
				            p_is_right INT,
				            p_option_type VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_question_option (";
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IS_RIGHT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_right,",");
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUESTION_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_question_id,",");
	END IF;
	
	
	IF p_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_content,"',");
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score,",");
	END IF;
	
	IF p_option_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"OPTION_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_option_type,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
