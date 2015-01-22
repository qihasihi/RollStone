DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `user_proc_add`(
				            
				            p_ref VARCHAR(1000),
				            p_user_name VARCHAR(1000),
				            p_password VARCHAR(1000),
				            p_state_id INT,
				            p_mail_address VARCHAR(1000),
				            p_real_name VARCHAR(1000),
				            p_gender INT,
				            p_pass_question VARCHAR(1000),
				            p_question_answer VARCHAR(1000),
				            p_birth_day	  VARCHAR(100),
				            p_lzx_user_id VARCHAR(32),
				            p_school_id VARCHAR(50),
				            p_dc_school_id INT,
				            p_ett_user_id INT,
				            p_headurl VARCHAR(1000),
				            p_is_activity INT,
				            OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO user_info (";
	
	IF p_password IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PASSWORD,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_password,"',");
	END IF;
	
	IF p_state_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STATE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_state_id,",");
	END IF;
	
	IF p_pass_question IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PASS_QUESTION,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_pass_question,"',");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	IF p_real_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REAL_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_real_name,"',");
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_name,"',");
	END IF;
	
	IF p_question_answer IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUESTION_ANSWER,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_question_answer,"',");
	END IF;
	
	IF p_gender IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GENDER,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_gender,",");
	END IF;
	
	IF p_mail_address IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MAIL_ADDRESS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_mail_address,"',");
	END IF;
	
	IF p_headurl IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"HEAD_IMAGE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_headurl,"',");
	END IF;
	
	IF p_birth_day IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"BIRTH_DATE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_birth_day,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	IF p_lzx_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"LZX_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_lzx_user_id,"',");
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCHOOL_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_school_id,"',");
	END IF;
	
	IF p_ett_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ETT_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ett_user_id,",");
	END IF;
	
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DC_SCHOOL_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dc_school_id,",");
	END IF;
	
	IF p_is_activity IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"is_activity,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_activity,",");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;