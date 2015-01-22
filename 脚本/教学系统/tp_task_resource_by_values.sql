DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_resource_by_values`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_resource_by_values`(p_gradeid int,
							p_subjectid int,
							p_name varchar(200),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(500),
							OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_count_sql VARCHAR(20000) DEFAULT ' '; 
	set tmp_sql = concat(tmp_sql,"SELECT cm.course_id,tc.COURSE_NAME,rs.*,IFNULL(IFNULL(t.teacher_name,s.stu_name),ui.user_name)real_name
					FROM teaching_material_info tm LEFT JOIN tp_j_course_teaching_material cm ON tm.material_id=cm.teaching_material_id
					LEFT JOIN tp_course_info tc ON cm.course_id=tc.COURSE_ID 
					LEFT JOIN tp_j_course_resource_info jcr ON tc.COURSE_ID=jcr.course_id
					RIGHT JOIN rs_resource_info rs ON jcr.res_id=rs.res_id
					LEFT JOIN user_info ui ON rs.user_id=ui.user_id
					LEFT JOIN teacher_info t ON t.user_id=ui.ref 
					LEFT JOIN student_info s ON s.user_id=ui.ref");
	if p_gradeid is not null then
		set tmp_sql=concat(tmp_sql," WHERE tm.grade_id=",p_gradeid);
	end if;
	if p_subjectid is not null then
		SET tmp_sql=CONCAT(tmp_sql," and  tm.subject_id=",p_subjectid);
	end if;
	if p_name is not null then 
		SET tmp_sql=CONCAT(tmp_sql," and  (tc.COURSE_NAME LIKE '%",p_name,"%' OR rs.RES_NAME LIKE '%",p_name,"%' )");
	end if;
	
	set tmp_sql=concat(tmp_sql," AND  NOT EXISTS (SELECT 1 FROM tp_task_allot_info ta,tp_task_info t WHERE ta.task_id=t.task_id AND t.course_id=jcr.course_id AND t.task_value_id=jcr.res_id)");
	set tmp_sql = concat(tmp_sql," group by rs.res_id");
	set tmp_sql = concat("SELECT aa.*,IFNULL(d1.DICTIONARY_NAME,'--') RES_TYPE_NAME,IFNULL(d2.DICTIONARY_NAME,'--') FILE_TYPE_NAME FROM (",tmp_sql,") aa ");
	set tmp_sql = concat(tmp_sql,"LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=aa.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'  
					LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=aa.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE'");
	SET tmp_count_sql=tmp_sql;
		
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql," )t ");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
END $$

DELIMITER ;
