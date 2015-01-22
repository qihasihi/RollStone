DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `rs_resource_info_proc_add`(
				            p_res_id BIGINT,
				            p_res_name VARCHAR(10000),
				            p_res_keyword VARCHAR(10000),
				            p_res_introduce VARCHAR(400000),
				            p_file_suffixname VARCHAR(1000),
				            p_file_size BIGINT,
				            p_user_id INT,
				            p_user_name VARCHAR(1000),
				            p_grade INT,
				            p_subject INT,
				            p_file_type INT,
				            p_res_type INT,
				            p_res_state INT,
				            p_share_status INT,
				            p_school_name VARCHAR(1000),
				            p_use_object VARCHAR(1000),
				            p_res_degree INT,
				            p_user_type INT,
				            p_file_name VARCHAR(100),
				            p_dc_school_id INT,
				            p_difftype INT,
				            p_ismicopiece INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(100000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(100000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(10000000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO rs_resource_info (";
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_res_id,",");
	END IF;
	
	IF p_grade IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"GRADE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_grade,",");
	END IF;
	
	IF p_file_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"FILE_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_file_type,",");
	END IF;
	
	IF p_res_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_res_type,",");
	END IF;
	
	IF p_file_suffixname IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"FILE_SUFFIXNAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_file_suffixname,"',");
	END IF;
	
	IF p_subject IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SUBJECT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject,",");
	END IF;
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCHOOL_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_school_name,"',");
	END IF;
	
	IF p_use_object IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USE_OBJECT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_use_object,"',");
	END IF;
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_res_name,"',");
	END IF;
	
	IF p_res_keyword IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_KEYWORD,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_res_keyword,"',");
	END IF;
	
	IF p_file_size IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"FILE_SIZE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_file_size,",");
	END IF;
	
	IF p_res_introduce IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_INTRODUCE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_res_introduce,"',");
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_name,"',");
	END IF;
	
	IF p_res_degree IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_DEGREE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_res_degree,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	IF p_res_state IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_res_state,",");
	END IF;
	
	IF p_share_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SHARE_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_share_status,",");
	END IF;
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_type,",");
	END IF;
	
	IF p_file_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"FILE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_file_name,"',");
	END IF;
	IF p_difftype IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DIFF_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_difftype,",");
	END IF;
	IF p_ismicopiece IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"is_mic_copiece,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ismicopiece,",");
	END IF;
	
	IF p_dc_school_id>0 THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dc_school_id,",");
	END IF;
 
 
 
 
		
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;