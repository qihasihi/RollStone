DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_ques_count`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_ques_count`(
				          p_paper_id BIGINT,
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(q.question_id) INTO @tmp_sumCount FROM question_info q,(SELECT IFNULL(t.ques_id,p.question_id)qid FROM  j_paper_question p LEFT JOIN j_ques_team_rela t ON p.`question_id`=t.`team_id`
WHERE p.paper_id=",p_paper_id,")a
WHERE q.question_id=a.qid");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
