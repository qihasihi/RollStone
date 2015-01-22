DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `stu_paper_ques_logs_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `stu_paper_ques_logs_proc_split`(
				          p_ref INT,
				          p_paper_id BIGINT,
				          p_user_id INT,
				          p_ques_id BIGINT,
				          p_is_right INT,
				          p_answer VARCHAR(2000),
				          p_task_id BIGINT,
				          p_ismarking INT,
				          p_class_id BIGINT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,s.stu_no,s.stu_name,ui.ETT_USER_ID ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 and u.user_id=ui.user_id AND ui.ref=s.user_id ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'stu_paper_ques_logs u,student_info s,user_info ui'; 
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID=",p_paper_id);
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.IS_RIGHT=",p_is_right);
	END IF;
	
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.task_id=",p_task_id);
	END IF;
	IF p_ismarking IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.is_marking=",p_ismarking);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID=",p_user_id);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.user_id IN (SELECT u.user_id FROM j_class_user cu,user_info u WHERE u.ref=cu.`USER_ID` AND cu.CLASS_ID=",p_class_id," )");
	END IF;
	
	
	IF p_answer IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ANSWER='",p_answer,"'");
	END IF;
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUES_ID=",p_ques_id);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	    ELSE
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  u.task_id,u.paper_id,u.ques_id ");
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
