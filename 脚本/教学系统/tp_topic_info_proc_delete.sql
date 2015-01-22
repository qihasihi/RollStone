DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_topic_info_proc_delete`(
				            p_order_idx INT,
				            p_topic_keyword VARCHAR(1000),
				            p_status INT,
				              p_topic_title VARCHAR(1000),
				              p_c_user_id INT,
				              p_topic_content VARCHAR(1000),
				               p_course_id BIGINT,
				            p_cloud_status INT,
				            p_topic_id BIGINT,
				            p_quote_id BIGINT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_topic_info where 1=1";
	
	IF p_topic_keyword IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TOPIC_KEYWORD='",p_topic_keyword,"'");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	IF p_quote_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUOTE_ID=",p_quote_id);
	END IF;
	
	IF p_topic_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TOPIC_ID=",p_topic_id);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ORDER_IDX=",p_order_idx);
	END IF;
	
	IF p_topic_title IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TOPIC_TITLE='",p_topic_title,"'");
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and STATUS=",p_status);
	END IF;
	
	IF p_topic_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TOPIC_CONTENT='",p_topic_content,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID=",p_c_user_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
