DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_myres_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_myres_proc_split`(
				          p_res_id VARCHAR(1000),
				          p_res_name VARCHAR(1000),				      		    
				          p_user_id INT,
				          p_grade INT,
				          p_subject INT,
				          p_file_type INT,
				          p_res_type INT,				       
				          p_res_status INT,
				          p_res_degree INT,				          
				          p_share_status INT,
				          p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE total_sql VARCHAR(20000) DEFAULT '';
        SET tmp_sql="
	SELECT DISTINCT aa.*,IFNULL(t.teacher_name,IFNULL(s.stu_name,u.user_name)) realname,
		(SELECT COUNT(*) FROM rs_resource_info WHERE user_id=aa.user_id) resnum,
		IFNULL(sub.SUBJECT_NAME,'--') SUBJECT_NAME,
		IFNULL(g.GRADE_NAME,'--') GRADE_NAME,
		IFNULL(d1.DICTIONARY_NAME,'--') RES_TYPE_NAME,
		IFNULL(d2.DICTIONARY_NAME,'--') FILE_TYPE_NAME FROM (
		
		
		SELECT r.* FROM rs_resource_info r INNER JOIN (
			SELECT r.res_id,c_time FROM (SELECT r.res_id,r.c_time  FROM rs_resource_info r WHERE 1=1 ";
			
		IF p_res_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_ID=",p_res_id);
		END IF;			
		IF p_res_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_NAME LIKE '%",p_res_name,"%'");
		END IF;			
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.USER_ID=",p_user_id);
		END IF;	
		IF p_grade IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.grade=",p_grade);
		END IF;	
		IF p_subject IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.subject=",p_subject);
		END IF;	
		IF p_file_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.file_type=",p_file_type);
		END IF;	
		IF p_res_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_type=",p_res_type);
		END IF;	
		IF p_res_status IS NOT NULL THEN
		      IF p_res_status=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status<>3");
		      END IF;
		      IF p_res_status<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status=",p_res_status);
		      END IF;
		END IF;		
		
		IF p_res_degree IS NOT NULL THEN
		      IF p_res_degree=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree<>3");
		      END IF;
		      IF p_res_degree<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree=",p_res_degree);
		      END IF;
		END IF;	
		
		
		 SET tmp_sql=CONCAT(tmp_sql," ) r
			UNION 
			SELECT r.RES_ID,r.c_time FROM rs_resource_info r
			INNER JOIN tp_j_course_resource_info cr ON r.RES_ID=cr.res_id
			INNER JOIN tp_j_course_teaching_material ctm ON ctm.course_id=cr.course_id
			INNER JOIN teaching_material_info tm ON tm.material_id=ctm.teaching_material_id
			WHERE 1=1
			");
			IF p_res_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_ID=",p_res_id);
		END IF;			
		IF p_res_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_NAME LIKE '%",p_res_name,"%'");
		END IF;			
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.USER_ID=",p_user_id);
		END IF;	
		IF p_grade IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND tm.grade_id=",p_grade);
		END IF;	
		IF p_subject IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND tm.subject_id=",p_subject);
		END IF;	
		IF p_file_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.file_type=",p_file_type);
		END IF;	
		IF p_res_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_id=",p_res_type);
		END IF;	
		IF p_res_status IS NOT NULL THEN
		      IF p_res_status=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status<>3");
		      END IF;
		      IF p_res_status<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status=",p_res_status);
		      END IF;
		END IF;		
		
		IF p_res_degree IS NOT NULL THEN
		      IF p_res_degree=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree<>3");
		      END IF;
		      IF p_res_degree<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree=",p_res_degree);
		      END IF;
		END IF;	
			
			
			
			
			
	IF p_sort_column IS NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  c_time DESC ");
	    ELSE
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	        
	END IF;
	
     IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
		
		
	SET tmp_sql=CONCAT(tmp_sql,") tr ON tr.res_id=r.RES_ID		
		) aa 
		LEFT JOIN user_info u ON u.user_id=aa.user_id
		LEFT JOIN teacher_info t ON t.user_id=u.ref
		LEFT JOIN student_info s ON s.user_id=u.ref
		
		LEFT JOIN subject_info sub ON sub.SUBJECT_ID=aa.SUBJECT
		LEFT JOIN grade_info g ON g.GRADE_ID=aa.GRADE
		LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=aa.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'  
		LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=aa.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE'
        
        ");
	
	
	SET @sql1=tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
	SET tmp_sql=CONCAT("
	
		SELECT COUNT(DISTINCT r.res_id) FROM rs_resource_info r INNER JOIN (
			SELECT r.res_id FROM (SELECT r.res_id FROM rs_resource_info r WHERE 1=1 ");
			
		IF p_res_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_ID=",p_res_id);
		END IF;			
		IF p_res_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_NAME LIKE '%",p_res_name,"%'");
		END IF;			
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.USER_ID=",p_user_id);
		END IF;	
		IF p_grade IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.grade=",p_grade);
		END IF;	
		IF p_subject IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.subject=",p_subject);
		END IF;	
		IF p_file_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.file_type=",p_file_type);
		END IF;	
		IF p_res_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_id=",p_res_type);
		END IF;	
		IF p_res_status IS NOT NULL THEN
		      IF p_res_status=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status<>3");
		      END IF;
		      IF p_res_status<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status=",p_res_status);
		      END IF;
		END IF;		
		
		IF p_res_degree IS NOT NULL THEN
		      IF p_res_degree=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree<>3");
		      END IF;
		      IF p_res_degree<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree=",p_res_degree);
		      END IF;
		END IF;	
		
		
		 SET tmp_sql=CONCAT(tmp_sql," ) r
			UNION 
			SELECT r.RES_ID FROM rs_resource_info r
			INNER JOIN tp_j_course_resource_info cr ON r.RES_ID=cr.res_id
			INNER JOIN tp_j_course_teaching_material ctm ON ctm.course_id=cr.course_id
			INNER JOIN teaching_material_info tm ON tm.material_id=ctm.teaching_material_id
			WHERE 1=1
			");
			IF p_res_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_ID=",p_res_id);
		END IF;			
		IF p_res_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.RES_NAME LIKE '%",p_res_name,"%'");
		END IF;			
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.USER_ID=",p_user_id);
		END IF;	
		IF p_grade IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND tm.grade_id=",p_grade);
		END IF;	
		IF p_subject IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND tm.subject_id=",p_subject);
		END IF;	
		IF p_file_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.file_type=",p_file_type);
		END IF;	
		IF p_res_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_id=",p_res_type);
		END IF;	
		IF p_res_status IS NOT NULL THEN
		      IF p_res_status=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status<>3");
		      END IF;
		      IF p_res_status<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_status=",p_res_status);
		      END IF;
		END IF;		
		
		IF p_res_degree IS NOT NULL THEN
		      IF p_res_degree=-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree<>3");
		      END IF;
		      IF p_res_degree<>-3 THEN
			SET tmp_sql=CONCAT(tmp_sql," AND r.res_degree=",p_res_degree);
		      END IF;
		END IF;	
			
			
		
	SET tmp_sql=CONCAT(tmp_sql,") tr ON tr.res_id=r.RES_ID ");
	
	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
