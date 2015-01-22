DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_report_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_report_proc_add`(
				            p_user_id INT,
				            p_content VARCHAR(1000),
				            p_res_id BIGINT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO rs_resource_report (";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	
	IF p_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_content,"',");
	END IF;
	
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_res_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
	UPDATE rs_resource_info SET REPORTNUM=REPORTNUM+1 WHERE RES_ID=p_res_id;
	
END $$

DELIMITER ;
