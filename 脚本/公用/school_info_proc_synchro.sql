DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_info_proc_synchro`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `school_info_proc_synchro`(p_school_id BIGINT,p_school_name VARCHAR(100),p_id VARCHAR(100),OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
	SET affect_row=0;
	SELECT COUNT(*) INTO @SchoolCount FROM school_info WHERE school_id=p_school_id;
	IF @SchoolCount>0 THEN
		SET tmp_sql=CONCAT("UPDATE school_info set name='",p_school_name,"',ip='",p_id,"' where school_id=",p_school_id);
	ELSE 
		SET tmp_sql=CONCAT("INSERT INTO school_info(school_id,name,ip,c_time) VALUES(",p_school_id,",'",p_school_name,"','",p_id,"',now())");
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row=1;  
END$$

DELIMITER ;