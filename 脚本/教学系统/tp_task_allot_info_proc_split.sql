DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_allot_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_task_allot_info_proc_split`(
					  p_task_id BIGINT,
				          p_course_id BIGINT,
				          p_user_type INT,
				          p_user_type_id BIGINT,
				          p_c_user_id VARCHAR(1000),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(4000) DEFAULT ' u.*,(CASE u.user_type 
 WHEN 0 THEN (SELECT CONCAT(c.CLASS_GRADE,c.CLASS_NAME) FROM class_info c WHERE c.CLASS_ID=u.user_type_id)
 WHEN 1 THEN (SELECT v.virtual_class_name FROM tp_virtual_class_info v WHERE v.VIRTUAL_CLASS_ID=u.user_type_id)
 WHEN 2 THEN (SELECT g.group_name FROM tp_group_info g,class_info c WHERE g.class_id=c.class_id AND g.GROUP_ID=u.user_type_id) END )allotobj,
 (CASE u.user_type 
 WHEN 0 THEN (SELECT class_id FROM class_info c WHERE c.CLASS_ID=u.user_type_id)
 WHEN 1 THEN (SELECT v.VIRTUAL_CLASS_ID FROM tp_virtual_class_info v WHERE v.VIRTUAL_CLASS_ID=u.user_type_id)
 WHEN 2 THEN (SELECT c.class_id FROM tp_group_info g,class_info c WHERE g.class_id=c.class_id AND g.GROUP_ID=u.user_type_id) END )allot_id,
 (CASE u.user_type 
 WHEN 2 THEN (SELECT CONCAT(c.class_grade,c.class_name) FROM tp_group_info g,class_info c WHERE g.class_id=c.class_id AND g.GROUP_ID=u.user_type_id) END )allot_name,
  (CASE u.user_type  WHEN 0 THEN (SELECT c.dc_type FROM class_info c WHERE c.class_id=u.user_type_id) ELSE -1 END) class_type,
 (SELECT dc_school_id FROM tp_course_info tc,tp_task_info t WHERE tc.course_id=t.course_id AND t.task_id=u.task_id)dc_school_id';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_task_allot_info u'; 
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_TYPE=",p_user_type);
	END IF;
	
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	IF p_user_type_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_TYPE_ID=",p_user_type_id);
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