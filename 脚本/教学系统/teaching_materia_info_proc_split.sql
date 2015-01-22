DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teaching_materia_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teaching_materia_info_proc_split`(
				          p_version_id INT,
				          p_material_name VARCHAR(1000),
				          p_remark VARCHAR(1000),
				          p_c_user_id VARCHAR(1000),
				          p_grade_id INT,
				          p_subject_id INT,
				          p_material_id INT,
				          p_type int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,v.version_name ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' teaching_material_info u left join teach_version_info v on u.version_id=v.version_id '; 
	
	IF p_version_id IS NOT NULL THEN 
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.VERSION_ID=",p_version_id);
	END IF;
	
	IF p_material_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.MATERIAL_NAME='",p_material_name,"'");
	END IF;
	
	IF p_remark IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REMARK='",p_remark,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_material_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.MATERIAL_ID=",p_material_id);
	END IF;
	
	if p_type is not null then
		
		IF p_type=1 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.material_name not like '%理科%'");
		END IF;
		IF p_type=2 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.material_name not like '%文科%'");
		END IF;
		
		
	end if;
	
	
	SET tmp_sql=CONCAT("
	SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY ",p_sort_column);
	    ELSE
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY IF(u.material_id<0,99999999999,u.material_id) ASC");
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT t.*,sub.subject_name,g.grade_name FROM (",tmp_sql,") t LEFT JOIN subject_info sub ON t.subject_id=sub.subject_id
		LEFT JOIN grade_info g ON t.grade_id=g.grade_id
		");
	
	
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
