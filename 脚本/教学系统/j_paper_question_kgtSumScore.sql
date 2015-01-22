DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_question_kgtSumScore`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_question_kgtSumScore`(
					p_paper_id BIGINT,
					p_user_id BIGINT,
					p_task_id BIGINT					
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET tmp_sql=CONCAT('	  
		SELECT SUM(score) SUM_SCORE FROM stu_paper_ques_logs WHERE user_id=',p_user_id,' AND paper_id=',p_paper_id,' AND task_id=',p_task_id,'
	');
	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
END $$

DELIMITER ;
