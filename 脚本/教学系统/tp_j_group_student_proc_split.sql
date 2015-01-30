DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `tp_j_group_student_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_group_student_proc_split`(
					p_ref INT,
				          p_group_id BIGINT,  
				          p_user_id INT,
				          p_isleader INT,
				          p_state_id INT,
				          p_gsubject_id INT,
					 p_class_id INT,
				          							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' gs.*,s.stu_name,s.stu_no,g.group_name,u.password,u.ett_user_id ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' gs.user_id=u.user_id AND u.REF=s.USER_ID and gs.group_id=g.group_id AND cu.class_id=g.class_id  AND cu.`USER_ID`=u.`REF`  ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_j_group_student gs,tp_group_info g,user_info u,student_info s,j_class_user cu '; 
	
	
	
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gs.GROUP_ID=",p_group_id);
		SET tmp_search_column=CONCAT(tmp_search_column," ,ifnull(Round((SELECT COUNT(*) FROM tp_task_performance tp WHERE tp.TASK_ID IN (SELECT  task_id FROM tp_task_allot_info t WHERE t.user_type=2 and now()>t.e_time AND t.user_type_id=",p_group_id,") AND tp.user_id=u.ref)/(SELECT  COUNT(task_id) FROM tp_task_allot_info t WHERE t.user_type=2  AND now()>t.e_time AND t.user_type_id=",p_group_id,")*100,2),0)complete_num");
		
	END IF;
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.class_id=",p_class_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gs.REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gs.USER_ID=",p_user_id);
	END IF;
	
	IF p_gsubject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.subject_id=",p_gsubject_id);
	END IF;
	
	IF p_state_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.STATE_ID=",p_state_id);
	END IF;
	
	IF p_isleader IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gs.ISLEADER=",p_isleader);
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