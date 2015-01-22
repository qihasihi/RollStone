DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_res_comment_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_res_comment_info_proc_update`(
				          p_to_user_id INT,
				          p_reply_user_id INT,
				          p_to_real_name VARCHAR(1000),
				          p_ref INT,
				          p_course_id INT,
				          p_content VARCHAR(1000),
				          p_c_user_id INT,
				          p_type INT,
				          p_parent_comment_id INT,
				          p_res_detail_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_res_comment_info set m_time=NOW()';
	
	IF p_to_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TO_USER_ID=",p_to_user_id);
	END IF;
	
	IF p_reply_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REPLY_USER_ID=",p_reply_user_id);
	END IF;
	
	IF p_to_real_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TO_REAL_NAME='",p_to_real_name,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	IF p_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CONTENT='",p_content,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID=",p_c_user_id);
	END IF;
	
	IF p_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TYPE=",p_type);
	END IF;
	
	IF p_parent_comment_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PARENT_COMMENT_ID=",p_parent_comment_id);
	END IF;
	
	IF p_res_detail_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_DETAIL_ID=",p_res_detail_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
