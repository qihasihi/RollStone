DELIMITER $$
CREATE DEFINER=`mytest`@`%` PROCEDURE `class_proc_add`(
					    p_lzx_class_id INT,
					    p_dc_school_id int,
				             p_class_grade VARCHAR(1000),
				             p_class_name VARCHAR(1000),
				             p_year VARCHAR(1000),
				            p_type VARCHAR(1000),
				            p_pattern VARCHAR(1000),
				            p_subject_id int,
				            p_dctype INT,
				            p_isflag INT, 
				            p_allow_join int,
				            p_verify_time varchar(50),
				            p_cls_num int,
				            p_invite_code varchar(6),
				            p_c_user_id INT,
				            p_im_valdate_code VARCHAR(6),
				            p_class_id INT,
							p_activity_type int,
							p_term_id int,
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO class_info (";
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"class_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_id,",");
	END IF;
	IF p_lzx_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"lzx_classid,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_lzx_class_id,",");
	END IF;
	IF p_im_valdate_code IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"im_valdate_code,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_im_valdate_code,"',");
	END IF;
	IF p_dc_school_id>0 THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dc_school_id,",");
	END IF;
	
	IF p_allow_join IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"allow_join,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_allow_join,",");
	END IF;
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"c_user_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_c_user_id,",");
	END IF;
	
	IF p_verify_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"verify_time,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_verify_time,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	IF p_cls_num IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLS_NUM,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_cls_num,",");
	END IF;
 
 
	IF p_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_type,"',");
	END IF;
	
	IF p_dctype IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dctype,",");
	END IF;
	
	
	IF p_year IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"YEAR,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_year,"',");
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"subject_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_id,",");
	END IF;
	IF p_isflag IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"isflag,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_isflag,",");
	END IF;
	
	IF p_pattern IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PATTERN,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_pattern,"',");
	END IF;
	
	
	IF p_class_grade IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_GRADE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_class_grade,"',");
	END IF;
	
	IF p_class_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLASS_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_class_name,"',");
	END IF;
	
	IF p_invite_code IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"INVITE_CODE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_invite_code,"',");
	END IF;
	
	IF p_activity_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"activity_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_activity_type,"',");
	END IF;
	
	IF p_term_id IS NOT NULL THEN

		SET tmp_column_sql=CONCAT(tmp_column_sql,"term_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_term_id,"',");
	END IF;


	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$
DELIMITER ;
