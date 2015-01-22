DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `paper_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `paper_info_proc_update`(
				          p_ref INT,
				          p_paper_id BIGINT,
				          p_paper_name VARCHAR(1000),
				          p_c_user_id INT,
				          p_score float,
				          p_paper_type INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE paper_info set m_time=NOW()';
	
	
	IF p_paper_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAPER_NAME='",p_paper_name,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID=",p_c_user_id);
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=",p_score);
	END IF;
	
	IF p_paper_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAPER_TYPE=",p_paper_type);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 " );  
	
		IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PAPER_ID=",p_paper_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
