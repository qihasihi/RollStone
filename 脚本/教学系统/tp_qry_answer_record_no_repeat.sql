DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_answer_record_no_repeat`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_answer_record_no_repeat`(
				          p_task_id VARCHAR(50),
				          p_group_id VARCHAR(50)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' DISTINCT tp.TASK_ID,tp.USER_ID,ar.QUES_PARENT_ID,tp.GROUP_ID,tp.COURSE_ID ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_ques_answer_record  ar LEFT JOIN tp_task_performance tp   ON ar.task_id=tp.task_id '; 
	
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and (tp.TASK_ID='",p_task_id,"' or ar.TASK_ID='",p_task_id,"' )");
	END IF;
	
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and (tp.GROUP_ID='",p_group_id,"' or ar.GROUP_ID='",p_group_id,"' )");
	END IF;
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET tmp_sql=CONCAT(tmp_sql," UNION SELECT ",tmp_search_column," FROM tp_task_performance tp  LEFT JOIN  tp_ques_answer_record  ar   ON ar.task_id=tp.task_id WHERE ",tmp_search_condition);
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
