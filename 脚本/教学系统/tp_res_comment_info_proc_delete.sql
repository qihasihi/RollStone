DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_res_comment_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_res_comment_info_proc_delete`(
					    p_ref INT,
					    p_res_detail_id varchar(50),
					    p_parent_comment_id varchar(50),
					    p_c_user_id varchar(50),
					    p_reply_user_id varchar(50),
				            p_type INT,
					    OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_res_comment_info where 1=1";
	
	
	IF p_reply_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REPLY_USER_ID='",p_reply_user_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	IF p_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TYPE=",p_type);
	END IF;
	
	IF p_parent_comment_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PARENT_COMMENT_ID='",p_parent_comment_id,"'");
	END IF;
	
	IF p_res_detail_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_ID='",p_res_detail_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
