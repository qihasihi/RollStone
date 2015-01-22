DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_ques_info_getZQLV`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_ques_info_getZQLV`(
							  p_papaerid BIGINT,
							  p_quesid BIGINT,							  
							  p_classid BIGINT,
							  p_task_id BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	 
		 SET tmp_sql=CONCAT('		 
		SELECT  
		FORMAT((	SELECT COUNT(DISTINCT user_id) FROM stu_paper_ques_logs  sl WHERE paper_id=',p_papaerid,' AND sl.ques_id=qo.question_id AND sl.task_id=',p_task_id,'
			AND user_id IN(
			SELECT u.user_id FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref  AND cu.relation_type="学生" AND cu.class_id=',p_classid,'
			) AND sl.is_right=1
		)
		/
		(SELECT COUNT(DISTINCT user_id) FROM stu_paper_ques_logs WHERE ques_id=qo.question_id AND paper_id=',p_papaerid,' AND task_id=',p_task_id,'
			AND user_id IN(
			SELECT u.user_id FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref  AND cu.relation_type="学生" AND cu.class_id=',p_classid,'
			)
		)*100,2) ZQL
		 FROM j_question_option qo
		WHERE qo.is_right=1 AND  question_id=',p_quesid);
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
END $$

DELIMITER ;
