DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_paper_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_j_course_paper_proc_split`(
					  p_ref INT,
				          p_paper_id BIGINT,
				          p_course_id BIGINT,
				          p_paper_name VARCHAR(100),
				          p_local_status INT,
				            p_course_name VARCHAR(100),
				          p_material_id INT,
				          p_grade_id INT,
				          p_subject_id INT,
				          p_filter_courseid BIGINT,
				          p_select_type INT,
				          p_task_flag INT,
				          p_papertype INT,
				          p_sel_date_type INT,
				          p_course_paper_name VARCHAR(200),
				          p_is_cloud INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_count_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(4000) DEFAULT ' distinct u.*,p.paper_name,p.score,p.paper_type,
	(select count(*) from tp_task_info t where t.task_value_id=u.paper_id and t.course_id=u.course_id)task_flag,
	(SELECT count(*) FROM tp_task_info t  WHERE t.task_type=6 and  t.paper_id=u.paper_id and u.course_id=t.course_id)mic_flag,
	(SELECT count(*) FROM tp_task_info t where t.task_type=6 and t.paper_id=u.paper_id and u.course_id=t.course_id)is_video_paper,
	(SELECT COUNT(*) FROM question_info q,j_paper_question pq
	WHERE q.`question_id`=pq.question_id AND pq.`paper_id`=u.paper_id AND q.question_type IN (3,4,7,8))objective1,
	 (SELECT COUNT(*) FROM j_ques_team_rela t,j_paper_question p,question_info q 
 WHERE t.team_id=p.question_id AND t.ques_id=q.`question_id`
 AND p.paper_id=u.paper_id AND q.question_type IN (3,4,7,8))objective2,
 (SELECT COUNT(*) FROM question_info q,j_paper_question pq
	WHERE q.`question_id`=pq.question_id AND pq.`paper_id`=u.paper_id AND q.question_type <3)subjective1,
	 (SELECT COUNT(*) FROM j_ques_team_rela t,j_paper_question p,question_info q 
 WHERE t.team_id=p.question_id AND t.ques_id=q.`question_id`
 AND p.paper_id=u.paper_id AND q.question_type <3)subjective2,
	(select ques_num from tp_task_info t where t.task_value_id=p.paper_id and t.course_id=u.course_id)ques_num';  
	DECLARE tmp_search_condition VARCHAR(4000) DEFAULT ' 1=1 and u.paper_id=p.paper_id and u.course_id=c.course_id and c.course_id=tcm.course_id and tm.material_id=tcm.teaching_material_id and  g.grade_id=tm.grade_id ';  
	DECLARE tmp_tbl_name VARCHAR(4000) DEFAULT ' tp_j_course_paper u,paper_info p,tp_course_info c,tp_j_course_teaching_material tcm,teaching_material_info tm,grade_info g '; 
	
	IF p_course_paper_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and (c.COURSE_NAME LIKE '%",p_course_paper_name,"%' or p.paper_name like '%",p_course_paper_name,"%')");
	END IF;
	
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID=",p_paper_id);
	END IF;
	
	IF p_is_cloud IS NOT NULL AND p_is_cloud=1 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID > 0 ");
	ELSEIF p_is_cloud=2 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID < 0 ");
	END IF;
	
	
	IF p_papertype IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.PAPER_TYPE=",p_papertype);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_filter_courseid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and not exists (select 1 from tp_j_course_paper a where a.COURSE_ID=u.COURSE_ID and a.COURSE_ID=",p_filter_courseid,")");
	END IF;
	
	IF p_paper_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.paper_name='",p_paper_name,"'");
	END IF;
	
	IF p_sel_date_type=1 AND p_local_status=2 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and  u.c_time > DATE_ADD(NOW(),INTERVAL -1 MONTH)  ");
	END IF;
	
	
	IF p_local_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.local_status=",p_local_status);
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.COURSE_NAME LIKE '%",p_course_name,"%'");
	END IF;
	
	
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.GRADE_ID=",p_grade_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tm.SUBJECT_ID=",p_subject_id);
	END IF;
	
	IF p_material_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tcm.teaching_material_id=",p_material_id);
	END IF;
	
	IF p_select_type IS NOT NULL AND p_select_type=4 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.paper_type in(1,2,3)");
	ELSEIF p_select_type=5 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.paper_type =4");
	ELSEIF p_select_type=6 THEN 
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.paper_type =5");
	ELSEIF p_select_type=7 THEN 
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.paper_type in(1,2,3,5) ");
	END IF;
	
	IF p_task_flag IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and  not exists (SELECT 1 FROM tp_task_allot_info ta,tp_task_info t WHERE ta.task_id=t.task_id AND t.course_id=u.course_id AND t.task_value_id=u.paper_id) ");
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_count_sql=tmp_sql;
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT t.*,(objective1+objective2)objectivenum,(subjective1+subjective2)subjectivenum FROM ( ",tmp_sql,")t");	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql,") t");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;