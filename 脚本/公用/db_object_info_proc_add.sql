DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `db_object_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `db_object_info_proc_add`(
				            p_sort_id INT,
				            p_c_user_id VARCHAR(1000),
				            p_desc VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO db_object_info (";
	
	
	IF p_sort_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SORT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_sort_id,",");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	
	IF p_desc IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"DESC,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_desc,"',");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
