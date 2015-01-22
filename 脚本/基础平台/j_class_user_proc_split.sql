DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_class_user_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `j_class_user_proc_split`(
				          p_ref VARCHAR(50),
				          p_user_ref VARCHAR(50),
				          p_user_id INT,
				          p_user_name VARCHAR(100),
				          p_class_id INT,
				          p_class_name VARCHAR(50),
				          p_class_grade VARCHAR(50),
				          p_class_type VARCHAR(50),
				          p_class_year VARCHAR(50),
				          p_class_pattern VARCHAR(50),
				          p_relation_type VARCHAR(50),
				          p_no_relation_type VARCHAR(50),
				          p_sport_sex INT,
				          p_real_name VARCHAR(50),
				          p_clsisflag INT,
				          p_history_year VARCHAR(50),
				          p_subject_id VARCHAR(10),
				          p_STATE_ID INT,
				          p_sel_complete INT,
				          p_dc_school_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(1000),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(20000) DEFAULT ' DISTINCT cu.*,cla.class_grade,cla.class_name,cla.year,cla.pattern,cla.dc_type,u.ett_user_id,u.user_name,u.password,u.user_id uid,u.head_image ';  
	DECLARE tmp_search_condition VARCHAR(20000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(5000) DEFAULT ''; 
	
	SET tmp_tbl_name="(SELECT cu.* FROM j_class_user cu WHERE 1=1";	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND cu.REF='",p_ref,"'");
	END IF;
	IF p_relation_type IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cu.RELATION_TYPE='",p_relation_type,"'");
	END IF;
	IF p_no_relation_type IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cu.RELATION_TYPE <> '",p_no_relation_type,"'");
	END IF;
	IF p_sport_sex IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cu.SPORT_SEX=",p_sport_sex);
	END IF;
	
	IF p_subject_id IS NOT NULL AND p_relation_type='任课老师' THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cu.SUBJECT_ID='",p_subject_id,"'");
	END IF;	
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cu.CLASS_ID='",p_class_id,"'");
		IF p_sel_complete IS NOT NULL  AND p_subject_id IS NOT NULL THEN
			SET tmp_search_column=CONCAT(tmp_search_column," ,ifnull(Round((SELECT COUNT(distinct task_id) FROM tp_task_performance tp WHERE tp.TASK_ID IN (SELECT  task_id FROM tp_task_allot_info t", 
			" left join tp_j_course_teaching_material jm on t.course_id=jm.course_id left join teaching_material_info m on jm.teaching_material_id=m.material_id WHERE 1=1 and m.subject_id=",p_subject_id," AND ((t.user_type=0 AND t.user_type_id=",p_class_id,") or (t.user_type_id in(SELECT g.group_id FROM tp_group_info g,tp_j_group_student gs WHERE g.GROUP_ID=gs.GROUP_ID AND gs.USER_ID=u.user_id AND g.class_id=",p_class_id,") and t.user_type=2 )) and now()>t.e_time ) AND tp.user_id=u.ref)",
			"/(SELECT  COUNT(DISTINCT task_id) FROM tp_task_allot_info t left join tp_j_course_teaching_material jm on t.course_id=jm.course_id left join teaching_material_info m on jm.teaching_material_id=m.material_id",
			 " WHERE m.subject_id=",p_subject_id," and ((t.user_type=0 AND  t.user_type_id=",p_class_id," ) OR (t.user_type_id in(SELECT g.group_id FROM tp_group_info g,tp_j_group_student gs WHERE g.GROUP_ID=gs.GROUP_ID AND gs.USER_ID=u.user_id AND g.class_id=",p_class_id,") and t.user_type=2)) AND now()>t.e_time )*100,2),0)complete_num");
		END IF;
		
	END IF;
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,") cu INNER JOIN (select cla.class_id,cla.class_grade,cla.class_name,cla.year,cla.pattern,cla.dc_type FROM class_info cla WHERE 1=1 ");	
	IF p_clsisflag IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND cla.isflag=",p_clsisflag);
	END IF;
	IF p_class_name IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cla.CLASS_NAME='",p_class_name,"'");
	END IF;
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cla.DC_SCHOOL_ID=",p_dc_school_id);
	END IF;
	
	IF p_class_grade IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cla.CLASS_GRADE='",p_class_grade,"'");
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND cla.CLASS_TYPE='",p_class_type,"'");
	END IF;
	
	
	IF p_class_year IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and cla.YEAR='",p_class_year,"'");
	END IF;
	
	IF p_class_pattern IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and cla.pattern='",p_class_pattern,"'");
	END IF;
	
	IF p_history_year IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and cla.year<'",p_history_year,"'");
	END IF;
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,") cla ON cla.CLASS_ID=cu.CLASS_ID  INNER JOIN 
		(select u.ref,u.ett_user_id,u.user_name,u.user_id,head_image,u.password FROM user_info u where 1=1 ");
	IF p_user_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND u.USER_ID=",p_user_id);
	END IF;
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND u.ref='",p_user_ref,"'");
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND u.user_name='",p_user_name,"'");
	END IF;		
	IF p_STATE_ID IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"  AND u.STATE_ID=",p_STATE_ID);
	END IF;
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,")u ON u.REF=cu.USER_ID ");
	SET tmp_tbl_name=CONCAT(tmp_tbl_name," WHERE 1=1 ");
	IF p_real_name IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and (u.user_name like '%",p_real_name,"%' or tea.teacher_name like '%",p_real_name,"%' or stu.stu_name like '%",p_real_name,"%' )");
	END IF;
	
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name);	
	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	SET tmp_sql=CONCAT("SELECT ts.isleader,t.*,sub.subject_name, IFNULL(tea.TEACHER_NAME,IFNULL(stu.STU_NAME,t.USER_NAME)) realname, stu.stu_no,
		IFNULL(tea.TEACHER_SEX,IFNULL(stu.STU_SEX,'')) sex
		FROM (",tmp_sql,") t ");
	SET tmp_sql=CONCAT(tmp_sql," LEFT JOIN teacher_info tea ON tea.USER_ID=t.USER_ID ");
	SET tmp_sql=CONCAT(tmp_sql," LEFT JOIN student_info stu ON stu.USER_ID=t.USER_ID ");			
	SET tmp_sql=CONCAT(tmp_sql," LEFT JOIN subject_info sub ON t.subject_id=sub.subject_id ");
	SET tmp_sql = CONCAT(tmp_sql," left join tp_j_group_student ts on ts.user_id = t.uid");
	IF p_class_id IS NOT NULL AND p_subject_id IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," and ts.group_id in(select group_id from tp_group_info where class_id=",p_class_id," and subject_id=",p_subject_id,")");
	END IF;
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);	
	ELSE
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY stu.stu_no ");	
	END IF;	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;