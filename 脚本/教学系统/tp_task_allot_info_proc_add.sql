DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_allot_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_allot_info_proc_add`(
					    p_task_id bigINT,
				            p_course_id BigINT,
				            p_user_type INT,
				            p_user_type_id bigINT,
				            p_b_time varchar(100),
				            p_e_time varchar(100),
				            p_c_user_id VARCHAR(1000),
				            p_criteria VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_task_allot_info (";
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_type,",");
	END IF;
	
	IF p_e_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"E_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_e_time,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	IF p_user_type_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_TYPE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_type_id,",");
	END IF;
	
	
	IF p_criteria IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CRITERIA,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_criteria,"',");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_id,",");
	END IF;
	
	IF p_b_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"B_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"str_to_date('",p_b_time,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
