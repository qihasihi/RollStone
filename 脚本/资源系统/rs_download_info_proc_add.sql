DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_download_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_download_info_proc_add`(
				            p_res_id BIGINT,
				            p_user_id VARCHAR(100),
				            p_ref VARCHAR(100),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO rs_download_info (";
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_res_id,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
