DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_operate_record_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_operate_record_proc_add`(
				            p_operate_type INT,
				            p_user_id INT,
				            p_res_id BIGINT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO rs_operate_record (";
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"OPERATE_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_operate_type,",");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
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
	
	if p_operate_type=1 then
		UPDATE rs_resource_info SET RECOMENDNUM=RECOMENDNUM+1 WHERE RES_ID=p_res_id;
	end if;
	
	IF p_operate_type=2 THEN
		UPDATE rs_resource_info SET PRAISENUM=PRAISENUM+1 WHERE RES_ID=p_res_id;
	END if;
	
END $$

DELIMITER ;
