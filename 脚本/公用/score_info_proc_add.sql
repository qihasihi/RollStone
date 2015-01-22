DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `score_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `score_info_proc_add`(
				            p_score_id VARCHAR(1000),
				            p_score_type INT,
				            p_score_object_id VARCHAR(1000),
				            p_score INT,
				            p_comment_id VARCHAR(1000),
				            p_score_user_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO score_info (";
	
	IF p_score_object_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE_OBJECT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_score_object_id,"',");
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score,",");
	END IF;
	
	IF p_comment_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_comment_id,"',");
	END IF;
	
	IF p_score_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_score_id,"',");
	END IF;
	
	IF p_score_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score_type,",");
	END IF;
	
	
	IF p_score_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score_user_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
