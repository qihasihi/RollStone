DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_user_columnright_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_user_columnright_info_proc_delete`(
				            p_m_user_id VARCHAR(1000),
				            p_column_id INT,
				            p_ref VARCHAR(1000),
				            p_columnright_id INT,
				            p_user_id VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from j_user_columnright_info where 1=1";
	
	IF p_m_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and M_USER_ID='",p_m_user_id,"'");
	END IF;
	
	IF p_column_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COLUMN_ID=",p_column_id);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	
	IF p_columnright_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COLUMNRIGHT_ID=",p_columnright_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
