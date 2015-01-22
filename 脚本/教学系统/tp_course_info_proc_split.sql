DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_split`(
				          p_course_id BIGINT,
				          p_dc_school_id INT,
				          p_cuser_id INT,
				          p_course_name VARCHAR(100),
				          p_share_type INT,
				          p_course_level INT,
				          p_cloud_status INT,
				          p_course_status INT,
				          p_local_status INT,
				          p_teacher_name VARCHAR(100),
				          p_school_name VARCHAR(100),
				          p_quote_id BIGINT,
				          p_subject_id INT,
				          p_grade_id INT,
				          p_material_values VARCHAR(50),
				          p_filter_quote INT,
				          p_subjectvalues VARCHAR(100),
					  p_versionvalues VARCHAR(1000),
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' DISTINCT school.name schoolname,tc.teacher_name realname,tc.course_id,GROUP_CONCAT(tm.material_id) materialids,GROUP_CONCAT(CONCAT(tm.material_name,"(",tv.version_name,")")) materialnames,
	(select count(*) from tp_task_info t where t.course_id=tc.course_id )task_count,(select count(*) from tp_j_course_resource_info t where t.course_id=tc.course_id )res_count,
	(select count(*) from tp_j_course_question_info t where t.course_id=tc.course_id )ques_count,(select count(*) from tp_topic_info t where t.course_id=tc.course_id )topic_count, 
	(select count(*) from tp_j_course_paper p where p.course_id=tc.course_id) paper_count ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_course_info tc 
	INNER JOIN tp_j_course_teaching_material jtm ON jtm.course_id=tc.COURSE_ID 
	INNER JOIN teaching_material_info tm ON tm.material_id=jtm.teaching_material_id
	INNER JOIN teach_version_info tv On tv.version_id=tm.version_id
	inner join grade_info g on g.grade_id=tm.grade_id
	left join school_info school on school.school_id=tc.dc_school_id
	'; 
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_NAME LIKE '%",p_course_name,"%'");
	END IF;
	IF p_subjectvalues IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.subject_id IN (",p_subjectvalues,")");
	END IF;
	IF p_filter_quote IS NOT NULL AND p_cuser_id IS NOT NULL THEN
	/*  */
		/*SET tmp_search_condition=CONCAT(tmp_search_condition," and (tc.quote_id IS  NULL or quote_id=0) and tc.cuser_id <> ",p_cuser_id,"");*/
		/*SET tmp_search_condition=CONCAT(tmp_search_condition," AND NOT EXISTS (SELECT 1 FROM tp_course_info WHERE CUSER_ID =",p_cuser_id," AND quote_id IS NOT NULL AND quote_id <>0  and QUOTE_ID=tc.COURSE_ID) ");*/
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND NOT EXISTS (SELECT 1 FROM tp_course_info WHERE CUSER_ID =",p_cuser_id," and (course_id=tc.course_id or (quote_id=tc.course_id and quote_id not in(select quote_id from tp_course_info where local_status=2 and cuser_id=",p_cuser_id,"))))");
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.TEACHER_NAME LIKE '",p_teacher_name,"%'");
	END IF;
	
	IF p_course_level IS NOT NULL THEN
	-- test sql SET tmp_search_condition=CONCAT(tmp_search_condition," and tc. course_id=-9372786402551 ");
	
		IF p_course_level =-3 THEN
			-- -3为查找所有符合共享条件 ：云端标准，云端共享专题  或 校内共享
			SET tmp_search_condition=CONCAT(tmp_search_condition," and (( (tc.COURSE_LEVEL=1 and tc.COURSE_ID>0) OR (tc.COURSE_LEVEL=2 AND tc.COURSE_ID>0)) OR (SHARE_TYPE=1 and  tc.dc_school_id=",p_dc_school_id,") )");
		ELSEIF p_course_level=-2 THEN     
			-- -2为查 云端标准，云端共享，和要共享到云端的，
			SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_LEVEL=1 or  (tc.COURSE_LEVEL=2 AND tc.COURSE_ID>0)  or (tc.COURSE_LEVEL=2 and  tc.dc_school_id=",p_dc_school_id," )");
	        ELSEIF p_course_level=1 THEN  -- 云端标准
				SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_LEVEL=",p_share_type);
	        ELSEIF p_course_level=2 THEN  -- 校内共享或云端共享 
				SET tmp_search_condition=CONCAT(tmp_search_condition,"  and ( (tc.dc_school_id=",p_dc_school_id,"  and tc.COURSE_LEVEL=2 ) or 
				(tc.COURSE_LEVEL=2 and tc.course_id>0)");
	         	
	        ELSEIF p_course_level=3 THEN  -- 本地
				SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.dc_school_id=",p_dc_school_id," and tc.COURSE_LEVEL=",p_share_type);
	         	
		END IF;
	
	END IF;
	
	IF p_cuser_id IS NOT NULL AND p_filter_quote IS NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.CUSER_ID=",p_cuser_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.COURSE_ID=",p_course_id);
	END IF;
	
	
	
	IF p_material_values IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.MATERIAL_ID IN(",p_material_values,")");
	END IF;
	
	IF p_versionvalues IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.version_id IN(",p_versionvalues,")");
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
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tm.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tm.GRADE_ID=",p_grade_id); 
	END IF;
	
	IF p_quote_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.QUOTE_ID=",p_quote_id);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition," GROUP BY tc.COURSE_ID ");	
	SET tmp_sql2=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition," GROUP BY tc.COURSE_ID ");	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT t.*,aa.*,(
	select count(*) from tp_j_course_resource_info tcr
	INNER JOIN rs_resource_info r ON r.res_id=tcr.res_id
	 where tcr.course_id=t.course_id AND tcr.local_status=1 AND ((r.RES_DEGREE=3 AND r.SHARE_STATUS=1) OR (r.RES_DEGREE <>3))) resnum,
		round(IFNULL((SELECT AVG(avg_score) FROM tp_course_info WHERE quote_id=t.COURSE_ID) ,0),1) AVG_SCORE,
		IFNULL((SELECT SUM(comment_num) FROM tp_course_info WHERE quote_id=t.COURSE_ID) ,0) COMMENT_NUM
		FROM tp_course_info t INNER JOIN (",tmp_sql,") aa ON aa.COURSE_ID=t.COURSE_ID");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql2=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_course_info t INNER JOIN (",tmp_sql2,") aa ON aa.COURSE_ID=t.COURSE_ID");
	SET @tmp_sql2=tmp_sql2;
	PREPARE stmt2 FROM @tmp_sql2  ;
	EXECUTE stmt2;
	DEALLOCATE PREPARE stmt2;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
