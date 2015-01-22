DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_theme_info_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_topic_theme_info_proc_add`(
						  p_view_count INT,
						  p_c_user_id INT,
						  p_comment_user_id INT,
						  p_cloud_status INT,
						  p_is_essence INT,
						  p_theme_title VARCHAR(1000),
						  p_comment_m_time DATETIME,
						  p_comment_title VARCHAR(1000),						  
						    p_theme_id BIGINT,
						    p_course_id BIGINT,
						    p_is_top INT,				            				          
						    p_topic_id BIGINT, 				            
						    p_quote_id BIGINT,
						    p_status BIGINT,
						    p_imattach VARCHAR(5000),
						    p_attachtype INT,
						    source_id INT,
						    p_pinglunshu INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_topic_theme_info (";
	
	IF p_theme_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"THEME_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_theme_id,",");
	END IF;
	
	IF p_imattach IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"im_attach,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_imattach,"',");
	END IF;
	
	IF p_attachtype IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"im_attach_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_attachtype,",");
	END IF;
	
	IF source_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"source_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,source_id,",");
	END IF;
	IF p_quote_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"quote_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_quote_id,",");
	END IF;
	IF p_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"status,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_status,",");
	END IF;
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_user_id,",");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	SET tmp_column_sql=CONCAT(tmp_column_sql,"THEME_CONTENT,");
	SET tmp_value_sql=CONCAT(tmp_value_sql,"'',");
	
	
	IF p_is_top IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IS_TOP,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_top,",");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	IF p_view_count IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"VIEW_COUNT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_view_count,",");
	END IF;
	
	IF p_topic_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TOPIC_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_topic_id,",");
	END IF;
	
	IF p_comment_m_time IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_M_TIME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_comment_m_time,",");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"CLOUD_STATUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_cloud_status,",");
	END IF;
	
	
	IF p_is_essence IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IS_ESSENCE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_essence,",");
	END IF;	
	SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_CONTENT,");
	SET tmp_value_sql=CONCAT(tmp_value_sql,"'',");
	
	
	IF p_theme_title IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"THEME_TITLE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_theme_title,"',");
	END IF;
	
	IF p_comment_title IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COMMENT_TITLE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_comment_title,"',");
	END IF;
	IF p_pinglunshu IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"pinglunshu,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_pinglunshu,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;