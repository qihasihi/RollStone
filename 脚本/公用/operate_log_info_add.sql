DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `operate_log_info_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `operate_log_info_add`(
		operate_user varchar(100),operate_table  VARCHAR(100)
		,operate_rowsid  VARCHAR(100),free_value  VARCHAR(2500)
		,current_value VARCHAR(2500),operate_type VARCHAR(100)
		,remark VARCHAR(2500), OUT affect_row INT)
begin
	declare tmp_col varchar(2000) default '';
	declare tmp_val varchar(10000) default '';
			 	
	declare tmp varchar(20000) default 'INSERT INTO operate_log_info(c_time';
	
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	
	IF operate_user IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col,",operate_user");
		SET tmp_val=CONCAT(tmp_val,",'",operate_user,"'");
	END IF;
	IF operate_table IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col,",operate_table");
		SET tmp_val=CONCAT(tmp_val,",'",operate_table,"'");
	END IF;
	IF operate_rowsid IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col,",operate_rowsid");
		SET tmp_val=CONCAT(tmp_val,",'",operate_rowsid,"'");
	END IF;
	IF free_value IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col,",free_value");
		SET tmp_val=CONCAT(tmp_val,",'",free_value,"'");
	END IF;
	IF current_value IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col,",current_value");
		SET tmp_val=CONCAT(tmp_val,",'",current_value,"'");
	END IF;
	IF operate_type IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col,",operate_type");
		SET tmp_val=CONCAT(tmp_val,",'",operate_type,"'");
	END IF;
	IF remark IS NOT NULL THEN
		SET tmp_col=CONCAT(tmp_col,",remark");
		SET tmp_val=CONCAT(tmp_val,",'",remark,"'");
	END IF;
	
	
	SET tmp=CONCAT(tmp,tmp_col,') values(NOW()',tmp_val,')');
	
	SET @tmp_sql = tmp;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
