DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_info_proc_delete`(p_school_id LONG,out affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
	set affect_row=0;
	set tmp_sql="DELETE FROM school_info where 1=1 ";
	IF p_school_id IS NOT NULL THEN
	   set tmp_sql=CONCAT(tmp_sql," AND school_id=",p_school_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	set affect_row=1;
END $$

DELIMITER ;
