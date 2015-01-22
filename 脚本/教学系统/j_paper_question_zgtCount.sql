DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_question_zgtCount`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_question_zgtCount`(
					p_paper_id BIGINT					
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET tmp_sql=CONCAT('	  
		SELECT COUNT(question_id) zgtCount
		 FROM question_info q WHERE question_id IN (
		SELECT question_id FROM j_paper_question WHERE paper_id=',p_paper_id,'
		)
		AND (question_type<>3 AND question_type<>4 AND question_type<>7 AND question_type<>8 AND question_type<>6  OR (
				question_type=6 AND  EXISTS(
				  SELECT question_id FROM j_ques_team_rela tr ,question_info q1 
				  WHERE tr.team_id=q.question_id AND tr.ques_id=q1.question_id
				  AND q1.question_type NOT IN (7,8,3,4)
				)
			)
		)	
	');
	
	
	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
END $$

DELIMITER ;
