DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_update_colscore`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_update_colscore`(				         
				            p_user_id BIGINT,	
				            p_class_id BIGINT,			        			         
				            p_course_id BIGINT,				         
				            p_dcschool_id BIGINT,
				            p_attendanceNum INT,
				            p_smilingNum INT,
				            p_violationDisNum INT,	
				              p_task_score INT	,
				            p_comment_score   INT,
				            p_group_score INT,
				            p_course_total_score BIGINT,
				            OUT affect_row INT
				          )
BEGIN
	DECLARE v_sql VARCHAR(10000) DEFAULT '';
	  SET v_sql=CONCAT('SELECT COUNT(REF) INTO @tmp_count FROM tp_stu_score where 1=1 ');
	IF p_user_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND user_id=",p_user_id);
	END IF;  
	IF p_course_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND course_id=",p_course_id);
	END IF;	
	SET v_sql=CONCAT(v_sql,' ORDER BY REF DESC LIMIT 0,1');
	
	SET @tmp_sql=v_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
		-- 得到subjectid
		SELECT cc.subject_id INTO @tmpsubjectid FROM tp_j_course_class cc,class_info cla WHERE cc.class_id=cla.class_id AND cc.class_id=p_class_id 
			AND cla.dc_school_id=p_dcschool_id AND cc.course_id=p_course_id LIMIT 0,1;         
		-- 得到当前的groupid
		 SELECT g.GROUP_ID 
		   INTO @tmp_groupid FROM j_class_user cu 
				INNER JOIN user_info u ON u.ref=cu.user_id
				INNER JOIN student_info s ON s.USER_ID=u.ref
				LEFT JOIN (
				SELECT gs.user_id,gs.GROUP_ID,g.GROUP_NAME,g.`CLASS_ID` FROM 
					tp_j_group_student gs,tp_group_info g 
					WHERE gs.GROUP_ID=g.GROUP_ID AND g.class_id=p_class_id AND class_type=1
					AND g.subject_id=@tmpsubjectid
				) g ON g.user_id=u.user_id 
			       WHERE  cu.class_id=p_class_id AND relation_type="学生" AND u.user_id=p_user_id LIMIT 0,1;		
	
	
	
	IF @tmp_count>0 THEN 
	
		SET v_sql=CONCAT('SELECT REF INTO @tmp_ref FROM tp_stu_score where 1=1 ');
		IF p_user_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND user_id=",p_user_id);
		END IF;  		
		IF p_course_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND course_id=",p_course_id);
		END IF;  		
		SET v_sql=CONCAT(v_sql,' ORDER BY REF DESC LIMIT 0,1');
		
		SET @tmp_sql=v_sql;
		PREPARE stmt FROM @tmp_sql  ;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
				
		IF @tmp_ref IS NOT NULL THEN
		
		SELECT cc.subject_id INTO @tmpsubjectid FROM tp_j_course_class cc,class_info cla WHERE cc.class_id=cla.class_id AND cc.class_id=p_class_id 
			AND cla.dc_school_id=p_dcschool_id AND cc.course_id=p_course_id LIMIT 0,1;         
		
		 SELECT g.GROUP_ID 
		   INTO @tmp_groupid FROM j_class_user cu 
				INNER JOIN user_info u ON u.ref=cu.user_id
				INNER JOIN student_info s ON s.USER_ID=u.ref
				LEFT JOIN (
				SELECT gs.user_id,gs.GROUP_ID,g.GROUP_NAME,g.`CLASS_ID` FROM 
					tp_j_group_student gs,tp_group_info g 
					WHERE gs.GROUP_ID=g.GROUP_ID AND g.class_id=p_class_id AND class_type=1
					AND g.subject_id=@tmpsubjectid
				) g ON g.user_id=u.user_id 
			       WHERE  cu.class_id=p_class_id AND relation_type="学生" AND u.user_id=p_user_id LIMIT 0,1;		
		
		     SET v_sql=CONCAT("UPDATE tp_stu_score set mtime=now()");
		     IF @tmp_groupid IS NOT NULL THEN
				SET v_sql=CONCAT(v_sql,",group_id=",@tmp_groupid);
		       ELSE
				SET v_sql=CONCAT(v_sql,",group_id=0");
		     END IF;
		     IF p_attendanceNum IS NOT NULL THEN
		        SET v_sql=CONCAT(v_sql,",attendance_num=IFNULL(attendance_num,0)+",p_attendanceNum);
		     END IF;
		     IF p_smilingNum IS NOT NULL THEN
		        SET v_sql=CONCAT(v_sql,",smiling_num=IFNULL(smiling_num,0)+",p_smilingNum);
		     END IF;
		     IF p_violationDisNum IS NOT NULL THEN
		        SET v_sql=CONCAT(v_sql,",violation_dis_num=IFNULL(violation_dis_num,0)+",p_violationDisNum);
		     END IF;
		     IF p_task_score IS NOT NULL THEN
		        SET v_sql=CONCAT(v_sql,",task_score=IFNULL(task_score,0)+",p_task_score);
		     END IF;
		     IF p_comment_score IS NOT NULL THEN
		        SET v_sql=CONCAT(v_sql,",comment_score=IFNULL(comment_score,0)+",p_comment_score);
		     END IF;
		     IF p_group_score IS NOT NULL THEN
			SET v_sql=CONCAT(v_sql,",group_score=IFNULL(group_score,0)+",p_group_score);
		     END IF;
		    IF p_course_total_score IS NOT NULL THEN
			SET v_sql=CONCAT(v_sql,",course_total_score=IFNULL(course_total_score,0)+",p_course_total_score);
		     END IF;
		      
		     SET v_sql=CONCAT(v_sql," WHERE ref=",@tmp_ref);		     
		     
		     SET @tmp_sql2=v_sql;
		     PREPARE stmt2 FROM @tmp_sql2  ;
		     EXECUTE stmt2;
		     DEALLOCATE PREPARE stmt2;
		     
		END IF;
         ELSE           
         
		CALL `tp_stu_score_add`(
						    p_user_id ,
						    p_attendanceNum ,
						    p_smilingNum ,
						    p_violationDisNum ,
						    @tmp_groupid ,				         
						    p_course_id ,
						    @tmpsubjectid,
						    p_class_id,
						    p_dcschool_id,
						    1,
						    p_task_score,
						    p_comment_score,
						    p_group_score,
						    p_course_total_score,					          
						    affect_row );
		  
	END IF;
	
	SET affect_row = 1;
	
	
	
END $$

DELIMITER ;
