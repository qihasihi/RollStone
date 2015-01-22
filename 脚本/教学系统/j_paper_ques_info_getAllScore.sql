DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_ques_info_getAllScore`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_ques_info_getAllScore`(							
							  p_papaerid BIGINT,
							  p_quesid BIGINT,
							  p_course_id BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	

	
	
	
			
	 
      	IF p_quesid IS NULL THEN 
		 SET tmp_sql=CONCAT('	
				 SELECT
				GROUP_CONCAT(CASE t.question_type 
					WHEN 6 THEN 
					qts.score
					ELSE
					   t.score
					 END)
					 score
					 FROM 	
					 ( 
					SELECT q.question_type,q.question_id,pq.score				
					FROM j_paper_question pq,question_info q 
					WHERE pq.question_id=q.question_id AND pq.paper_id=',p_papaerid,'
					ORDER BY pq.order_idx
				) t
				LEFT JOIN (
					 SELECT GROUP_CONCAT(IFNULL(pqts.score,qtr.score)) score,qtr.team_id FROM j_ques_team_rela qtr
					  INNER JOIN question_info tmpq ON tmpq.question_id=qtr.ques_id
					LEFT JOIN j_paper_ques_team_score pqts  ON pqts.ques_ref=qtr.ref AND pqts.paper_id=',p_papaerid,' AND pqts.course_id=',p_course_id,'
					WHERE 1=1 
					GROUP BY qtr.team_id
					ORDER BY qtr.order_id			
				) qts ON qts.team_id=t.question_id
				
		');
		
	ELSE
		SET tmp_sql=CONCAT('		 
		SELECT IFNULL(t.score,pq.score) score FROM j_paper_question pq 
		LEFT JOIN 
		(
		SELECT IFNULL(pqts.score,qtr.score) score,qtr.team_id,qtr.ques_id
		FROM j_ques_team_rela qtr
		 INNER JOIN question_info tmpq ON tmpq.question_id=qtr.ques_id
		LEFT JOIN j_paper_ques_team_score pqts  ON pqts.ques_ref=qtr.ref AND pqts.paper_id=',p_papaerid,' AND pqts.course_id=',p_course_id,'
		)  t ON t.team_id=pq.question_id 
		where pq.paper_id=',p_papaerid,' AND IFNULL(t.ques_id,pq.question_id)=',p_quesid);
		
		
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
END $$

DELIMITER ;
