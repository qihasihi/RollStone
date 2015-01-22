DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_collect_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_collect_proc_delete`(
				            p_user_id varchar(50),
				            p_collect_id INT,
				            p_res_detail_id bigint,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_resource_collect where 1=1";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_collect_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COLLECT_ID=",p_collect_id);
	END IF;
	
	
	IF p_res_detail_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_ID=",p_res_detail_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
