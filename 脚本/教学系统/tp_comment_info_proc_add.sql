DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_comment_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_comment_info_proc_add`(
				            p_comment_type INT,
				            p_comment_id INT,
				            p_score INT,
				            p_comment_user_id INT,
				            p_report_context VARCHAR(1000),
				            p_comment_context VARCHAR(1000),
				            p_report_user_id INT,
				            p_comment_object_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_comment_info (";
	
	IF p_comment_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_type,",");
	END IF;
	
	IF p_comment_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_id,",");
	END IF;
	
	IF p_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score,",");
	END IF;
	
	
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_user_id,",");
	END IF;
	
	IF p_report_context IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REPORT_CONTEXT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_report_context,"',");
	END IF;
	
	IF p_comment_context IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_CONTEXT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_comment_context,"',");
	END IF;
	
	IF p_report_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REPORT_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_report_user_id,",");
	END IF;
	
	IF p_comment_object_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_OBJECT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_object_id,",");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
