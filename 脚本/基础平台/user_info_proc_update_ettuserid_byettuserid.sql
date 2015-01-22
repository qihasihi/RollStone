DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_info_proc_update_ettuserid_byettuserid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `user_info_proc_update_ettuserid_byettuserid`(	
						p_ett_user_id BIGINT,
				            OUT affect_row INT
				          )
BEGIN
	declare v_sql VARCHAR(10000) default '';
	SET affect_row = 0;
		set v_sql=CONCAT("UPDATE user_info set ett_user_id=NULL WHERE 1=1 AND ett_user_id=",p_ett_user_id);
		     SET @tmp_sql2=v_sql;
		     PREPARE stmt2 FROM @tmp_sql2  ;
		     EXECUTE stmt2;
		     DEALLOCATE PREPARE stmt2;
	
	SET affect_row = 1;
	
END $$

DELIMITER ;
