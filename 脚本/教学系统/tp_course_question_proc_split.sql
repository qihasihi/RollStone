DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_question_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_question_proc_split`(p_courseid bigint,
						       p_coursename varchar(300),
						       p_courselevel int,
						       p_gradeid int,
						       p_material_id int,
						       p_current_course_id bigint,
						       p_subject_id int,
						       p_question_type int,
						       p_current_page INT,
							p_page_size	INT,
							p_sort_column VARCHAR(50),
							OUT totalNum INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(20000) DEFAULT ' distinct t.*,tv.version_name,ti.grade_id,ti.subject_id,ti.material_name,g.grade_name';  
	DECLARE tmp_search_condition VARCHAR(20000) DEFAULT ' 1=1 and t.course_id=tm.course_id and ti.version_id=tv.version_id AND ti.material_id=tm.teaching_material_id AND g.GRADE_ID = ti.grade_id  ';  
	DECLARE tmp_tbl_name VARCHAR(5000) DEFAULT ' tp_course_info t,teach_version_info tv,tp_j_course_teaching_material tm,teaching_material_info ti,grade_info g  ';
	DECLARE tmp_count_sql varchar(5000) default '';
	DECLARE tmp_sel_sql VARCHAR(2000) DEFAULT ' ,(SELECT COUNT(*) FROM tp_j_course_question_info q WHERE q.course_id = t.course_id) questionnum  ';
	
	if p_coursename is not null then
		set tmp_search_condition = concat(tmp_search_condition," and t.course_name like '%",p_coursename,"%'");
	end if;
	
	IF p_question_type IS NOT NULL THEN
		SET tmp_sel_sql = CONCAT("",",(SELECT COUNT(*) FROM tp_j_course_question_info cq,question_info q WHERE cq.question_id=q.question_id and cq.course_id = t.course_id and q.question_type=",p_question_type,") questionnum ");
	END IF;
	
	SET tmp_search_column = CONCAT(tmp_search_column,tmp_sel_sql);
	
	
	
	
	if p_courselevel is not null then 
		if p_courselevel=1 then 
			SET tmp_search_condition = CONCAT(tmp_search_condition," and t.course_level = 3");
		end if;
		
		IF p_courselevel=2 THEN 
			SET tmp_search_condition = CONCAT(tmp_search_condition," and t.course_level <> 3");
		END IF;
		
		IF p_courselevel=3 and p_current_course_id is not null THEN 
			SET tmp_tbl_name=CONCAT(tmp_tbl_name," ,tp_j_course_related_info cr ");
			SET tmp_search_condition=CONCAT(tmp_search_condition," and  t.course_id=cr.related_course_id and cr.COURSE_ID=",p_current_course_id);
		END IF;
	end if;
	
	IF p_courseid IS NOT NULL THEN 
		SET tmp_search_condition = CONCAT(tmp_search_condition," and t.course_id = ",p_courseid);
	END IF;
	
	if p_gradeid is not null then
		set tmp_search_condition = concat(tmp_search_condition," and ti.grade_id=",p_gradeid);
	end if;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition = CONCAT(tmp_search_condition," and ti.subject_id=",p_subject_id);
	END IF;
	
	IF p_material_id IS NOT NULL THEN
		SET tmp_search_condition = CONCAT(tmp_search_condition," and ti.material_id=",p_material_id);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	set tmp_count_sql=tmp_sql;
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	
	SET tmp_sql=CONCAT(" select count(*) into @totalcount from (",tmp_count_sql,")t");
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET totalNum = @totalcount;
END $$

DELIMITER ;
