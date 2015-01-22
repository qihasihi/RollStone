DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_resource_info_proc_union`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_j_course_resource_info_proc_union`(p_course_id BIGINT,
							p_taskflag INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(500),
							OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.ref,u.c_time ctime,u.operate_time,tc.course_name,tc.quote_id,u.course_id,u.resource_type ,r.*,ifnull(ifnull(t.teacher_name,s.stu_name),ui.user_name)real_name';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '  1=1 ';  
	DECLARE tmp_count_sql VARCHAR(2000) DEFAULT ' ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_j_course_resource_info u inner join rs_resource_info r on r.res_id=u.res_id
	left join user_info ui on r.user_id=ui.user_id
	inner join tp_course_info tc on tc.course_id=u.course_id 
	left join teacher_info t on t.user_id=ui.ref 
	left join student_info s on s.user_id=ui.ref
	 '; 
	DECLARE idx INT DEFAULT 0;
	DECLARE total INT DEFAULT 0;
	DECLARE courseids VARCHAR(2000) DEFAULT '';
	SELECT GROUP_CONCAT(related_course_id) INTO @ids FROM `tp_j_course_related_info` tr WHERE tr.course_id=p_course_id;
	SET courseids = @ids;
	IF p_course_id IS NOT NULL THEN
		/*SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);*/
		
		SET total = get_split_string_total(courseids,',');
		SET idx = 0;
		SET tmp_search_condition=CONCAT(tmp_search_condition," and( u.course_id=",p_course_id);
		WHILE idx<total DO
			SET tmp_search_condition=CONCAT(tmp_search_condition," or u.COURSE_ID=",get_split_string(courseids,',',idx+1));
			/*IF idx<total-1 THEN
				SET tmp_search_condition=CONCAT(tmp_search_condition," or ");
			END IF;*/
			SET idx = idx+1;
		END WHILE;
		SET tmp_search_condition=CONCAT(tmp_search_condition," )");
	END IF;
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," and r.diff_type<>1");
	
	IF p_taskflag IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and  u.res_id not in(SELECT ifnull(GROUP_CONCAT(t.task_value_id),0)  FROM tp_task_allot_info ta,tp_task_info t WHERE ta.task_id=t.task_id AND t.course_id=u.course_id) ");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_sql=CONCAT("SELECT aa.*,
		IFNULL(d1.DICTIONARY_NAME,'--') RES_TYPE_NAME,
		IFNULL(d2.DICTIONARY_NAME,'--') FILE_TYPE_NAME FROM (",tmp_sql,") aa 
		LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=aa.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'  
		LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=aa.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE'");
	SET tmp_count_sql=tmp_sql;
		
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	ELSE
	    SET tmp_sql = CONCAT(tmp_sql," order by if(course_id=",p_course_id,",-99999999999999,course_id)");
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql," )t ");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
    END$$

DELIMITER ;