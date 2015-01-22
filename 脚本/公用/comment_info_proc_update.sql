DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `comment_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `comment_info_proc_update`(
				          p_comment_id VARCHAR(1000),
				          p_comment_type INT,
				          p_comment_object_id VARCHAR(1000),
				          p_comment_context VARCHAR(1000),
				          p_comment_user_id INT,
				          p_anonymous INT,
				          p_report_user_id INT,
				          p_report_context VARCHAR(1000),
				          p_support INT,
				          p_oppose INT,
				          
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE comment_info set m_time=NOW()';
	
	IF p_oppose IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",OPPOSE=",p_oppose);
	END IF;
	
	
	IF p_report_context IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REPORT_CONTEXT='",p_report_context,"'");
	END IF;
	
	IF p_comment_object_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COMMENT_OBJECT_ID='",p_comment_object_id,"'");
	END IF;
	
	IF p_report_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REPORT_USER_ID=",p_report_user_id);
	END IF;
	
	IF p_support IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SUPPORT=",p_support);
	END IF;
	
	IF p_anonymous IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ANONYMOUS=",p_anonymous);
	END IF;
	
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COMMENT_USER_ID=",p_comment_user_id);
	END IF;
	
	IF p_comment_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COMMENT_TYPE=",p_comment_type);
	END IF;
	
	
	IF p_comment_context IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COMMENT_CONTEXT='",p_comment_context,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE COMMENT_ID='",p_comment_id,"'");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
