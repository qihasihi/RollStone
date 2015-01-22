DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_idx_downrank`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_idx_downrank`(
							p_gradestr VARCHAR(100),
							p_subjectstr VARCHAR(100),
							p_size INT
							)
BEGIN
      DECLARE tmp_sql VARCHAR(30000);
 SET tmp_sql=CONCAT('
			SELECT t2.*,t1.downloadnum FROM (
					SELECT res_id,SUM(downloadnum) downloadnum FROM rs_hot_rank WHERE TYPE=3
					GROUP BY res_id 
				) t1 INNER JOIN 
				(			
				SELECT DISTINCT r.res_id,rtrim(ltrim(r.res_name)) res_name
				  FROM tp_j_course_resource_info cr 
				  INNER JOIN rs_resource_info r ON cr.res_id=r.RES_ID
				  INNER JOIN tp_course_info tc ON tc.course_id=cr.course_id
				  INNER JOIN  tp_j_course_teaching_material tcm ON tc.course_id=tcm.course_id
				  INNER JOIN teaching_material_info tm ON tm.material_id=tcm.teaching_material_id 
				   INNER JOIN grade_info g ON g.GRADE_ID=tm.grade_id
				  WHERE 1=1 AND r.res_status=1 AND tm.grade_id IN (',p_gradestr,') AND tm.subject_id IN (',p_subjectstr,')	
				  UNION ALL
				SELECT DISTINCT r.res_id,rtrim(ltrim(r.res_name)) res_name FROM rs_resource_info r WHERE r.GRADE IN (',p_gradestr,') AND SUBJECT IN (',p_subjectstr,')			  	
				) t2
				
				 ON t1.res_id=t2.res_id
				 ORDER BY t1.downloadnum desc
				LIMIT 0,',p_size);
				
				
				
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;	
	EXECUTE stmt;
END $$

DELIMITER ;
