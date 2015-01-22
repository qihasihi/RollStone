DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `fn_click_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `fn_click_update`(p_fnid int,
					 p_count int,
					 out affect_row int
					)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET tmp_sql ='UPDATE fn_click_info set ';
	
	IF p_fnid IS NULL THEN
		SET affect_row = 0;
	ELSE
		IF p_count IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,"click_count=",p_count);
		END IF;
		SET tmp_sql = CONCAT(tmp_sql," where fn_id=",p_fnid," and click_date = curdate()");
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		SET affect_row = 1;
		end if;
END $$

DELIMITER ;
