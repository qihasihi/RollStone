DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_teach_statices`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_teach_statices`(
				         p_term_id   VARCHAR(100),
				         p_class_id INT,
				         p_subject_id INT,		
				         p_order_by INT   -- 1 OR NULL 正序   否则则 ：逆序
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
         /*如果class_type==1  则*/
		 SET tmp_sql=CONCAT('
		 
SELECT t.*,(
SELECT COUNT(*)+1 FROM (SELECT SUM(tp.course_total_score) course_total_score				
			FROM tp_stu_score tp WHERE tp.subject_id=',p_subject_id,' AND tp.class_id=',p_class_id,' AND submit_flag=1 AND tp.`course_id` IN (
			SELECT course_id FROM tp_j_course_class cc WHERE cc.class_id=',p_class_id,' AND term_id="',p_term_id,'" AND cc.subject_id=',p_subject_id,'
		   )
		GROUP BY `user_id`, `subject_id`, `class_id`, `dc_school_id`, `class_type`		
		) A WHERE 1=1
                AND A.course_total_score>t.course_total_score
                
                )  rank FROM (
               
SELECT DISTINCT s.STU_NO,s.STU_NAME,t1.user_id,
		
		 ROUND(IFNULL((	
			SELECT COUNT(DISTINCT task_id) FROM tp_task_performance WHERE user_id=(SELECT ref FROM user_info tu WHERE tu.user_id=t1.user_id)
				AND TASK_ID IN (
					SELECT ta.task_id FROM tp_task_allot_info ta WHERE  ta.e_time<NOW() AND course_id IN (
						SELECT course_id FROM tp_j_course_class cc WHERE cc.class_id=',p_class_id,' AND term_id="',p_term_id,'" AND cc.subject_id=',p_subject_id,'
					) AND ((
						user_type=0 AND user_type_id=',p_class_id,'
					) OR ( user_type=2 AND user_type_id IN (
							SELECT gs.group_id FROM tp_j_group_student gs,tp_group_info g WHERE 
								gs.group_id=g.group_id AND gs.user_id=t1.user_id
								 AND g.subject_id=',p_subject_id,'
								  AND class_id=',p_class_id,'
							)
						)
					)
				)
		 )/
			-- 发给班级的
			(IFNULL((SELECT COUNT(*) FROM `tp_task_allot_info` WHERE user_type=0 AND user_type_id=',p_class_id,' AND course_id IN (
				 SELECT course_id FROM tp_j_course_class cc WHERE cc.class_id=',p_class_id,' AND term_id="',p_term_id,'" AND cc.subject_id=',p_subject_id,'		
			 ) AND e_time<NOW()),0)+
			 -- 发给小组的
			 IFNULL((SELECT COUNT(*) FROM `tp_task_allot_info` ta WHERE user_type=2 AND e_time<NOW() AND  user_type_id IN (
				SELECT DISTINCT g.group_id FROM tp_j_group_student gs,tp_group_info g WHERE g.group_id=gs.group_id 
					AND g.class_id=',p_class_id,' AND term_id="',p_term_id,'" AND g.subject_id=',p_subject_id,'
					AND gs.user_id=t1.user_id 
			 ) ),0)),0)*100,2) complateTaskBl,
		IFNULL(wsScore,0) wsScore, 
		 IFNULL(wxScore,0) wxScore
		   ,IFNULL(tcp.group_score,0) group_score,IFNULL(tcp.`course_total_score`,0) course_total_score
		FROM(
	       SELECT s.STU_NAME,s.stu_no,g.group_name,g.group_id,u.user_id  FROM j_class_user cu 
		INNER JOIN user_info u ON u.ref=cu.user_id
		INNER JOIN student_info s ON s.USER_ID=u.ref
		LEFT JOIN (
		SELECT gs.user_id,gs.GROUP_ID,g.GROUP_NAME,g.`CLASS_ID` FROM 
			tp_j_group_student gs,tp_group_info g 
			WHERE gs.GROUP_ID=g.GROUP_ID AND g.class_id=',p_class_id,' AND class_type=1
			AND g.subject_id=',p_subject_id,'
		
		) g ON g.user_id=u.user_id 
	        WHERE  cu.class_id=',p_class_id,' AND relation_type="学生" AND u.STATE_ID=0 
	       ) t1
		LEFT JOIN (
			SELECT user_id,subject_id,class_id,dc_school_id,class_type,
			SUM(tp.violation_dis_num) violation_dis_num,
			SUM(tp.smiling_num) smiling_num,
			SUM(tp.attendance_num) attendance_num,
			SUM(tp.course_total_score) course_total_score,
			SUM(tp.group_score) group_score,
			SUM(tp.`comment_score`) comment_score	,
			SUM(tp.`task_score`) task_score	,
			SUM(IFNULL(tp.violation_dis_num,0))+SUM(IFNULL(tp.attendance_num,0))+SUM(IFNULL(tp.smiling_num,0)) wxScore,
			SUM(IFNULL(tp.comment_score,0))+SUM(IFNULL(tp.task_score,0)) wsScore,
			submit_flag		
			FROM tp_stu_score tp WHERE tp.subject_id=',p_subject_id,' AND tp.class_id=',p_class_id,' AND tp.submit_flag=1  AND tp.`course_id` IN (
			SELECT course_id FROM tp_j_course_class cc WHERE cc.class_id=',p_class_id,' AND term_id="',p_term_id,'" AND cc.subject_id=',p_subject_id,'
		   )
		GROUP BY `user_id`, `subject_id`, `class_id`, `dc_school_id`, `class_type`		
		) tcp  ON t1.user_id=tcp.user_id 
			
		
		LEFT JOIN 
			(SELECT SUM(award_number) award_number,group_id from tp_group_score cpad where group_id IS NOT NULL AND cpad.subject_id=',p_subject_id,' AND cpad.submit_flag=1)
		cpad ON IF(t1.group_id IS NULL,FALSE,(t1.group_id=cpad.group_id)) 
		INNER JOIN user_info u ON u.user_id=t1.user_id 
		LEFT JOIN student_info s ON s.user_id=u.ref 
		WHERE u.STATE_ID=0 	
	) t ORDER BY rank ',IF(p_order_by IS NULL OR p_order_by=1,'asc','desc'),', t.STU_NO ');
       
       
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
	
END $$

DELIMITER ;
