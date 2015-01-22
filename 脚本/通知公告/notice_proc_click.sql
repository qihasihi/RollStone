DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `notice_proc_click`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `notice_proc_click`(p_ref varchar(60),OUT affect_row INT)
BEGIN
	declare tmp_sql varchar(3000) default '';
		 
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row = 0;
	if p_ref is not null then 
		SELECT click_count INTO @click FROM notice_info where ref=p_ref;
		set tmp_sql = concat("update notice_info set click_count = ",@click+1," where ref = '",p_ref,"'");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
			
		SET affect_row = 1;
	else 
		set affect_row = 0;
	end if;
	
	
END $$

DELIMITER ;
