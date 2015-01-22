DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `comment_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `comment_info_proc_delete`(
				            p_score INT,
				            p_report_context VARCHAR(1000),
				            p_comment_object_id VARCHAR(1000),
				            p_report_user_id INT,
				            p_comment_id VARCHAR(1000),
				            p_anonymous INT,
				            p_comment_user_id INT,
				            p_comment_type INT,
				            p_comment_context VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="delete from comment_info where 1=1";
	
	IF p_report_context IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REPORT_CONTEXT='",p_report_context,"'");
	END IF;
	
	
	IF p_comment_object_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_OBJECT_ID='",p_comment_object_id,"'");
	END IF;
	
	
	IF p_report_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REPORT_USER_ID=",p_report_user_id);
	END IF;
	
	IF p_comment_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_ID='",p_comment_id,"'");
	END IF;
	
	IF p_anonymous IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ANONYMOUS=",p_anonymous);
	END IF;
	
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_USER_ID=",p_comment_user_id);
	END IF;
	
	IF p_comment_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_TYPE=",p_comment_type);
	END IF;
	
	IF p_comment_context IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COMMENT_CONTEXT='",p_comment_context,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
