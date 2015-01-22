DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `init_wizard_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `init_wizard_proc_add`(p_step int,p_success int,OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO init_wizard_info(";
	if p_step is not null then
		set tmp_column_sql=concat(tmp_column_sql,"current_step");
		set tmp_value_sql=concat(tmp_value_sql,p_step);
	end if;
	
	IF p_success IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,",success");
		SET tmp_value_sql=CONCAT(tmp_value_sql,",",p_success);
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,")VALUES(",tmp_value_sql,")");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
