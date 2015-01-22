DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `stu_paper_logs_scoresum`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `stu_paper_logs_scoresum`(				       
				          p_user_id INT,
				          p_paper_id BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET tmp_sql=CONCAT("SELECT  IFNULL(SUM(IFNULL(sq.score,0)),0) v_score FROM stu_paper_ques_logs sq,question_info q 
			WHERE q.question_id=sq.ques_id 
			AND (q.question_type=3 OR q.question_type=4)
			AND sq.user_id=",p_user_id," AND sq.paper_id=",p_paper_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
END $$

DELIMITER ;
