DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_ques_answer_record_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_ques_answer_record_proc_split`(
				          p_ref INT,
				          p_ques_id bigint,
				          p_ques_parent_id bigint,
				          p_user_id varchar(50),
				          p_task_id bigint,
				          p_course_id bigint,
				          p_group_id bigint,
				          p_task_type INT,
				          p_right_answer INT,
				          p_cls_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' ar.*,IFNULL(IFNULL(t.teacher_name,s.stu_name),c.user_name)realname ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_ques_answer_record ar INNER JOIN user_info c ON  ar.user_id=c.ref LEFT JOIN teacher_info t ON t.user_id=c.ref LEFT JOIN student_info s ON s.user_id=c.ref '; 
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.TASK_ID=",p_task_id);
	END IF;
	
	IF p_ques_parent_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.QUES_PARENT_ID=",p_ques_parent_id);
	END IF;
	IF p_cls_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.ref IN (");
		SET tmp_search_condition=CONCAT(tmp_search_condition,"SELECT user_id FROM j_class_user where class_id=",p_cls_id);		
		SET tmp_search_condition=CONCAT(tmp_search_condition,")");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.QUES_ID=",p_ques_id);
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.GROUP_ID=",p_group_id);
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.TASK_TYPE=",p_task_type);
	END IF;
	
	IF p_right_answer IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ar.RIGHT_ANSWER=",p_right_answer);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
