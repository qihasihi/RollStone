DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `notice_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `notice_proc_delete`(p_ref varchar(60),out affect_row int)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	IF p_ref IS NOT NULL THEN
		SET tmp_sql = CONCAT("delete from notice_info where ref='",p_ref,"'");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		
		SET affect_row = 1;
        ELSE
		SET affect_row = 0;
	END IF;
END $$

DELIMITER ;
