DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_resource_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_resource_proc_split`(
				          p_course_id BIGINT,
				          p_course_name VARCHAR(100),
				          p_course_level INT,
				          p_material_id int,
				          p_grade_id INT,
				          p_subject_id int,
				          p_c_user_id	 varchar(50),
				          p_current_course_id BIGINT,
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' distinct tc.*,tv.version_name,tm.grade_id,tm.subject_id,tm.material_name,g.grade_name,(SELECT COUNT(*) FROM tp_j_course_resource_info tr WHERE tr.course_id=tc.COURSE_ID) res_num ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT '  tp_course_info tc inner JOIN  tp_j_course_teaching_material tcm ON tc.course_id=tcm.course_id
inner JOIN teaching_material_info tm ON tm.material_id=tcm.teaching_material_id 
inner JOIN grade_info g ON  g.grade_id=tm.grade_id 
inner join teach_version_info tv on tm.version_id=tv.version_id '; 
	DECLARE tmp_count_sql VARCHAR(5000) DEFAULT '';
	
	
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_NAME LIKE '%",p_course_name,"%'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.cuser_id = '",p_c_user_id,"'");
	END IF;
	
	IF p_course_level IS NOT NULL THEN
		if p_course_level=1 then
			SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_LEVEL=3");
		ELSEif p_course_level=-1 THEN    
			SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_LEVEL<>3");
	        elseif p_course_level=2 and p_current_course_id is not null then 
			SET tmp_tbl_name=CONCAT(tmp_tbl_name," INNER JOIN tp_j_course_related_info cr ON tc.course_id=cr.related_course_id ");
			SET tmp_search_condition=CONCAT(tmp_search_condition," and cr.COURSE_ID=",p_current_course_id);
		end if;
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_material_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tcm.teaching_material_id=",p_material_id);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	set tmp_count_sql=tmp_sql;
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql,")t");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
