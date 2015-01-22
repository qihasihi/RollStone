DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_ques_answer_stunote_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_ques_answer_stunote_split`(
				          p_ques_id BIGINT,
				          p_ques_parent_id BIGINT,
				          p_user_id VARCHAR(50),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE order_tmp VARCHAR(2000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT '  t.*,IFNULL(IFNULL(sr.stu_name,tr.teacher_name),
		r.user_name)rrealname,IFNULL(IFNULL(sc.stu_name,tc.teacher_name),c.user_name)crealname,
		IFNULL(IFNULL(tc.img_heard_src,c.head_image),''images/defaultheadsrc.jpg'')headimage   ';  
		
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 and t.task_type=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT '  tp_ques_answer_record t 
	LEFT JOIN user_info r ON r.ref=t.REPLY_USER_ID LEFT JOIN student_info sr ON sr.user_id=t.REPLY_USER_ID
	LEFT JOIN  teacher_info tr ON tr.user_id=t.REPLY_USER_ID
	INNER JOIN user_info c ON c.ref=t.USER_ID
	LEFT JOIN student_info sc ON sc.user_id =t.USER_ID
	LEFT JOIN teacher_info tc ON tc.user_id= t.USER_ID '; 
        	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.user_id='",p_user_id,"'");
	END IF;
	
	
	IF p_ques_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.ques_id=",p_ques_id);
	END IF;
	
	
	IF p_ques_parent_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.ques_parent_id=",p_ques_parent_id);
	END IF;
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET order_tmp=CONCAT(order_tmp," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
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
