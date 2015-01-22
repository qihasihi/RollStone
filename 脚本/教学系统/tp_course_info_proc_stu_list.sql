DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_stu_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_stu_list`(
				          p_course_id BIGINT,
				          p_cuser_id INT,
				          p_course_name VARCHAR(100),
				          p_share_type INT,
				          p_course_level INT,
				          p_cloud_status INT,
				          p_course_status INT,
				          p_local_status INT,
				          p_teacher_name VARCHAR(100),
				          p_school_name VARCHAR(100),
				          p_term_id VARCHAR(50),
				          p_subject_id INT,
				          p_grade_id INT,
				          p_user_id INT,
				          p_selectType INT ,    
				          p_class_id INT,
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column varchar(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " tc.*,aa.term_id,aa.USER_ID TEACHER_ID,aa.REF COURSECLASSREF,aa.classesid,aa.classtimes ,aa.classes,aa.classtype,
	(SELECT COUNT(*) FROM tp_task_info t ,tp_task_allot_info ta WHERE t.`COURSE_ID`=tc.course_id AND t.`TASK_ID`=ta.`task_id` AND t.`TASK_TYPE`=10 AND NOW() BETWEEN ta.`b_time` AND ta.`e_time`)islive
	";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_course_info tc '; 
	
	SET tmp_search_column=CONCAT(tmp_search_column,",IF(EXISTS(SELECT c.COMMENT_ID FROM comment_info c WHERE c.COMMENT_USER_ID=",p_user_id," AND c.COMMENT_OBJECT_ID=tc.COURSE_ID  AND c.COMMENT_TYPE=2),1,0) iscomment ");
	
	if p_class_id IS NOT NULL AND p_subject_id IS NOT NULL AND p_user_id IS NOT NULL THEN
	set tmp_search_column=CONCAT(tmp_search_column,"
		,IFNULL((SELECT course_total_score FROM tp_stu_score WHERE course_id=tc.course_id
		 AND subject_id=",p_subject_id," AND class_id=",p_class_id,"  AND class_type=1 AND user_id=",p_user_id,"  ORDER BY c_time DESC  LIMIT 0,1),-1) coursetotalscore
	");
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.TERM_ID='",p_term_id,"'");
	END IF;
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.CLASS_ID=",p_class_id," AND tcc.class_type=1");
	END IF;
	
	
		
	
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,"INNER JOIN
		(SELECT tcc.term_id,tcc.course_id,tcc.ref,tcc.USER_ID,CONCAT(tcc.class_id) classesid,CONCAT(tcc.begin_time) classtimes,CONCAT(c.CLASS_GRADE,c.CLASS_NAME) classes,'1' classtype,tcc.c_time order_c FROM tp_j_course_class tcc 
			INNER JOIN j_class_user cu ON cu.class_id=tcc.class_id AND tcc.class_type=1
			INNER JOIN class_info c ON c.CLASS_ID=tcc.CLASS_ID
			INNER JOIN user_info u ON u.REF=cu.USER_ID
			WHERE u.USER_ID=",p_user_id,tmp_search_condition," 
			UNION 
		 SELECT tcc.term_id,tcc.course_id,tcc.ref,tcc.USER_ID,CONCAT(tcc.class_id) classesid,CONCAT(tcc.begin_time) classtimes,tvc.VIRTUAL_CLASS_NAME classes,'2' classtype,tcc.c_time order_c FROM tp_j_course_class tcc 
			INNER JOIN tp_virtual_class_info tvc ON tvc.VIRTUAL_CLASS_ID=tcc.CLASS_ID AND tcc.class_type=2
			INNER JOIN tp_j_virtual_class_student tvcs ON tvcs.VIRTUAL_CLASS_ID=tvc.VIRTUAL_CLASS_ID
			WHERE tvcs.USER_ID=",p_user_id,tmp_search_condition,"
		)aa ON aa.course_id=tc.course_id ");
	
	SET tmp_search_condition=" 1=1 ";
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_NAME='",p_course_name,"'");
	END IF;
	
	IF p_share_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.SHARE_TYPE=",p_share_type);
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.TEACHER_NAME='",p_teacher_name,"'");
	END IF;
	
	IF p_course_level IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_LEVEL=",p_course_level);
	END IF;
	
	IF p_cuser_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.CUSER_ID=",p_cuser_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_course_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_STATUS=",p_course_status);
	END IF;
	
	
	
	IF p_local_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.LOCAL_STATUS=",p_local_status);
	ELSE
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.LOCAL_STATUS=1");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	else
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  aa.order_c DESC");
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
