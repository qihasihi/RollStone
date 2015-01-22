DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_right_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_right_info_proc_add`(
					 p_ref VARCHAR(1000),
					 p_c_user_id VARCHAR(100),
					  p_right_type INT,
					    p_right_roletype INT,
					     p_right_subject VARCHAR(1000),
				            p_right_user_ref VARCHAR(1000),
				            res_id VARCHAR(100),
				            subjectid VARCHAR(100),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO rs_resource_right_info (";
	
	
	
	IF p_right_user_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RIGHT_USER_REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_right_user_ref,"',");
	END IF;
	IF subjectid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"subjectid,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",subjectid,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	IF p_right_subject IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RIGHT_SUBJECT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_right_subject,"',");
	END IF;
	
	IF res_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"res_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",res_id,"',");
	END IF;
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	IF p_right_roletype IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RIGHT_ROLETYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_right_roletype,",");
	END IF;
	
	IF p_right_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RIGHT_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_right_type,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
