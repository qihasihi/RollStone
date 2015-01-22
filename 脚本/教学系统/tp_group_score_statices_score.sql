DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_score_statices_score`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_score_statices_score`(				       
				         p_subject_id BIGINT,				         
					 p_class_id BIGINT,
					 p_sort VARCHAR(100)
				       )
BEGIN
	DECLARE tmp_sql VARCHAR(200000) DEFAULT '';
         
       
		 SET tmp_sql=CONCAT('
		  SELECT *,(
	SELECT COUNT(DISTINCT cu.user_id)+1
		 FROM j_class_user cu
		INNER JOIN user_info u ON cu.`USER_ID`=u.ref
		INNER JOIN student_info s ON s.`USER_ID`=u.ref
		LEFT JOIN 
		(
		
		SELECT tsc.class_id,tsc.user_id tsuser_id,
			IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,SUM(course_total_score),0) kejf				
			FROM (
			SELECT DISTINCT tsc.`user_id`,tsc.course_id,tsc.class_id,tsc.user_id tsuser_id,attendance_num,smiling_num,
			violation_dis_num,task_score,comment_score,group_score,course_total_score,tsc.`submit_flag`,tsc.`subject_id`
			 FROM tp_stu_score  tsc		
			LEFT JOIN (
				SELECT gs.user_id,gs.GROUP_ID,g.GROUP_NAME,g.`CLASS_ID` FROM 
					tp_j_group_student gs,tp_group_info g 
					WHERE gs.GROUP_ID=g.GROUP_ID AND g.class_id=',p_class_id,' AND class_type=1
					AND g.subject_id=',p_subject_id,'				
			) g ON g.user_id=tsc.user_id 
			LEFT JOIN tp_group_score gs ON gs.group_id=g.group_id	 
			WHERE tsc.class_id=',p_class_id,' AND tsc.`subject_id`=',p_subject_id,'
		) tsc GROUP BY class_id,tsc.user_id		
		
		
		
		) t1 ON u.`USER_ID`=t1.tsuser_id 
		WHERE cu.class_id=',p_class_id,'
		AND IFNULL(t.course_total_score,-1)<IFNULL(t1.kejf,-1)			
  ) rnum
			 FROM (			 
		SELECT t.*,u.user_id,IFNULL((SELECT SUM(attendance_num) FROM tp_stu_score WHERE user_id=u.user_id AND subject_id=',p_subject_id,' AND class_id=',p_class_id,'),0)  cqs
		,IFNULL(task_score,0) rws,s.`STU_NO`,s.`STU_NAME` FROM j_class_user cu
		INNER JOIN user_info u ON cu.`USER_ID`=u.ref
		INNER JOIN student_info s ON s.`USER_ID`=u.ref
		LEFT JOIN 
		(
		
		
		SELECT class_id,tsc.user_id tsuser_id,SUM(IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,IFNULL(attendance_num,0),0)+IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,IFNULL(smiling_num,0),0)+IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,IFNULL(violation_dis_num,0),0)) wxdf,
			SUM(IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,IFNULL(task_score,0),0)+IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,IFNULL(comment_score,0),0)) wsdf	,
			SUM(IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,IFNULL(tsc.group_score,0),0)) group_score,
			IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,SUM(course_total_score),0) kejf, IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,SUM(course_total_score),0) course_total_score,tsc.subject_id,IF(tsc.SUBMIT_FLAG IS NOT NULL AND tsc.SUBMIT_FLAG=1,SUM(task_score),0) task_score,tsc.submit_flag					
		FROM (
			SELECT DISTINCT tsc.`user_id`,tsc.course_id,tsc.class_id,tsc.user_id tsuser_id,attendance_num,smiling_num,
			violation_dis_num,task_score,comment_score,group_score,course_total_score,tsc.`submit_flag`,tsc.`subject_id`
			 FROM tp_stu_score  tsc		
			LEFT JOIN (
				SELECT gs.user_id,gs.GROUP_ID,g.GROUP_NAME,g.`CLASS_ID` FROM 
					tp_j_group_student gs,tp_group_info g 
					WHERE gs.GROUP_ID=g.GROUP_ID AND g.class_id=',p_class_id,' AND class_type=1
					AND g.subject_id=',p_subject_id,'				
			) g ON g.user_id=tsc.user_id 
			LEFT JOIN tp_group_score gs ON gs.group_id=g.group_id	 
			WHERE tsc.class_id=',p_class_id,' AND tsc.`subject_id`=',p_subject_id,'
		) tsc GROUP BY class_id,tsc.user_id
		) t
		ON u.`USER_ID`=t.tsuser_id		
		WHERE  cu.`CLASS_ID`=',p_class_id,' AND u.STATE_ID=0 
		ORDER BY course_total_score DESC
		)t ORDER BY rnum ',IFNULL(p_sort,'asc'),',stu_no  '); 
       
       
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
	
END $$

DELIMITER ;
