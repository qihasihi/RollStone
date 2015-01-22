DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `help_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `help_info_proc_add`(
				           p_column_id INT,
					  p_column_name VARCHAR(1000),					
					  p_parentid INT,					  				          
				          p_edit_userid INT,					          
					 OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO help_info (";
	IF p_column_id IS NOT NULL THEN
	  SET tmp_column_sql=CONCAT(tmp_column_sql,'h_column_id,');
	  SET tmp_value_sql=CONCAT(tmp_value_sql,p_column_id,',');
	END IF;
	IF p_column_name IS NOT NULL THEN
	  SET tmp_column_sql=CONCAT(tmp_column_sql,'h_column_name,');
	  SET tmp_value_sql=CONCAT(tmp_value_sql,'"',p_column_name,'",');
	END IF;
	IF p_parentid IS NOT NULL THEN
	  SET tmp_column_sql=CONCAT(tmp_column_sql,'h_parentid,');
	  SET tmp_value_sql=CONCAT(tmp_value_sql,p_parentid,',');
	END IF;
	IF p_edit_userid IS NOT NULL THEN
	  SET tmp_column_sql=CONCAT(tmp_column_sql,'h_edit_userid,');
	  SET tmp_value_sql=CONCAT(tmp_value_sql,p_edit_userid,',');
	END IF;	
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"c_time,h_content)VALUES(",tmp_value_sql,"NOW(),'')");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
