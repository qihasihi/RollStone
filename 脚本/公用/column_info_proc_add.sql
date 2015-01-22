DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `column_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `column_info_proc_add`(
				            p_m_user_id VARCHAR(1000),
				            p_column_name VARCHAR(1000),
				            p_column_id INT,
				            p_path VARCHAR(1000),
				            p_ref VARCHAR(1000),
				            p_styleclassid VARCHAR(100),
				             p_fnid VARCHAR(100),
				             p_remark VARCHAR(4000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO column_info (";
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"M_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_m_user_id,"',");
	END IF;
	
	IF p_column_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COLUMN_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_column_name,"',");
	END IF;
	
	IF p_column_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COLUMN_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_column_id,",");
	END IF;
	
	IF p_path IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PATH,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_path,"',");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	IF p_styleclassid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"styleclassid,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_styleclassid,"',");
	END IF;
	IF p_fnid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"fn_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_fnid,"',");
	END IF;
	IF p_remark IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"remark,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_remark,"',");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
