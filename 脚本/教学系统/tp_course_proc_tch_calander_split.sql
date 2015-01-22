DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_proc_tch_calander_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_course_proc_tch_calander_split`(
				          p_course_id BIGINT,
				          p_course_level INT,
				          p_term_id VARCHAR(50),
				          p_cuser_id INT,
				          p_user_type INT,
				          p_dc_schoolid INT,
				          
				          p_subject_id INT,
				          p_grade_id INT,
				          p_day VARCHAR(50),
				          p_class_id INT,
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' distinct  tc.*, cc.begin_time,cc.end_time,cc.user_id,cc.subject_id, cc.grade_id,cc.term_id,
		c.class_grade,c.class_name,c.class_id,getCourseDate(cc.begin_time,cc.end_time)date_msg ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '  tc.`COURSE_ID`=cc.course_id AND c.class_id=cc.class_id AND tc.LOCAL_STATUS=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_course_info tc,tp_j_course_class cc,class_info c'; 
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND cc.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND cc.GRADE_ID=",p_grade_id); 
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND c.class_grade=(select grade_value from grade_info where grade_id=",p_grade_id,")"); 
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND c.class_id=",p_class_id); 
	END IF;
	
	
	
	IF p_day IS NOT NULL THEN
		
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND STR_TO_DATE('",p_day,"','%Y-%m-%d') BETWEEN DATE_FORMAT(cc.begin_time,'%Y-%m-%d') and DATE_FORMAT(cc.end_time,'%Y-%m-%d')");
		
	END IF;
	
	
	IF p_term_id IS NOT NULL  THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND cc.TERM_ID='",p_term_id,"'"); 
	END IF;
	
	
	IF p_course_level IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_LEVEL=",p_course_level);
	END IF;
	
	IF p_cuser_id IS NOT NULL THEN
		IF p_user_type IS NOT NULL AND p_user_type=3 THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and exists (select 1 from tp_j_course_class c where c.class_id=cc.class_id
			 and (c.class_id in(select j.class_id from j_class_user j,user_info u where u.ref=j.user_id and j.relation_type='°àÖ÷ÈÎ' and u.user_id=",p_cuser_id,") OR cc.`user_id`=",p_cuser_id,"))");
		ELSEIF p_user_type=2 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.user_id=",p_cuser_id ,"");
		END IF;
		
		
		SET tmp_search_column=CONCAT(tmp_search_column," ,(select count(*) from tp_j_course_class c where c.ref=cc.ref and c.user_id=",p_cuser_id,")isself"); 
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_ID=",p_course_id);
	END IF;
	
	
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition," ");	
	
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
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;