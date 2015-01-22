DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_mic_perform_option_num`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_mic_perform_option_num`(p_taskid BIGINT,p_question_id BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql='SELECT j.option_type,COUNT(l.answer)num
		FROM   stu_paper_ques_logs l 
			INNER JOIN j_mic_video_paper p ON p.paper_id=l.paper_id 
			INNER JOIN tp_task_info t ON  t.task_value_id=p.mic_video_id
			INNER JOIN j_question_option j ON  j.question_id=l.ques_id
		WHERE 1=1 ';
	SET tmp_sql=CONCAT(tmp_sql," AND t.task_id= ",p_taskid);
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and l.ques_id=",p_question_id);
	END IF;
	SET tmp_sql=CONCAT(tmp_sql," AND INSTR(REPLACE(l.answer,'%7C',','),j.option_type)>0 GROUP BY option_type");
	 
     
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
