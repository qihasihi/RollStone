DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_myinfo_user_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_myinfo_user_info_proc_delete`(
				            p_msg_name VARCHAR(1000),
				            p_operate_id VARCHAR(1000),
				            p_msg_id INT,
				            p_user_ref VARCHAR(1000),
				            p_ref INT,
				            p_template_id VARCHAR(1000),
				            p_my_date VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from j_myinfo_user_info where 1=1";
	
	IF p_msg_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and MSG_NAME='",p_msg_name,"'");
	END IF;
	
	IF p_operate_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and OPERATE_ID='",p_operate_id,"'");
	END IF;
	
	IF p_msg_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and MSG_ID=",p_msg_id);
	END IF;
	
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_REF='",p_user_ref,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_template_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TEMPLATE_ID='",p_template_id,"'");
	END IF;
	
	IF p_my_date IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and MY_DATE='",p_my_date,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
