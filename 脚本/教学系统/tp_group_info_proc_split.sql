DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_info_proc_split`(
				          p_group_id BIGINT,
				          p_class_id INT,
				          p_class_type INT,
				          p_c_user_id INT,
				          p_group_name VARCHAR(100),
				          p_term_id VARCHAR(50),
				          p_stu_userid INT,
				          p_subject_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' distinct u.*,(select t.teacher_name from teacher_info t,user_info cu where t.user_id=cu.ref and cu.user_id=u.c_user_id)teacher_name,
	(SELECT COUNT(*) FROM tp_j_group_student gs WHERE gs.group_id=u.group_id)group_num ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_group_info u LEFT JOIN j_class_user cu ON u.CLASS_ID=cu.CLASS_ID AND cu.RELATION_TYPE=''»ŒøŒ¿œ ¶'' AND u.CLASS_TYPE=1 '; 
	
        	
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.GROUP_ID=",p_group_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.subject_id=",p_subject_id);
	END IF;
	
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLASS_ID=",p_class_id);
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLASS_TYPE=",p_class_type);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID=",p_c_user_id);
	END IF;
	
	
	IF p_group_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.GROUP_NAME='",p_group_name,"'");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TERM_ID='",p_term_id,"'");
	END IF;
	
	IF p_stu_userid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and exists (select 1 from tp_j_group_student t where t.user_id=",p_stu_userid," and t.group_id=u.group_id )");
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	ELSE
		SET tmp_sql=CONCAT(tmp_sql," order by u.c_time asc");
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
