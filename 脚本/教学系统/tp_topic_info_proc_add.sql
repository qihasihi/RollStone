DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_topic_info_proc_add`(
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
				            OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_topic_info (";
	
	IF p_topic_keyword IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TOPIC_KEYWORD,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_topic_keyword,"',");
	END IF;
	
	IF p_quote_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUOTE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_quote_id,",");
	END IF;
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_topic_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TOPIC_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_topic_id,",");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLOUD_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_cloud_status,",");
	END IF;
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ORDER_IDX,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_order_idx,",");
	END IF;
	
	IF p_topic_title IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TOPIC_TITLE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_topic_title,"',");
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_status,",");
	END IF;
	
	IF p_topic_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TOPIC_CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_topic_content,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_c_user_id,",");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
