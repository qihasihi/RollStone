DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_teacher_course_by_stu_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_teacher_course_by_stu_split`(
			                  p_course_id VARCHAR(50),
				          p_course_name VARCHAR(200),
				          p_term_id VARCHAR(50),
				          p_available INT,
				          p_user_id VARCHAR(50),
				          p_subject_id INT,
				           p_not_available INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT '';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_teacher_course_info tc 
	                                               LEFT JOIN tp_course_class cc ON cc.course_id = tc.course_id
						       LEFT JOIN j_class_user cu ON cu.class_id = cc.class_id'; 
	
	SET tmp_search_column = CONCAT("tc.available,tc.course_id,tc.course_name,tc.subject_id,tc.term_id,cc.classtime,tc.USER_ID,
	(SELECT t.TEACHER_NAME FROM teacher_info t WHERE t.user_id = tc.user_id) TEACHER_NAME,
	(SELECT COUNT(*) FROM comment_info cm,user_info u WHERE cm.COMMENT_OBJECT_ID=tc.COURSE_ID AND cm.COMMENT_USER_ID=u.USER_ID AND cm.COMMENT_TYPE=2 AND u.REF='",p_user_id,"') COMMENTSTATE");
	SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE<>2");
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_ID='",p_course_id,"'");
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_NAME='",p_course_name,"'");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.TERM_ID='",p_term_id,"'");
	END IF;
	
	IF p_not_available IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE<> ",p_not_available);
	END IF;
	
	
	
	IF p_available IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE=",p_available);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cu.user_id='",p_user_id,"' and cu.RELATION_TYPE='Ñ§Éú'");
	END IF;
	
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.SUBJECT_ID=",p_subject_id);
	END IF;
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.CLASSTIME<NOW()");
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
