DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `fn_click_search`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `fn_click_search`(p_fnid int)
BEGIN
	declare tmp_sql varchar(1000) default '';
	if p_fnid is not null then
		set tmp_sql = concat("select click_count from fn_click_info where fn_id =",p_fnid," and click_date=curdate()");
		SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;   
	end if;
		
END $$

DELIMITER ;
