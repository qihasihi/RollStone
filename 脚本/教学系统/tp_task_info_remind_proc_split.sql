DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_info_remind_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_task_info_remind_proc_split`(
				          p_sel_type INT,
				          p_task_id  BIGINT,
					p_current_page INT(10),
					p_page_size INT(10),
					p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT 'distinct u.*,ifnull(tea.teacher_name,ui.user_name)realname,ui.user_id,ui.ett_user_id,(CASE u.task_type 
	WHEN 1 THEN (SELECT CONCAT(res_name,file_suffixname) FROM rs_resource_info r WHERE r.res_id=u.TASK_VALUE_ID)
	WHEN 2 THEN (SELECT topic_title FROM tp_topic_info t WHERE t.topic_id=u.TASK_VALUE_ID)
	WHEN 3 THEN  NULL
	WHEN 4 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=u.TASK_VALUE_ID)
	WHEN 5 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=u.TASK_VALUE_ID)
	WHEN 6 THEN (SELECT res_name FROM rs_resource_info r WHERE r.res_id=u.TASK_VALUE_ID)
	WHEN 7 THEN task_name
	WHEN 8 THEN task_name
	WHEN 9 THEN task_name
	WHEN 10 THEN task_name
	END
 )taskobjname  ';  
	DECLARE tmp_search_condition VARCHAR(4000) DEFAULT ' 1=1 and ui.ref=ta.c_user_id and ui.ref=tea.user_id and u.task_id=ta.task_id and u.status=1 and ta.remind_status=0 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_task_info u ,tp_task_allot_info ta ,user_info ui,teacher_info tea'; 
	
	IF p_sel_type IS NOT NULL THEN
		IF p_sel_type=1 THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and ta.b_time 
BETWEEN DATE_ADD(STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d %H:'),'00:00'),'%Y-%m-%d %H:%i:%s'),INTERVAL -1 HOUR)
 AND  STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d %H:'),'00:00'),'%Y-%m-%d %H:%i:%s')
 AND NOW() NOT BETWEEN  DATE_ADD(STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'22:00:00'),'%Y-%m-%d %H:%i:%s'),INTERVAL -1 DAY)
 AND  STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'08:00:00'),'%Y-%m-%d %H:%i:%s')");
	        ELSEIF p_sel_type=2 THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and ta.b_time 
BETWEEN  DATE_ADD(STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'22:00:00'),'%Y-%m-%d %H:%i:%s'),INTERVAL -1 DAY)
 AND  STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'08:00:00'),'%Y-%m-%d %H:%i:%s') ");
 
		ELSEIF p_sel_type=3 AND p_task_id IS NOT NULL THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and ta.b_time <=DATE_ADD(NOW(),INTERVAL 1 MINUTE) and u.task_id=",p_task_id,"");
     
		END IF;
		
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	ELSE
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ui.user_id ");	
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
	
    END$$

DELIMITER ;