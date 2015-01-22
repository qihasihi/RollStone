DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `appraise_item_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `appraise_item_proc_update`(
				          p_ref INT,
				          p_name VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE pj_appraise_item_info set m_time=NOW()';
	
	
	IF p_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",NAME='",p_name,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE REF=",p_ref);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
