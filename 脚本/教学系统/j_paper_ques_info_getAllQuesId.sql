DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_ques_info_getAllQuesId`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_ques_info_getAllQuesId`(
							  p_papaerid BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	

	
	 set tmp_sql=CONCAT('		 
		SELECT GROUP_CONCAT(CASE WHEN
		q.question_type>=6 THEN
		(SELECT GROUP_CONCAT(q.question_id,"|",ques_id) FROM j_ques_team_rela qtr,question_info qt WHERE qtr.ques_id=qt.question_id AND  team_id=q.question_id)
		ELSE
		q.question_id
		END) allQuesId
		 FROM (
		SELECT question_id FROM j_paper_question WHERE paper_id=',p_papaerid,' order by order_idx
		) pq,question_info q 
		WHERE pq.question_id=q.question_id		
	 ');	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
END $$

DELIMITER ;
