DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teacher_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teacher_proc_add`(
				           p_teacher_name VARCHAR(100),
				            p_teacher_sex VARCHAR(10),
				            p_teacher_address VARCHAR(1000),
				            p_teacher_phone VARCHAR(1000),
				            p_teacher_card_id varchar(20),
				            p_teacher_post VARCHAR(1000),
				            p_user_id VARCHAR(50),
				            p_password VARCHAR(1000),
				            p_teacher_level VARCHAR(1000),
				            p_teacher_birth varchar(50),
				            p_entry_time varchar(50),
				             p_img_heard_src VARCHAR(1000),
				           OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO teacher_info (";
	
	IF p_teacher_address IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_ADDRESS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_address,"',");
	END IF;
	
	IF p_teacher_sex IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_SEX,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_sex,"',");
	END IF;
	
	IF p_password IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PASSWORD,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_password,"',");
	END IF;
	
	IF p_teacher_card_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_CARD_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_card_id,"',");
	END IF;
	
	IF p_entry_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ENTRY_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_entry_time,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	IF p_teacher_post IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_POST,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_post,"',");
	END IF;
	
	
	IF p_teacher_birth IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_BIRTH,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_teacher_birth,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	IF p_img_heard_src IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IMG_HEARD_SRC,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_img_heard_src,"',");
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_name,"',");
	END IF;
	
	IF p_teacher_level IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_LEVEL,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_level,"',");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	
	IF p_teacher_phone IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TEACHER_PHONE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_teacher_phone,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
