DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activitysite_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activitysite_proc_update`(p_ref INT,
								 p_site_name VARCHAR(1000),
								 p_site_address VARCHAR(1000),
				                                 p_site_contain INT,
				                                 p_baseinfo VARCHAR(1000),		 
				                                 p_state INT,
				                                 OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET tmp_sql ='UPDATE at_site_info set m_time=NOW()';
	
	if p_ref is null then
		set affect_row = 0;
	else
		IF p_site_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SITE_NAME='",p_site_name,"'");
		END IF;
		IF p_site_address IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SITE_ADDRESS='",p_site_address,"'");
		END IF;
		IF p_site_contain IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SITE_CONTAIN=",p_site_contain);
		END IF;
		IF p_baseinfo IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",BASEINFO='",p_baseinfo,"'");
		END IF;
		IF p_state IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",STATE=",p_state);
		END IF;
		
		set tmp_sql = concat(tmp_sql," where ref=",p_ref);
		
		SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
	end if;
END $$

DELIMITER ;
