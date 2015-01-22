DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `fn_click_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `fn_click_add`(p_fnid int,
				      p_count int,
				      out affect_row int
					)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET tmp_sql="INSERT INTO fn_click_info (";
	IF p_fnid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"fn_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_fnid,",");
	END IF;
	IF p_count IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"click_count,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_count,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"CLICK_DATE)VALUES(",tmp_value_sql,"curdate())");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
END $$

DELIMITER ;
