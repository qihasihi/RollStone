DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_allot_info_proc_update`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_task_allot_info_proc_update`(
				          p_ref INT,
				          p_remind_status INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_task_allot_info set m_time=now() ';
	
	
	
	IF p_remind_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",remind_status=",p_remind_status);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ref=",p_ref);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
    END$$

DELIMITER ;