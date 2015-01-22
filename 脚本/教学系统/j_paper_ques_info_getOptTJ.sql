DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_ques_info_getOptTJ`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_ques_info_getOptTJ`(
							  p_papaerid BIGINT,
							  p_quesid BIGINT,
							  p_classid BIGINT,
							  p_task_id BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
		 SET tmp_sql=CONCAT('		 
			SELECT option_type,			
			 IFNULL(FORMAT(
			(SELECT COUNT(DISTINCT user_id) FROM stu_paper_ques_logs sq WHERE ques_id=qo.question_id AND paper_id=',p_papaerid,' AND LOCATE(qo.option_type,answer)>0
				AND sq.task_id=',p_task_id,'
				AND user_id IN(
					SELECT u.user_id FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref  AND cu.relation_type="学生" AND cu.class_id=',p_classid,'
				)
			)
			/(SELECT COUNT(DISTINCT user_id) FROM stu_paper_ques_logs WHERE ques_id=qo.question_id AND paper_id=',p_papaerid,'
					AND task_id=',p_task_id,'
					AND user_id IN(
						SELECT u.user_id FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref  AND cu.relation_type="学生" AND cu.class_id=',p_classid,'
					)
			
			)*100,2),0)
			bili
			 FROM j_question_option qo WHERE qo.question_id=',p_quesid,' ORDER BY qo.question_id,qo.option_type '
		);
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
END $$

DELIMITER ;
