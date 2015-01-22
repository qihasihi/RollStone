DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_file_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_file_info_proc_add`(
				            p_file_size int,
				            p_ref VARCHAR(1000),
				            p_file_name VARCHAR(1000),
				            p_res_id VARCHAR(1000),
				            p_path VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO rs_resource_file_info (";
	
	IF p_file_size IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"FILE_SIZE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_file_size,",");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	IF p_file_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"FILE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_file_name,"',");
	END IF;
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_res_id,"',");
	END IF;
	
	IF p_path IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PATH,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_path,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
