DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_remind_allotobj_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_task_remind_allotobj_split`(
					  p_task_id BIGINT,
				          p_course_id BIGINT,
				          p_user_type INT,
				          p_user_type_id BIGINT,
				          p_c_user_id VARCHAR(1000),
				          p_sel_type INT,
				          p_remind_status INT,
				          p_btime VARCHAR(200),
				          p_etime VARCHAR(200),
				          p_task_type INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(4000) DEFAULT ' distinct  u.ref,u.user_type_id,u.user_type  ';  
	DECLARE tmp_search_condition VARCHAR(4000) DEFAULT ' 1=1 and  u.task_id=t.task_id and t.status=1 ';  
	DECLARE tmp_tbl_name VARCHAR(4000) DEFAULT 'tp_task_allot_info u,tp_task_info t'; 
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_task_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.task_type=",p_task_type);
	END IF;
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_TYPE=",p_user_type);
	END IF;
	
	IF p_remind_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.remind_status=",p_remind_status);
	END IF;
	
	
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	IF p_user_type_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_TYPE_ID=",p_user_type_id);
	END IF;
	
	IF p_sel_type IS NOT NULL THEN
		IF p_sel_type=1 THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.b_time 
BETWEEN DATE_ADD(STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d %H:'),'00:00'),'%Y-%m-%d %H:%i:%s'),INTERVAL -1 HOUR)
 AND  STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d %H:'),'00:00'),'%Y-%m-%d %H:%i:%s')
 AND NOW() NOT BETWEEN DATE_ADD(STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'22:00:00'),'%Y-%m-%d %H:%i:%s'),INTERVAL -1 DAY)
 AND STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'08:01:00'),'%Y-%m-%d %H:%i:%s')");
	        ELSE
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.b_time 
BETWEEN DATE_ADD(STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'22:00:00'),'%Y-%m-%d %H:%i:%s'),INTERVAL -1 DAY)
 AND  STR_TO_DATE(CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),'08:01:00'),'%Y-%m-%d %H:%i:%s') ");
		END IF;
		
	END IF;
	
	
	IF p_btime IS NOT NULL AND p_etime IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.b_time <STR_TO_DATE('",p_etime,"','%Y-%m-%d %H:%i:%s') and  u.e_time >STR_TO_DATE('",p_btime,"','%Y-%m-%d %H:%i:%s')   ");
	END IF;
	
	
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TASK_ID=",p_task_id);
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
	
    END$$

DELIMITER ;