DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_question_team_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_question_team_proc_split`(
					  p_ref BIGINT,
				          p_question_id BIGINT,
				          p_course_id BIGINT,
				          p_ques_status INT,
				          p_course_name VARCHAR(100),
				          p_material_id INT,
				          p_grade_id INT,
				          p_subject_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp__count_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' DISTINCT u.ref,ifnull((select score from j_paper_ques_team_score pqts where pqts.course_id=u.course_id and pqts.ques_ref=t.ref limit 1),t.score)score,t.order_id,u.course_id,u.operate_time,tc.quote_id,q.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' u.course_id=tc.course_id AND  tc.course_id=tcm.course_id AND tm.material_id=tcm.teaching_material_id AND g.grade_id=tm.grade_id AND t.ques_id=q.question_id AND t.team_id=u.question_id ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_j_course_question_info u,question_info q,tp_course_info tc,tp_j_course_teaching_material tcm,teaching_material_info tm,grade_info g ,j_ques_team_rela t  '; 
	
	
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ref=",p_ref);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_ID=",p_question_id);
	END IF;
	
	
	IF p_ques_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and q.STATUS=",p_ques_status);
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.course_name like '%",p_course_name,"%'");
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
	
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET tmp__count_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp__count_sql,")t WHERE 1=1");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
