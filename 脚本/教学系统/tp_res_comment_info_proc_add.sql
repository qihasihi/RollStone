DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_res_comment_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_res_comment_info_proc_add`(
					    p_ref varchar(50),
					    p_reply_user_id varchar(50),
					    p_res_detail_id varchar(50),
					    p_content VARCHAR(1000),
					    p_type INT,
					    p_c_user_id varchar(50),
					    p_course_id varchar(50),
				            p_to_user_id varchar(50),
				            p_to_real_name VARCHAR(1000),
				            p_parent_comment_id varchar(50),
				            
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_res_comment_info (";
	
	IF p_to_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TO_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_to_user_id,"',");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	IF p_reply_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REPLY_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_reply_user_id,"',");
	END IF;
	
	IF p_to_real_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TO_REAL_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_to_real_name,"',");
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_course_id,"',");
	END IF;
	
	IF p_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_content,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	IF p_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_type,",");
	END IF;
	
	IF p_parent_comment_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PARENT_COMMENT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_parent_comment_id,"',");
	END IF;
	
	IF p_res_detail_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_res_detail_id,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
