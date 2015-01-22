DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_split_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_split_list`(
				          p_course_id BIGINT,
					  p_term_id VARCHAR(50),
					  p_user_id INT,
					  p_subject_id INT,
					  p_grade_id INT,
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column INT(1),
				          OUT sumCount VARCHAR(50)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' distinct tc.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 AND tc.course_id=t.course_id AND tc.course_id=tcm.course_id
 AND tcm.teaching_material_id=tm.material_id  AND tc.local_status=1  ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_course_info tc,tp_j_course_class t,tp_j_course_teaching_material tcm,teaching_material_info tm '; 
	
	
	
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.subject_id=",p_subject_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.grade_id=",p_grade_id);
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.TERM_ID='",p_term_id,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.USER_ID=",p_user_id);
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
	
	SET tmp_sql2=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql2=tmp_sql2;
	PREPARE stmt2 FROM @tmp_sql2  ;
	EXECUTE stmt2;
	DEALLOCATE PREPARE stmt2;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
