DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_teacher_course_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_teacher_course_info_proc_split`(p_course_id VARCHAR(50),
				          p_course_name VARCHAR(200),
				          p_term_id VARCHAR(50),
				          p_available INT,
				          p_user_id VARCHAR(50),
				          p_subject_id INT,
				          p_class_id INT,
				          p_not_available INT,    
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' tc.user_id,tc.course_id,tc.course_name,tc.subject_id,t.TEACHER_NAME,tc.term_id,tc.available,tc.C_TIME,
	GROUP_CONCAT(CONCAT(c.class_grade,c.class_name)) classes ,   
	GROUP_CONCAT(c.class_id) classesid,
	GROUP_CONCAT(c.year) classesyear,
	GROUP_CONCAT(DATE_FORMAT(cc.classtime,''%Y-%m-%d %H:%i:%s'')) classtimes';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_teacher_course_info tc
		inner join teacher_info t on t.user_id=tc.user_id 
		left join tp_course_class cc on cc.course_id=tc.course_id
		left join class_info c on c.class_id=cc.class_id'; 
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE<>2");
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_NAME='",p_course_name,"'");
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.SUBJECT_ID=",p_subject_id);
	END IF;
	
	
	IF p_available IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE=",p_available);
	END IF;
	
	IF p_not_available IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE<>",p_not_available);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.CLASS_ID=",p_class_id);
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.TERM_ID='",p_term_id,"'");
	END IF;	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.user_id='",p_user_id,"'");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_ID='",p_course_id,"'");
	END IF;
	
	
	SET tmp_sql=CONCAT("select round(IFNULL(avg(si.score),0),1) avg_score,count(si.score) comment_times,aa.* from(SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition
	," group by tc.course_id,tc.course_name,tc.C_TIME,tc.subject_id,t.TEACHER_NAME,tc.available,tc.term_id)aa
    left join score_info si on aa.course_id=si.score_object_id and si.score_type=2
    group by aa.course_id,aa.course_name,aa.subject_id,aa.TEACHER_NAME,
    aa.available,aa.term_id,aa.classes,aa.classesid,aa.classesyear,aa.classtimes,aa.C_TIME ORDER BY aa.C_TIME desc");	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(DISTINCT tc.course_id) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
