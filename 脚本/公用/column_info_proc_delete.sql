DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `column_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `column_info_proc_delete`(
				            p_m_user_id VARCHAR(1000),
				            p_column_name VARCHAR(1000),
				            p_column_id INT,
				            p_path VARCHAR(1000),
				            p_ref VARCHAR(1000),
				            p_styleclassid VARCHAR(100),
				             p_fnid VARCHAR(100),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from column_info where 1=1";
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_column_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COLUMN_NAME='",p_column_name,"'");
	END IF;
	
	IF p_column_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COLUMN_ID=",p_column_id);
	END IF;
	
	IF p_path IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PATH='",p_path,"'");
	END IF;
	IF p_styleclassid IS NOT NULL THEN
	SET tmp_sql=CONCAT(tmp_sql," and styleclassid='",p_styleclassid,"'");
	END IF;
	IF p_fnid IS NOT NULL THEN
	SET tmp_sql=CONCAT(tmp_sql," and fn_id='",p_fnid,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
