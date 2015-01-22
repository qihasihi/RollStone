DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `page_right_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `page_right_proc_delete`(
				            p_ref VARCHAR(100),
							OUT affect_row INT)
BEGIN
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;	
	DELETE FROM page_right_info where ref=p_ref;
	SET affect_row = 1;	
END $$

DELIMITER ;
