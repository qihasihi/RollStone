DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_logo_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_logo_info_proc_delete`(p_school_id int,OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="delete from school_logo_info where 1=1";
	
	if p_school_id is not null then
		set tmp_sql = concat(tmp_sql," and school_id=",p_school_id);
	end if;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
END $$

DELIMITER ;
