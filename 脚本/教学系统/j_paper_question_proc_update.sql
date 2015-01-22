DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_question_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_question_proc_update`(
					   p_ref INT,
				          p_paper_id BIGINT,
				          p_question_id BIGINT,
				          p_order_idx INT,
				          p_score float,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_paper_question set c_time=c_time ';
	
	
	
	
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ORDER_IDX=",p_order_idx);
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=",p_score);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PAPER_ID=",p_paper_id);
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUESTION_ID=",p_question_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
