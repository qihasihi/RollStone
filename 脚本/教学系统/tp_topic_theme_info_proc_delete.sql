DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_theme_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_topic_theme_info_proc_delete`(
				             p_view_count INT,
						  p_c_user_id INT,
						  p_comment_user_id INT,
						  p_cloud_status INT,
						  p_is_essence INT,
						  p_theme_title VARCHAR(1000),
						  p_theme_content VARCHAR(1000),
						  p_comment_m_time DATETIME,
						  p_comment_title VARCHAR(1000),						  
						    p_theme_id BIGINT,
						    p_course_id BIGINT,
						    p_is_top INT,				            				          
						    p_topic_id BIGINT, 
						    p_comment_content VARCHAR(1000),
						    p_quote_id BIGINT,
						      p_status BIGINT,
						    p_imattach VARCHAR(5000),
						    p_attachtype INT,
						    source_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_topic_theme_info where 1=1";
	IF p_imattach IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and im_attach=",p_imattach);
	END IF;
	IF p_attachtype IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and im_attach_type=",p_attachtype);
	END IF;
	IF source_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and source_id=",source_id);
	END IF;
	
	IF p_theme_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and THEME_ID=",p_theme_id);
	END IF;
	IF p_quote_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUOTE_ID=",p_quote_id);
	END IF;
	
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_USER_ID=",p_comment_user_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID='",p_c_user_id,"'");
	END IF;
	IF p_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and STATUS=",p_status);
	END IF;
	
	IF p_theme_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and THEME_CONTENT='",p_theme_content,"'");
	END IF;
	
	IF p_is_top IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and IS_TOP=",p_is_top);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	IF p_view_count IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and VIEW_COUNT=",p_view_count);
	END IF;
	
	IF p_topic_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TOPIC_ID=",p_topic_id);
	END IF;
	
	IF p_comment_m_time IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_M_TIME=",p_comment_m_time);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	
	IF p_is_essence IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and IS_ESSENCE=",p_is_essence);
	END IF;
	
	IF p_comment_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_CONTENT='",p_comment_content,"'");
	END IF;
	
	
	IF p_theme_title IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and THEME_TITLE='",p_theme_title,"'");
	END IF;
	
	IF p_comment_title IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_TITLE='",p_comment_title,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
