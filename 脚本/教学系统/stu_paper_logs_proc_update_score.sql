DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `stu_paper_logs_proc_update_score`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `stu_paper_logs_proc_update_score`(
				          p_userid INT,
				          p_paper_id BIGINT,
				          p_score FLOAT,
				             p_ismarking INT,
				          OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE stu_paper_logs set c_time=c_time ';
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=score+",p_score);
	END IF;
	IF p_ismarking IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",is_marking=",p_ismarking);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_userid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and user_id=",p_userid);
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
