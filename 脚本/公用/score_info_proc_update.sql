DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `score_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `score_info_proc_update`(
				          p_score_object_id VARCHAR(1000),
				          p_score INT,
				          p_comment_id VARCHAR(1000),
				          p_score_id VARCHAR(1000),
				          p_score_type INT,
				          p_score_user_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE score_info set m_time=NOW()';
	
	IF p_score_object_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE_OBJECT_ID='",p_score_object_id,"'");
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=",p_score);
	END IF;
	
	IF p_comment_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COMMENT_ID='",p_comment_id,"'");
	END IF;
	
	IF p_score_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE_ID='",p_score_id,"'");
	END IF;
	
	IF p_score_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE_TYPE=",p_score_type);
	END IF;
	
	
	IF p_score_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE_USER_ID=",p_score_user_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE SCORE_ID='",p_score_id,"'"); 
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
