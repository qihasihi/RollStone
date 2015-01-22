DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_teacher_course_theme_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_teacher_course_theme_split`(
					       p_page_size     INT,
					       p_page_index    INT,
					       p_sort_column  VARCHAR(200),
					       p_course_id  VARCHAR(100),
					       p_user_id  VARCHAR(100),
					       p_teacher_name  VARCHAR(200),
					       p_course_name  VARCHAR(200),
					       p_term_id       VARCHAR(100),
					       p_class_id      VARCHAR(100),
					       p_grade      VARCHAR(100),    
					       p_available  INT,                              
					      OUT sumCount INT
				          )
BEGIN
	DECLARE v_sql VARCHAR(20000) DEFAULT '  FROM(
			    SELECT tc.course_id,tc.course_name,s.SUBJECT_NAME,t.TEACHER_NAME,tc.term_id,tc.available,tc.C_TIME,
			    CAST(GROUP_CONCAT(CONCAT(c.class_grade,c.class_name)) AS CHAR )  classes ,   
			    CAST(GROUP_CONCAT(c.class_id) AS CHAR ) classesid,
			    CAST(GROUP_CONCAT(c.year) AS CHAR ) classesyear
			    FROM tp_teacher_course_info tc
			    INNER JOIN teacher_info t ON t.user_id=tc.user_id
			    INNER JOIN subject_info s ON s.SUBJECT_ID=tc.SUBJECT_id 
			    LEFT JOIN j_class_user cc ON cc.USER_ID=tc.User_id
			    LEFT JOIN class_info c ON c.class_id=cc.class_id WHERE 1=1   
	';	
	DECLARE v_search_col VARCHAR(2000) DEFAULT ' IFNULL(AVG(cm.score),0) avg_score,COUNT(cm.score) comment_times,aa.* ';
	DECLARE v_tmp_sql VARCHAR(20000) DEFAULT '';
	 IF p_course_id IS NOT NULL THEN
	     SET v_sql = CONCAT(v_sql," AND tc.course_id ='",p_course_id,"'");
	    END IF;
	  
	    IF p_term_id IS NOT NULL THEN
	      SET v_sql =  CONCAT(v_sql," AND tc.term_id ='", p_term_id,"'");
	    END IF;
	  
	    IF p_user_id IS NOT NULL THEN
	      SET v_sql =  CONCAT(v_sql," AND tc.user_id ='" , p_teacher_id,"'");
	    END IF;
	  
	    IF p_teacher_name IS NOT NULL THEN
	     SET v_sql =  CONCAT(v_sql," AND t.teacher_name like '%",p_teacher_name,"%'");
		      
	    END IF;
	  
	    IF p_course_name IS NOT NULL THEN
	      SET v_sql =  CONCAT(v_sql," AND tc.course_name like '%",p_course_name,"%'");
	    END IF;
	  
	    IF p_class_id IS NOT NULL THEN
	      SET v_sql =  CONCAT(v_sql," AND c.class_id ='",p_class_id,"'");
	    END IF;
	      
	    IF p_grade IS NOT NULL THEN
	      SET v_sql =  CONCAT(v_sql," AND c.class_grade='", p_grade ,"'");
	    END IF;
	
	    IF p_available IS NOT NULL THEN
		SET v_sql=CONCAT(v_sql," and u.AVAILABLE=",p_available);
	 END IF;
	
	
	SET v_sql=CONCAT(v_sql,'    GROUP BY tc.course_id,tc.course_name,tc.C_TIME,t.TEACHER_NAME,tc.available,tc.term_id)aa
			    LEFT JOIN tp_comment_info cm ON aa.course_id=cm.comment_object_id
			    GROUP BY aa.course_id,aa.course_name,aa.subject_name,aa.TEACHER_NAME,
			    aa.available,aa.term_id,aa.classes,aa.classesid,aa.classesyear,aa.C_TIME ORDER BY course_id DESC');
	
	SET v_tmp_sql=v_sql;
	IF p_sort_column IS NOT NULL THEN
	    SET v_sql=CONCAT(v_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_page_index IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET v_sql=CONCAT(v_sql," LIMIT ",(p_page_index-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =v_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET v_tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",v_tmp_sql);
	SET @tmp_sql=v_tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;	
END $$

DELIMITER ;
