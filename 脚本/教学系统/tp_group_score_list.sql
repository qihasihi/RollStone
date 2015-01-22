DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_score_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_score_list`(
				         p_course_id   BIGINT,
				         p_class_id INT,
				         p_class_type  INT,
				         p_subject_id INT,				         
				         p_group_idArray VARCHAR(2000),
				         p_user_id BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
         
       IF p_class_type IS NULL OR p_class_type=1 THEN
		 SET tmp_sql=CONCAT('
		      SELECT DISTINCT t1.user_id,g.group_id,g.group_name,IFNULL(cpad.award_number,0) award_number,
		      IFNULL(cpad.score1,0) gscore1,IFNULL(cpad.score2,0) gscore2,IFNULL(cpad.score3,0)gscore3,IFNULL(cpad.score4,0) gscore4,IFNULL(cpad.score5,0) gscore5
		      ,s.STU_NO,s.STU_NAME,IFNULL(tcp.attendance_num,2)attendancenum,tcp.course_total_score,tcp.task_score,tcp.comment_score,tcp.group_score
		,IFNULL(tcp.smiling_num,0) smilingnum,IFNULL(tcp.violation_dis_num,0) violationdisnum,
		IFNULL((SELECT SUM(score) FROM tp_stu_score_logs WHERE user_id=u.user_id AND course_id=',p_course_id,'),0) wsScore
		,tcp.ref tcpref,IFNULL(tcp.submit_flag,0) submit_flag,IFNULL(cpad.submit_flag,0) groupsubmit_flag  FROM(
		       SELECT s.STU_NAME,s.stu_no,u.user_id  FROM j_class_user cu 
			INNER JOIN user_info u ON u.ref=cu.user_id
			INNER JOIN student_info s ON s.USER_ID=u.ref		
		       WHERE  cu.class_id=',p_class_id,' AND relation_type="学生"
	       ) t1		
		LEFT JOIN (
			SELECT tcp.attendance_num,smiling_num,violation_dis_num,course_total_score,group_score,submit_flag,comment_score,task_score
			,tcp.ref,tcp.user_id,tcp.subject_id,tcp.course_id,tcp.group_id FROM (	SELECT tcp.* FROM tp_stu_score tcp 
					WHERE tcp.`class_id`=',p_class_id,' AND tcp.`subject_id`=',p_subject_id,' AND tcp.course_id=',p_course_id,') tcp
				
		) tcp ON t1.user_id=tcp.user_id 
		
		LEFT JOIN tp_group_score cpad ON tcp.group_id=cpad.group_id AND cpad.course_id=',p_course_id,' AND cpad.subject_id=',p_subject_id,'
		LEFT JOIN (
			SELECT g.class_id,g.group_id,g.group_name,gs.user_id,g.`subject_id` FROM 
				tp_j_group_student gs,tp_group_info g WHERE g.group_id=gs.group_id AND g.subject_id=',p_subject_id,'
				AND g.class_id=',p_class_id,'
		) g ON t1.user_id=g.user_id		
		INNER JOIN user_info u ON u.user_id=t1.user_id 
		LEFT JOIN student_info s ON s.user_id=u.ref WHERE 1=1 AND u.STATE_ID=0 ');
		
		
		
		IF LENGTH(p_group_idArray)>0 THEN
			SET tmp_sql=CONCAT(tmp_sql,' AND g.group_id IN(',p_group_idArray,')');
		END IF;
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,' AND t1.user_id=',p_user_id);
		END IF;
		SET tmp_sql=CONCAT(tmp_sql,'
			ORDER BY IFNULL(g.group_id,9999),s.stu_no    
		 ');       
       ELSE
          	 SET tmp_sql=CONCAT('
		      SELECT DISTINCT t1.user_id,tcp.group_id,tcp.group_name,IFNULL(cpad.award_number,0) award_number,
		      IFNULL(tcp.score1,0) gscore1,IFNULL(tcp.score2,0) gscore2,IFNULL(tcp.score3,0)gscore3,IFNULL(tcp.score4,0) gscore4,IFNULL(tcp.score5,0) gscore5
		      ,s.STU_NO,s.STU_NAME,IFNULL(tcp.attendance_num,2)attendancenum,tcp.course_total_score,tcp.task_score,tcp.comment_score,tcp.group_score
		,IFNULL(tcp.smiling_num,0) smilingnum,IFNULL(tcp.violation_dis_num,0) violationdisnum,
		IFNULL((SELECT SUM(score) FROM tp_stu_score_logs WHERE user_id=u.user_id AND course_id=', p_course_id,'),0) wsScore
		,tcp.ref tcpref,IFNULL(tcp.submit_flag,0) submit_flag,IFNULL(cpad.submit_flag,0) groupsubmit_flag  FROM(
		       SELECT s.STU_NAME,s.stu_no,u.user_id  FROM j_class_user cu 
			INNER JOIN user_info u ON u.ref=cu.user_id
			INNER JOIN student_info s ON s.USER_ID=u.ref		
		       WHERE  cu.class_id=',p_class_id,' AND relation_type="学生"
	       ) t1		
		LEFT JOIN (
			 SELECT tcp.*,(SELECT g.group_name FROM 
				tp_j_group_student gs,tp_group_info g WHERE g.group_id=gs.group_id AND g.subject_id=',p_subject_id,'
					AND gs.user_id=tcp.`user_id` AND g.class_id=tcp.`class_id`
				) group_name
				 FROM tp_stu_score tcp 			
			 WHERE tcp.class_id=',p_class_id,' AND tcp.subject_id=',p_subject_id,'	
		) tcp ON t1.user_id=tcp.user_id AND tcp.subject_id=',p_subject_id,' AND tcp.course_id=', p_course_id,'
		LEFT JOIN tp_group_score cpad ON IF(tcp.group_id IS NULL,TRUE,(tcp.group_id=cpad.group_id)) AND cpad.course_id=', p_course_id,' AND cpad.subject_id=',p_subject_id,'
		INNER JOIN user_info u ON u.user_id=t1.user_id 
		LEFT JOIN student_info s ON s.user_id=u.ref WHERE 1=1 AND u.STATE_ID=0 ');
		
		
		
		IF LENGTH(p_group_idArray)>0 THEN
			SET tmp_sql=CONCAT(tmp_sql,' AND tcp.group_id IN(',p_group_idArray,')');
		END IF;
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,' AND t1.user_id=',p_user_id);
		END IF;
		SET tmp_sql=CONCAT(tmp_sql,'
			ORDER BY IFNULL(tcp.group_id,9999),s.stu_no    
		 ');       
       
       END IF;
       
       
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
	
END $$

DELIMITER ;
