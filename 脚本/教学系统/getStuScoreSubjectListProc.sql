DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `getStuScoreSubjectListProc`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `getStuScoreSubjectListProc`(
								p_user_id VARCHAR(50),
								p_class_id INT,
								 OUT total INT)
BEGIN
	DECLARE	 tmp_sql VARCHAR(20000) DEFAULT '';
	
	IF p_user_id IS NULL THEN 
		SET tmp_sql="SELECT DISTINCT  s.subject_id,s.subject_name FROM tp_stu_score ss,subject_info s
		WHERE s.`SUBJECT_ID`=ss.`subject_id` AND ss.`class_type`<3 AND ss.course_total_score >0  "; 
		
		IF p_class_id IS NOT NULL THEN 
			SET tmp_sql=CONCAT(tmp_sql," AND ss.`class_id`=",p_class_id);
		END IF;
		 
		SET tmp_sql=CONCAT(tmp_sql," UNION SELECT DISTINCT  s.subject_id,s.subject_name FROM tp_stu_score ss,subject_info s
		WHERE s.`SUBJECT_ID`=ss.`subject_id` AND ss.`class_type`=3 ");
		
		IF p_class_id IS NOT NULL THEN 
			SET tmp_sql=CONCAT(tmp_sql," AND ss.`class_id`=",p_class_id);
		END IF;
		 
		SET tmp_sql=CONCAT(tmp_sql," AND ss.`submit_flag`=1 and ss.course_total_score >0");
	
	END IF;
	
	IF p_user_id IS NOT NULL THEN  
	
		SET tmp_sql=CONCAT("SELECT DISTINCT  s.subject_id,s.subject_name FROM tp_stu_score ss,subject_info s,j_class_user cu
		WHERE s.`SUBJECT_ID`=ss.`subject_id` 
		 and cu.class_id=ss.class_id and cu.user_id='",p_user_id,"'  and cu.subject_id=ss.subject_id
		 AND ss.`class_type`<3 AND ss.course_total_score >0  AND ss.`class_id`=",p_class_id,"
		 
		 UNION 
		 
		 SELECT DISTINCT  s.subject_id,s.subject_name FROM tp_stu_score ss,subject_info s,j_class_user cu
		WHERE s.`SUBJECT_ID`=ss.`subject_id`
		 and cu.class_id=ss.class_id  and cu.user_id='",p_user_id,"'  and cu.subject_id=ss.subject_id
		 AND ss.`class_type`=3 AND  ss.class_id=",p_class_id," AND ss.`submit_flag`=1 and ss.course_total_score >0");
	
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
	SET total=0;
END $$

DELIMITER ;
