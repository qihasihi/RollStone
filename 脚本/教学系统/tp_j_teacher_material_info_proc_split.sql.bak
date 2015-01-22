DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_teacher_material_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_j_teacher_material_info_proc_split`(
							p_user_id INT,
							p_subject_id INT ,
							p_material_id INT,
							p_grade_id INT,
							p_term_id VARCHAR(100),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " u.*";
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_j_teacher_material_info u'; 	
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.user_id=",p_user_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.subject_id=",p_subject_id);
	END IF;
	IF p_material_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.material_id=",p_material_id);
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TERM_ID ='",p_term_id,"'");
	END IF;	
	
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.grade_id=",p_grade_id);
	END IF;	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT t.*,g.grade_name,tm.material_name,v.version_name FROM (",tmp_sql,")t 
			INNER JOIN teaching_material_info tm ON tm.material_id=t.material_id
			LEFT JOIN teach_version_info v ON v.version_id=tm.version_id
			INNER JOIN grade_info g on tm.grade_id=g.grade_id ");
	
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