DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `comment_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `comment_info_proc_add`(
				            p_comment_id VARCHAR(1000),
				            p_comment_type INT,
				            p_comment_object_id VARCHAR(1000),
				            p_comment_context VARCHAR(1000),
				            p_score INT,
				            p_comment_user_id INT,
				            p_anonymous INT,
				            p_comment_parentid varchar(50),
				            p_to_userid	varchar(50),
				            p_to_realname varchar(100),
				            p_report_user_id VARCHAR(100),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO comment_info (";
	
	IF p_comment_object_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_OBJECT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_comment_object_id,"',");
	END IF;
	
	IF p_comment_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_comment_id,"',");
	END IF;
	
	IF p_anonymous IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ANONYMOUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_anonymous,",");
	END IF;
	
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_user_id,",");
	END IF;
	
	IF p_comment_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_type,",");
	END IF;
	
	IF p_comment_context IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_CONTEXT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_comment_context,"',");
	END IF;
	
	IF p_comment_parentid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"comment_parent_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_comment_parentid,"',");
	END IF;
	
	IF p_to_userid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"to_user_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_to_userid,"',");
	END IF;
	
	IF p_to_realname IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"to_real_name,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_to_realname,"',");
	END IF;
	
	IF p_report_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"report_user_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_report_user_id,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
