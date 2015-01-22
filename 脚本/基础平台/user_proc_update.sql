DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `user_proc_update`(
				          p_ref VARCHAR(1000),
				          p_user_id INT,
				          p_user_name VARCHAR(1000),
				          p_password VARCHAR(1000),
				          p_state_id INT,
				          p_identity_number VARCHAR(1000),
				          p_birth_date VARCHAR(50),
				          p_address VARCHAR(1000),
				          p_mail_address VARCHAR(1000),
				          p_real_name VARCHAR(1000),
				          p_gender INT,  
				          p_head_image VARCHAR(1000),
				          p_pass_question VARCHAR(1000),
				          p_question_answer VARCHAR(1000),
				          p_ismodify	int,
				          p_lzx_user_id varchar(32),
				           p_school_id VARCHAR(32),
				           p_ett_user_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row=1;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE user_info set m_time=NOW()';
	
	IF p_head_image IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",HEAD_IMAGE='",p_head_image,"'");
	END IF;
	
	IF p_password IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PASSWORD='",p_password,"'");
	END IF;
	
	IF p_state_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STATE_ID=",p_state_id);
	END IF;
	
	IF p_pass_question IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PASS_QUESTION='",p_pass_question,"'");
	END IF;
	
	IF p_real_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REAL_NAME='",p_real_name,"'");
	END IF;
	
	IF p_identity_number IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IDENTITY_NUMBER='",p_identity_number,"'");
	END IF;
	
	IF p_birth_date IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",BIRTH_DATE=str_to_date('",p_birth_date,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	IF p_question_answer IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",QUESTION_ANSWER='",p_question_answer,"'");
	END IF;
	
	IF p_address IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ADDRESS='",p_address,"'");
	END IF;
	
	IF p_gender IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",GENDER=",p_gender);
	END IF;
	
	IF p_ismodify IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",IS_MODIFY=",p_ismodify);
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_NAME='",p_user_name,"'");
	END IF;
	IF p_ett_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ETT_USER_ID=",p_ett_user_id);
	END IF;
	
	
	IF p_mail_address IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",MAIL_ADDRESS='",p_mail_address,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_ref  IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ref='",p_ref,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and user_id=",p_user_id);
	END IF;
	
	IF p_lzx_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and lzx_user_id='",p_lzx_user_id,"'");
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and school_id='",p_school_id,"'");
	END IF;
	
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
	
	IF affect_row>0 THEN
		SET @affect_row=0;
	
		CALL j_myinfo_user_info_add_tongy(20,p_ref,11,'ÓÃ»§ÐÞ¸Ä',null,'',@affect_row);	
		SET affect_row=@affect_row;
	END IF;
END $$

DELIMITER ;
