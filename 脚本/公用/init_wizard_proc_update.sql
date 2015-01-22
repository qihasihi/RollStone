DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `init_wizard_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `init_wizard_proc_update`(p_ref int,p_step INT,p_success INT,OUT affect_row INT)
BEGIN
	
        DECLARE tmp_column_sql VARCHAR(2000) DEFAULT '';
        DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
		 
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row = 0;
	
	if p_ref is not null and
           p_step is not null then
           
           set tmp_column_sql = concat(tmp_column_sql,"current_step=",p_step);
           
           if p_success is not null then
		set tmp_column_sql = concat(tmp_column_sql,",success=",p_success);
           end if;
        
        end if;
           
	
	SET tmp_sql = CONCAT("update  init_wizard_info set ",tmp_column_sql," where ref='",p_ref,"'");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
		
	SET affect_row = 1;
END $$

DELIMITER ;
