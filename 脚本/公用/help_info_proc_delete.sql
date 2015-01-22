DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `help_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `help_info_proc_delete`(
				           p_column_id INT,
					  p_column_name VARCHAR(1000),					
					  p_parentid INT,					  				          
				          p_edit_userid INT,					          
					 OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="DELETE FROM help_info ";	
	
	set tmp_sql=CONCAT(tmp_sql,' WHERE 1=1 ');
	if p_column_id IS NOT NULL THEN
	  set tmp_sql=CONCAT(tmp_sql,' AND h_column_id=',p_column_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
