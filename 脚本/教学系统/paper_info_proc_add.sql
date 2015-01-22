DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `paper_info_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `paper_info_proc_add`(
				            p_paper_id BIGINT,
				            p_paper_name VARCHAR(1000),
				            p_c_user_id INT,
				            p_score FLOAT,
				            p_paper_type INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO paper_info (";
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_paper_id,",");
	END IF;
	
	IF p_paper_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_paper_name,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_c_user_id,",");
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score,",");
	END IF;
	
	IF p_paper_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_paper_type,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;