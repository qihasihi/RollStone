DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_suggest_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_suggest_info_proc_split`(
						p_ref	int,
						p_task_id VARCHAR(50),
						p_course_id VARCHAR(50),
						p_user_id varchar(50),
						p_is_anonymous INT,
						p_login_id int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,t.task_name,IFNULL(IFNULL(tt.teacher_name,s.stu_name),c.user_name)realname ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 and t.status<>2';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_task_suggest_info u INNER JOIN user_info c ON c.ref=u.user_id inner join tp_task_info t on u.task_id=t.task_id and u.course_id=t.course_id
                       LEFT JOIN teacher_info tt ON tt.user_id=c.ref
                       LEFT JOIN student_info s ON s.user_id=c.ref   '; 
                       
                       
	IF p_login_id IS NOT NULL THEN	
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND NOT EXISTS (SELECT t.ref FROM tp_operate_info t WHERE t.course_id=u.COURSE_ID AND t.target_id=u.TASK_ID AND t.operate_type=1 AND t.c_user_id=",p_login_id,")");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN	
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_is_anonymous IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.IS_ANONYMOUS=",p_is_anonymous);
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_ID='",p_task_id,"'");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID='",p_course_id,"'");
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
