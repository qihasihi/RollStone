DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_performance_option_num`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_performance_option_num`(p_taskid BIGINT,p_classid BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(2000) DEFAULT '';
	DECLARE user_type VARCHAR(100) DEFAULT '';
	IF p_classid IS NOT NULL AND p_classid<>0 THEN
		SET tmp_sql2 = CONCAT("select user_type into @user_type from tp_task_allot_info where task_id=",p_taskid," and user_type_id=",p_classid);
		SET @sql2=tmp_sql2;
		PREPARE s2 FROM @sql2;
		EXECUTE s2;
		SET user_type=@user_type;
	END IF;
	SET tmp_sql='SELECT t.ques_id,j.option_type,COUNT(t.ques_id) num FROM 
			tp_ques_answer_record t LEFT JOIN tp_task_allot_info ta ON t.TASK_ID=ta.task_id';
	IF user_type = 0 THEN 
		SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN j_class_user jc ON jc.class_id=ta.user_type_id AND jc.user_id=t.USER_ID");
	END IF;
	IF user_type = 1 THEN
		SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN user_info u ON t.user_id=u.ref LEFT JOIN tp_j_virtual_class_student jv ON jv.user_id=u.user_id");
	END IF;
			
			
	SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN j_question_option j ON t.QUES_ID=j.ref  WHERE 1=1");
	SET tmp_sql=CONCAT(tmp_sql," AND t.task_id= ",p_taskid);
	IF p_classid IS NOT NULL AND p_classid<>0 THEN
		IF user_type = 0 THEN 
			SET tmp_sql = CONCAT(tmp_sql," and jc.class_id=",p_classid);
		END IF;
		IF user_type = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," and jv.virtual_class_id=",p_classid);
		END IF;		
	END IF;
	SET tmp_sql=CONCAT(tmp_sql," GROUP BY t.ques_id");
	 
     
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
