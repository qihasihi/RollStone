DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `subject_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `subject_info_proc_add`(
				            p_subject_name VARCHAR(1000),
				            p_subject_type int,
				            p_lzx_subjectid int,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO subject_info (";
	
	
	IF p_subject_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SUBJECT_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_subject_name,"',");
	END IF;
	if p_subject_type is not null then
		SET tmp_column_sql=CONCAT(tmp_column_sql,"subject_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_type,",");
	end if;
	if p_lzx_subjectid is not null then
		SET tmp_column_sql=CONCAT(tmp_column_sql,"lzx_subject_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_lzx_subjectid,",");
	end if;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
