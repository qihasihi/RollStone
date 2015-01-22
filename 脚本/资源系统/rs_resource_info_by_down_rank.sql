DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_by_down_rank`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_by_down_rank`(
				          p_gradestr VARCHAR(1000),				          
				          p_subjectstr VARCHAR(1000),				          
				          p_type VARCHAR(100),
							p_current_page INT,
							p_page_size INT,
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sort VARCHAR(100) DEFAULT p_sort_column;
	
	SET tmp_sql=CONCAT('
SELECT tmp.downloadnum currentClicks,rs.*,IFNULL(sub.SUBJECT_NAME,"--") SUBJECT_NAME,
		IFNULL(g.GRADE_NAME,"--") GRADE_NAME,
		IFNULL(d1.DICTIONARY_NAME,"--") RES_TYPE_NAME,
		IFNULL(d2.DICTIONARY_NAME,"--") FILE_TYPE_NAME
		 FROM (
SELECT t1.res_id,t1.downloadnum,t2.grade,t2.subject FROM (
					SELECT res_id,SUM(downloadnum) downloadnum FROM rs_hot_rank WHERE TYPE=',p_type,'
					GROUP BY res_id 
				) t1
				 INNER JOIN 
				(			
				SELECT DISTINCT r.res_id,tm.grade_id grade,tm.subject_id subject
				  FROM tp_j_course_resource_info cr 
				  INNER JOIN rs_resource_info r ON cr.res_id=r.RES_ID
				  INNER JOIN tp_course_info tc ON tc.course_id=cr.course_id
				  INNER JOIN  tp_j_course_teaching_material tcm ON tc.course_id=tcm.course_id
				  INNER JOIN teaching_material_info tm ON tm.material_id=tcm.teaching_material_id 
				   INNER JOIN grade_info g ON g.GRADE_ID=tm.grade_id
				  WHERE 1=1 AND r.res_status=1 AND tm.grade_id IN (',p_gradestr,') AND tm.subject_id IN (',p_subjectstr,')	
				  UNION ALL
				SELECT DISTINCT r.res_id,grade,subject FROM rs_resource_info r WHERE r.GRADE IN (',p_gradestr,') AND SUBJECT IN (',p_subjectstr,')			  	
				) t2				
				 ON t1.res_id=t2.res_id
		');	
	
	IF p_sort_column IS NULL OR length(p_sort_column)<1 THEN
	   SET tmp_sort=' t1.downloadnum desc ';
	   else
	   set tmp_sort=p_sort_column;
	END IF;	
	 SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",tmp_sort);
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	SET tmp_sql=CONCAT(tmp_sql," ) tmp
		  INNER JOIN rs_resource_info rs ON rs.res_id=tmp.res_id 
		 LEFT JOIN subject_info sub ON sub.SUBJECT_ID=tmp.SUBJECT
		LEFT JOIN grade_info g ON g.GRADE_ID=tmp.GRADE
		LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=rs.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'
		LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=rs.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE'
		 
		 ");
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT('
		SELECT COUNT(*) FROM (
					SELECT res_id,SUM(downloadnum) downloadnum FROM rs_hot_rank WHERE TYPE=',p_type,'
					GROUP BY res_id 
				) t1
				 INNER JOIN 
				(			
				SELECT DISTINCT r.res_id
				  FROM tp_j_course_resource_info cr 
				  INNER JOIN rs_resource_info r ON cr.res_id=r.RES_ID
				  INNER JOIN tp_course_info tc ON tc.course_id=cr.course_id
				  INNER JOIN  tp_j_course_teaching_material tcm ON tc.course_id=tcm.course_id
				  INNER JOIN teaching_material_info tm ON tm.material_id=tcm.teaching_material_id 
				   INNER JOIN grade_info g ON g.GRADE_ID=tm.grade_id
				  WHERE 1=1 AND r.res_status=1 AND tm.grade_id IN (',p_gradestr,') AND tm.subject_id IN (',p_subjectstr,')	
				  UNION ALL
				SELECT DISTINCT r.res_id FROM rs_resource_info r WHERE r.GRADE IN (',p_gradestr,') AND SUBJECT IN (',p_subjectstr,')			  	
				) t2				
				 ON t1.res_id=t2.res_id	');
	SET @sql2=tmp_sql;
	PREPARE stmt FROM @sql2  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
