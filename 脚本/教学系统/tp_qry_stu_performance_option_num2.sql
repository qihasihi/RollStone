DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_performance_option_num2`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_performance_option_num2`(p_taskid BIGINT,p_classid BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql=concat(tmp_sql,' SELECT t.ques_id,j.option_type,COUNT(t.ques_id) num FROM',
			' tp_ques_answer_record t RIGHT JOIN tp_task_allot_info ta ON t.TASK_ID=ta.task_id',
			' LEFT JOIN j_question_option j ON t.QUES_ID=j.ref  ',
			' INNER JOIN tp_group_info ti ON ti.group_id=ta.user_type_id',
			' inner join tp_j_group_student tj on ti.group_id=tj.group_id',
			' inner join user_info u on tj.user_id=u.user_id',
			' WHERE 1=1 and t.user_id=u.ref');
	SET tmp_sql=CONCAT(tmp_sql," AND t.task_id= ",p_taskid);
	IF p_classid IS NOT NULL AND p_classid<>0 THEN
		SET tmp_sql=CONCAT(tmp_sql," and ti.class_id=",p_classid);
	END IF;
	SET tmp_sql=CONCAT(tmp_sql," GROUP BY t.ques_id");
	 
     
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
