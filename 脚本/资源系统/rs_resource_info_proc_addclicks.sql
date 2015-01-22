DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_addclicks`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_proc_addclicks`(
				          p_res_id VARCHAR(1000),
				          OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	SET affect_row = 0;
	
	IF p_res_id IS NOT NULL THEN
	
	SET tmp_sql =concat("UPDATE rs_resource_info set CLICKS=CLICKS+1 where RES_ID='",p_res_id,"'");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
	
	END IF;
END $$

DELIMITER ;
