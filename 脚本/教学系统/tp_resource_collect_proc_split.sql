DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_collect_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_collect_proc_split`(
					  p_collect_id INT,
				          p_user_id VARCHAR(50),
				          p_res_detail_id BIGINT,
				          p_course_id BIGINT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT 'u.*,f.file_suffixname,f.file_size,f.res_name,f.file_name,tc.course_name,ifnull(ifnull(ifnull(tt.teacher_name,s.stu_name),ui.user_name),f.user_name)realname';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1  ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_resource_collect u INNER JOIN rs_resource_info f ON u.res_id=f.res_id LEFT JOIN user_info ui ON f.user_id=ui.user_id
	INNER JOIN tp_course_info tc ON u.course_id=tc.course_id
	 LEFT JOIN student_info s ON ui.ref=s.user_id LEFT JOIN teacher_info tt ON tt.user_id=ui.REF  '; 
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_collect_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COLLECT_ID=",p_collect_id);
	END IF;
	
	
	IF p_res_detail_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RES_ID=",p_res_detail_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
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
