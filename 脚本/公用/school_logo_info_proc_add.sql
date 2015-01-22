DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_logo_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_logo_info_proc_add`(p_school_id int,
							p_logo_src varchar(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO school_logo_info (";
	
	if p_school_id is not null then
		set tmp_column_sql = concat(tmp_column_sql,"school_id");
		set tmp_value_sql = concat(tmp_value_sql,p_school_id);
	end if;
	IF p_logo_src IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,",logo_src");
		set tmp_value_sql = concat(tmp_value_sql,",'",p_logo_src,"'");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,")VALUES(",tmp_value_sql,")");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
END $$

DELIMITER ;
