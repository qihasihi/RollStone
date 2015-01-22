DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `grade_proc_search_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `grade_proc_search_split`(
				          
				          p_grade_id INT,
				          p_grade_name VARCHAR(1000),
				          p_grade_value VARCHAR(1000),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(500),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' g.*';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(500) DEFAULT ' grade_info g '; 
	
	
	IF p_grade_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.GRADE_NAME LIKE '%",p_grade_name,"%'");
	END IF;
	
	IF p_grade_value IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.grade_value='",p_grade_value,"'");
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.GRADE_ID=",p_grade_id);
	END IF;
	
	
	
	SET tmp_sql=CONCAT("SELECT g.*,IFNULL((SELECT GROUP_CONCAT(t.teacher_name) FROM grade_info gg LEFT JOIN j_role_user ru ON gg.grade_id=ru.grade_id
LEFT JOIN teacher_info t ON ru.user_id=t.user_id WHERE gg.grade_id=g.grade_id AND ru.role_id=9),'''')zheng_leader,
IFNULL((SELECT GROUP_CONCAT(t.teacher_name) FROM grade_info gg LEFT JOIN j_role_user ru ON gg.grade_id=ru.grade_id
LEFT JOIN teacher_info t ON ru.user_id=t.user_id WHERE gg.grade_id=g.grade_id AND ru.role_id=10),'''')fu_leader 
FROM (SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	    ELSE
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  IF(g.grade_id=0,999,g.GRADE_ID)");
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	SET tmp_sql=CONCAT(tmp_sql,") g");
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