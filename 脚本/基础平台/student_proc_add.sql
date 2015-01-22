DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `student_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `student_proc_add`(
				            
				            p_ref VARCHAR(1000),
				            p_user_id VARCHAR(50),
				            p_stu_no VARCHAR(100),
				            p_stu_name VARCHAR(1000),
				            p_stu_sex VARCHAR(1000),
				            p_stu_address VARCHAR(1000),
				            p_link_man VARCHAR(1000),
				            p_link_man_phone VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO student_info (";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	else
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"UUID(),");
	END IF;
	
	IF p_stu_address IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STU_ADDRESS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_stu_address,"',");
	END IF;
	
	IF p_stu_sex IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STU_SEX,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_stu_sex,"',");
	END IF;
	
	IF p_stu_no IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STU_NO,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_stu_no,"',");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	
	IF p_link_man IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"LINK_MAN,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_link_man,"',");
	END IF;
	
	
	
	IF p_link_man_phone IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"LINK_MAN_PHONE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_link_man_phone,"',");
	END IF;
	
	IF p_stu_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STU_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_stu_name,"',");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
