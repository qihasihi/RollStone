DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_proc_delete`(
				            p_res_keyword VARCHAR(1000),
				            p_user_id INT,
				            p_res_name VARCHAR(1000),
				            p_res_introduce VARCHAR(1000),
				            p_res_state INT,
				            p_res_id VARCHAR(1000),
				            right_view_roletype INT,
				            right_down_roletype INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from rs_resource_info where 1=1";
	
	IF p_res_keyword IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_KEYWORD='",p_res_keyword,"'");
	END IF;
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_NAME='",p_res_name,"'");
	END IF;
	
	
	IF p_res_introduce IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_INTRODUCE='",p_res_introduce,"'");
	END IF;
	
	IF p_res_state IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_STATUS=",p_res_state);
	END IF;
	IF right_down_roletype IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and right_down_roletype=",right_down_roletype);
	END IF;
	IF right_view_roletype IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and right_view_roletype=",right_view_roletype);
	END IF;
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_ID='",p_res_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
