DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_state_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_proc_state_update`(
					p_updatestate INT,
				          p_res_state INT,					        
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE rs_resource_info set m_time=NOW()';
	
	
	
	IF p_updatestate IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_STATE=",p_updatestate);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND RES_STATE=",p_res_state);
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
