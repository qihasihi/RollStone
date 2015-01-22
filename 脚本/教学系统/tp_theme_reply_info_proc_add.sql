DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_theme_reply_info_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_theme_reply_info_proc_add`(
				            p_theme_id BIGINT,
				            p_user_id BIGINT,				          
				            p_reply_id BIGINT,
				            p_topic_id BIGINT, 
				            p_to_replyid BIGINT,
				            p_to_real_name VARCHAR(100),
				            p_ctimeStr VARCHAR(100),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_theme_reply_info (";
	
	IF p_theme_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"THEME_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_theme_id,",");
	END IF;
	IF p_topic_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TOPIC_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_topic_id,",");
	END IF;	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_user_id,",");
	END IF;
	SET tmp_column_sql=CONCAT(tmp_column_sql,"REPLY_CONTENT,");
	SET tmp_value_sql=CONCAT(tmp_value_sql,"'',");
	IF p_reply_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REPLY_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_reply_id,",");
	END IF;
	IF p_to_replyid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"to_replyid,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_to_replyid,",");
	END IF;
	IF p_to_real_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"to_real_name,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_to_real_name,"',");
	END IF;
	
	
	
	SET tmp_column_sql=CONCAT(tmp_column_sql,"c_time");
	IF p_ctimeStr IS NOT NULL THEN
	  SET tmp_value_sql=CONCAT(tmp_value_sql,"DATE_FORMAT('",p_ctimeStr,"','%Y-%m-%d %H:%i:%s')");
	  ELSE
	  SET tmp_value_sql=CONCAT(tmp_value_sql,"now()");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,")VALUES(",tmp_value_sql,")");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;