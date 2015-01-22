DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_ques_team_rela_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_ques_team_rela_proc_update`(
				            p_order_id INT,				         
				            p_ques_id BIGINT,
				               p_team_id BIGINT,
				               p_ref BIGINT,
				               p_score Float,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_ques_team_rela set c_time=c_time ';
	
	
	
	IF p_order_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ORDER_ID=",p_order_id);
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",score=",p_score);
	END IF;
	
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	
	IF p_team_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TEAM_ID=",p_team_id);
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUES_ID=",p_ques_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
