DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `up_tp_course_info_proc_share`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `up_tp_course_info_proc_share`(
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '
	UPDATE tp_course_info set CLOUD_STATUS=0 AND cloud_status=4
					WHERE share_type=2 AND course_level=3  AND cloud_status IS NULL 					';
	
	SET @tmp_sql2=tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2  ;
	EXECUTE stmt2;
	DEALLOCATE PREPARE stmt2;
	set sumCount=1;
END $$

DELIMITER ;
