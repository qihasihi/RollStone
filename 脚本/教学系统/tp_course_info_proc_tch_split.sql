DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_tch_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_course_info_proc_tch_split`(
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
				          p_material_values VARCHAR(50),
				          p_user_id INT,
				          p_course_class_ref INT,
				          p_res_id BIGINT,
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' DISTINCT u.COURSE_ID,GROUP_CONCAT(tm.material_id) materialids,GROUP_CONCAT(tm.material_name) materialnames	';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_course_info u 
	INNER JOIN tp_j_course_teaching_material jtm ON jtm.course_id=u.COURSE_ID 
	INNER JOIN teaching_material_info tm ON tm.material_id=jtm.teaching_material_id '; 
	
	
	SET tmp_search_condition=CONCAT("  exists (SELECT 1 FROM tp_j_course_class tcc WHERE 1=1 and u.COURSE_ID=tcc.course_id ");
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.GRADE_ID=",p_grade_id); 
	END IF;
	
	IF p_user_id IS NOT NULL THEN 
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.USER_ID=",p_user_id);
	END IF;
	
	IF p_term_id IS NOT NULL  THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.TERM_ID='",p_term_id,"'"); 
	END IF;
	
	IF p_course_class_ref IS NOT NULL  THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tcc.REF=",p_course_class_ref); 
	END IF;
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," GROUP BY tcc.COURSE_ID )"); 
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_NAME='",p_course_name,"'");
	END IF;
	
	IF p_share_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SHARE_TYPE=",p_share_type);
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TEACHER_NAME='",p_teacher_name,"'");
	END IF;
	
	IF p_course_level IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_LEVEL=",p_course_level);
	END IF;
	
	IF p_cuser_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CUSER_ID=",p_cuser_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_material_values IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and jtm.TEACHING_MATERIAL_ID IN(",p_material_values,")");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_course_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_STATUS=",p_course_status);
	END IF;
	
	IF p_local_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.LOCAL_STATUS=",p_local_status);
	ELSE
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.LOCAL_STATUS=1");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition," GROUP BY u.COURSE_ID ");	
	SET tmp_sql2=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition," GROUP BY u.COURSE_ID ");
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	
	
	SET tmp_sql=CONCAT("SELECT distinct cc.*,tc.*,qtc.course_level q_course_level,qtc.cuser_id q_cuser_id
		FROM (SELECT bb.COURSE_ID,bb.SUBJECT_ID,bb.GRADE_ID,bb.materialids,bb.materialnames,GROUP_CONCAT(classname) classes,
		GROUP_CONCAT(classid) classesid,GROUP_CONCAT(classtype) classtype,GROUP_CONCAT(classtime) classtimes,GROUP_CONCAT(classendtime) classendtimes,bb.term_id,
		(case when  NOW()>MIN(classtime) AND classid<>0 then 1 else null end)isbegin,
		(SELECT COUNT(*) FROM tp_task_info t ,tp_task_allot_info ta WHERE t.`COURSE_ID`=bb.course_id AND t.`TASK_ID`=ta.`task_id` AND t.`TASK_TYPE`=10 AND NOW() BETWEEN ta.`b_time` AND ta.`e_time`)islive
		
	 FROM(SELECT aa.COURSE_ID,aa.materialids,aa.materialnames,cc.SUBJECT_ID,cc.GRADE_ID,cc.term_id,
			    IF(cc.class_type!=0,IFNULL(CONCAT(c.class_grade, c.class_name),tvc.VIRTUAL_CLASS_NAME),0) classname,
			    IF(cc.class_type!=0,IFNULL(c.class_id,tvc.VIRTUAL_CLASS_ID),0) classid,
			    cc.CLASS_TYPE classtype,
			    DATE_FORMAT(
				cc.begin_time,
				'%Y-%m-%d %H:%i:%s'
			      ) classtime,
			      DATE_FORMAT(
				cc.end_time,
				'%Y-%m-%d %H:%i:%s'
			      ) classendtime FROM(",tmp_sql,")aa
			LEFT JOIN tp_j_course_class cc ON cc.COURSE_ID = aa.COURSE_ID ");
			
			IF p_term_id IS NOT NULL THEN 
				SET tmp_sql=CONCAT(tmp_sql, " and cc.term_id='",p_term_id,"'");
			END IF;
			
			SET tmp_sql=CONCAT(tmp_sql,"
			LEFT JOIN class_info c ON c.CLASS_ID = cc.CLASS_ID AND cc.CLASS_TYPE=1
			LEFT JOIN tp_virtual_class_info tvc ON tvc.VIRTUAL_CLASS_ID = cc.CLASS_ID AND cc.CLASS_TYPE=2)bb 
		GROUP BY bb.COURSE_ID)cc
	INNER JOIN tp_course_info tc ON tc.COURSE_ID=cc.COURSE_ID
	LEFT JOIN tp_course_info qtc ON qtc.COURSE_ID=tc.QUOTE_ID
	INNER JOIN tp_j_course_class tcc ON tcc.course_id=tc.`COURSE_ID`");
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," where 1=1 and not exists (select 1 from tp_task_info t where t.course_id=tc.course_id and t.task_value_id=",p_res_id,")");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY tcc.c_time DESC");
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql2=CONCAT("SELECT distinct COUNT(*) INTO @tmp_sumCount FROM (SELECT bb.COURSE_ID,bb.SUBJECT_ID,bb.GRADE_ID,bb.materialids,bb.materialnames,GROUP_CONCAT(classname) classes,GROUP_CONCAT(classid) classesid,GROUP_CONCAT(classtype) classtype,GROUP_CONCAT(classtime) classtimes,GROUP_CONCAT(classendtime) classendtimes,bb.term_id
			FROM(SELECT aa.COURSE_ID,aa.materialids,aa.materialnames,cc.SUBJECT_ID,cc.GRADE_ID,cc.term_id,
			    IF(cc.class_type!=0,IFNULL(CONCAT(c.class_grade, c.class_name),tvc.VIRTUAL_CLASS_NAME),0) classname,
			    IF(cc.class_type!=0,IFNULL(c.class_id,tvc.VIRTUAL_CLASS_ID),0) classid,
			    cc.CLASS_TYPE classtype,
			    DATE_FORMAT(
				cc.begin_time,
				'%Y-%m-%d %H:%i:%s'
			      ) classtime,DATE_FORMAT(
				cc.end_time,
				'%Y-%m-%d %H:%i:%s'
			      ) classendtime FROM(",tmp_sql2,")aa
			LEFT JOIN tp_j_course_class cc ON cc.COURSE_ID = aa.COURSE_ID");
			
			IF p_term_id IS NOT NULL THEN 
				SET tmp_sql2=CONCAT(tmp_sql2, " and cc.term_id='",p_term_id,"'");
			END IF;
			
			SET tmp_sql2=CONCAT(tmp_sql2," LEFT JOIN class_info c ON c.CLASS_ID = cc.CLASS_ID AND cc.CLASS_TYPE=1
			LEFT JOIN tp_virtual_class_info tvc ON tvc.VIRTUAL_CLASS_ID = cc.CLASS_ID AND cc.CLASS_TYPE=2)bb 
		GROUP BY bb.COURSE_ID)cc
	INNER JOIN tp_course_info tc ON tc.COURSE_ID=cc.COURSE_ID
	LEFT JOIN tp_course_info qtc ON qtc.COURSE_ID=tc.QUOTE_ID");
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql2=CONCAT(tmp_sql2," where 1=1 and not exists (select 1 from tp_task_info t where t.course_id=tc.course_id and t.task_value_id=",p_res_id,")");
	END IF;
	
	SET @tmp_sql2=tmp_sql2;
	PREPARE stmt2 FROM @tmp_sql2  ;
	EXECUTE stmt2;
	DEALLOCATE PREPARE stmt2;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;