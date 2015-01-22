DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_right_user_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_right_user_proc_add`(
				            p_user_id int,
				            p_page_right_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_user_page_right (";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	
	
	IF p_page_right_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAGE_RIGHT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_page_right_id,",");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
