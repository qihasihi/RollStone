DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `operate_log_info_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `operate_log_info_delete`(
		operate_user varchar(100),operate_table  VARCHAR(100)
		,operate_rowsid  VARCHAR(100),operate_type VARCHAR(100)
		, OUT affect_row INT)
begin
	declare tmp_col varchar(2000) default '';
	declare tmp_val varchar(10000) default '';
			 	
	declare tmp varchar(20000) default 'DELETE FROM  operate_log_info WHERE 1=1 ';
	
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	
	IF operate_user IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col," AND operate_user='",operate_user,"'");
		
	END IF;
	IF operate_table IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col," AND operate_table='",operate_table,"'");
		
	END IF;
	IF operate_rowsid IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col," AND operate_rowsid='",operate_rowsid,"'");
		
	END IF;
	
	IF operate_type IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col," AND operate_type='",operate_type,"'");
		
	END IF;	
	
	SET @tmp_sql = tmp;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
