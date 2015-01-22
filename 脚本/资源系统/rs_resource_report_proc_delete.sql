DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_report_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_report_proc_delete`(
				            p_ref INT,
				            p_user_id INT,
				            p_content VARCHAR(1000),
				            p_res_id BIGINT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="delete from rs_resource_report where 1=1";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	
	IF p_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CONTENT='",p_content,"'");
	END IF;
	
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_ID=",p_res_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
