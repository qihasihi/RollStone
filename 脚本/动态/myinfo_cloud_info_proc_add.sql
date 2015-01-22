DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `myinfo_cloud_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `myinfo_cloud_info_proc_add`(
				            p_target_id Long,
					    p_type INT,
					    p_data VARCHAR(4000),
					    p_user_id LONG,					    
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO myinfo_cloud_info (";
	
	IF p_target_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"target_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_target_id,",");
	END IF;
	
	IF p_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_type,",");
	END IF;
	
	IF p_data IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"data,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_data,"',");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;	
	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"CTIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
