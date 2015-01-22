DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_proc_split`(
				          p_ref INT,
				          p_course_id BIGINT,
				          p_class_id INT,
				          p_term_id VARCHAR(60),
				          p_class_type INT,
				          p_begin_time DATETIME,
				          p_user_id INT,
				          p_subject_id INT,
				          p_grade_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' distinct cc.*,tc.course_name,tc.course_level,tc.quote_id,t.teacher_name,sub.subject_name,gde.grade_name,c.dc_type,
		concat(c.class_grade,c.class_name) class_name';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_j_course_class cc
	inner join user_info u on u.user_id=cc.user_id
	inner join teacher_info t on t.user_id=u.ref
	inner join tp_course_info tc on tc.course_id=cc.course_id and tc.LOCAL_STATUS=1 
	inner join subject_info sub on sub.subject_id=cc.subject_id
	inner join grade_info gde on gde.grade_id=cc.grade_id
	inner join class_info c on c.class_id=cc.class_id and cc.class_type=1
	';   
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.CLASS_ID=",p_class_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.GRADE_ID=",p_grade_id);
	END IF;
	
        IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.term_id='",p_term_id,"'");
        END IF;	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.REF=",p_ref);
	END IF;
	
	IF p_begin_time IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.BEGIN_TIME=",p_begin_time);
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.CLASS_TYPE=",p_class_type);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.USER_ID=",p_user_id);
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
