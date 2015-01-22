DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_score_list_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_score_list_split`(
				         p_course_id   BIGINT,
				         p_class_id INT,
				         p_class_type  INT,
				         p_subject_id iNT,
				         p_group_id BIGINT,
				         p_dcschool_id BIGINT,
				         	p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				         
				   )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_group_score u'; 
	
       IF p_course_id IS NOT NULL THEN
	SET tmp_search_condition=CONCAT(tmp_search_condition,' AND u.course_id=',p_course_id);
       END IF;
       IF p_class_id IS NOT NULL THEN
	SET tmp_search_condition=CONCAT(tmp_search_condition,' AND u.class_id=',p_class_id);
       END IF;
       IF p_class_type IS NOT NULL THEN
	SET tmp_search_condition=CONCAT(tmp_search_condition,' AND u.class_type=',p_class_type);
       END IF;
       IF p_subject_id IS NOT NULL THEN
	SET tmp_search_condition=CONCAT(tmp_search_condition,' AND u.subject_id=',p_subject_id);
       END IF;
       IF p_group_id IS NOT NULL THEN
	SET tmp_search_condition=CONCAT(tmp_search_condition,' AND u.group_id=',p_group_id);
       END IF;
       IF p_dcschool_id IS NOT NULL THEN
	SET tmp_search_condition=CONCAT(tmp_search_condition,' AND u.dc_school_id=',p_dcschool_id);
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
	
END $$

DELIMITER ;
