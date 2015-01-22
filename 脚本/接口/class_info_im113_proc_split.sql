DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_info_im113_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `class_info_im113_proc_split`(
				          
				           p_dc_school_id INT,				         
				          p_cuser_id INT,
				          p_class_id INT,
				          p_search_uid INT,
							p_current_page INT,
							p_page_size INT,
							p_sort_column VARCHAR(100),
							
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(1000) DEFAULT 'class_info u'; 
	
	
	IF  p_dc_school_id  IS NOT NULL AND  p_dc_school_id>0 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.dc_school_id=",p_dc_school_id);
	END IF;
	IF  p_search_uid  IS NOT NULL AND  p_search_uid>0 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.c_user_id=",p_search_uid);
	END IF;
	IF  p_class_id  IS NOT NULL  THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLASS_ID=",p_class_id);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT u.*,
       IFNULL((SELECT COUNT(*) FROM j_class_user cu WHERE cu.class_id=u.CLASS_ID AND cu.relation_type='学生'),0)NUM ,
       (SELECT subject_name FROM subject_info WHERE subject_id=u.subject_id) subjectname,
       (SELECT class_year_name FROM class_year_info where class_year_value=u.year) classyearname");
           SET tmp_sql=CONCAT(tmp_sql,",(SELECT COUNT(ref) FROM j_myinfo_user_info WHERE class_id=u.class_id AND template_id=18 ");
       IF p_dc_school_id IS NOT NULL AND p_cuser_id IS NOT NULL THEN	
		
	SET tmp_sql=CONCAT(tmp_sql,"	AND c_time>
		IFNULL((SELECT MAX(c_time)FROM j_myinfo_user_info WHERE class_id=u.class_id
			AND template_id=19
			AND user_ref=(SELECT ref FROM user_info WHERE user_id=",p_cuser_id,")),'2000-01-01')							
							");		
	END IF;
       SET tmp_sql=CONCAT(tmp_sql,") dynamicCount FROM (SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition,") u");	
	
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
	SET @sql2=tmp_sql;
	PREPARE stmt FROM @sql2  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;