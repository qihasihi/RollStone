DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_tp_stu_statices_group_score`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `cal_tp_stu_statices_group_score`(
					p_task_id BIGINT,
					p_class_id BIGINT,
					p_user_id BIGINT,
					p_course_id BIGINT,
					p_dc_school_id BIGINT,
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_allowAddScore BIGINT DEFAULT 1;  
	DECLARE tmp_group_score BIGINT DEFAULT 0;
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';	
	
	SELECT dc_type INTO @dcType FROM class_info WHERE class_id=p_class_id;
	SET sumCount=1;
        IF @dcType<>3 THEN			
			-- 查询学科
			SELECT subject_id INTO @subjectId FROM tp_j_course_teaching_material ctm ,teaching_material_info tm 
			WHERE tm.material_id=ctm.teaching_material_id	AND ctm.course_id=p_course_id LIMIT 0,1;
			
			IF @subjectId IS NOT NULL THEN         
				 
				SELECT COUNT(DISTINCT gs.user_id) INTO @groupStuCount  FROM tp_j_group_student gs,user_info u WHERE gs.USER_ID=u.user_id AND u.STATE_ID=0 
				AND group_id IN (
						SELECT gs.group_id FROM  tp_j_group_student gs,tp_group_info g WHERE g.group_id=gs.group_id AND gs.user_id=p_user_id
						AND g.class_id=p_class_id AND g.subject_id=@subjectId
				);	
				IF @groupStuCount>0 THEN					
					SELECT COUNT(DISTINCT user_id) INTO @allCompleCount FROM `tp_task_performance` WHERE task_id=p_task_id 
					 AND user_id IN ( 
						SELECT u.ref FROM  tp_j_group_student gs,user_info u WHERE gs.user_id=u.user_id  AND u.STATE_ID=0
						AND group_id=(
							SELECT tgs.group_id FROM tp_j_group_student tgs,tp_group_info tg WHERE tg.group_id=tgs.group_id
							AND tg.class_id=p_class_id AND tg.subject_id=@subjectId AND tgs.user_id=p_user_id
							LIMIT 0,1
						)
					 
					 );					
					 IF @groupStuCount=@allCompleCount THEN
						UPDATE tp_stu_score SET mtime=NOW()
							,GROUP_SCORE=IFNULL(group_score,0)+1
							,course_total_score=IFNULL(course_total_score,0)+1
							WHERE course_id=p_course_id AND class_id=p_class_id AND subject_id=@subjectId AND group_id=(
								SELECT tgs.group_id FROM tp_j_group_student tgs,tp_group_info tg WHERE tg.group_id=tgs.group_id
								AND tg.class_id=p_class_id AND tg.subject_id=@subjectId AND tgs.user_id=p_user_id
								LIMIT 0,1
						) AND user_id NOT IN (
							SELECT user_id FROM tp_stu_group_score_log
							WHERE task_id=p_task_id AND course_id=p_course_id AND dc_school_id=p_dc_school_id
						);
						
						
						-- 向记录表中添加数据
						SET tmp_sql=CONCAT('
							INSERT INTO tp_stu_group_score_log(task_id,course_id,user_id,c_time,dc_school_id)
							SELECT ',p_task_id,',',p_course_id,',tgs.user_id,now(),',p_dc_school_id,' 
							FROM tp_j_group_student tgs,tp_group_info tg WHERE tg.group_id=tgs.group_id
							AND tg.class_id=',p_class_id,' AND tg.subject_id=',@subjectId,' AND 
							tgs.group_id=(
									SELECT tgs.group_id FROM tp_j_group_student tgs,tp_group_info tg WHERE tg.group_id=tgs.group_id
									AND tg.class_id=',p_class_id,' AND tg.subject_id=',@subjectId,' AND tgs.user_id=',p_user_id,'
									LIMIT 0,1
							) AND user_id NOT IN (
								SELECT user_id FROM tp_stu_group_score_log
								WHERE task_id=',p_task_id,' AND course_id=',p_course_id,' AND dc_school_id=',p_dc_school_id,' 
							)	');			
						SET @tmp_sql = tmp_sql;
						PREPARE stmt FROM @tmp_sql;
						EXECUTE stmt;
						
						COMMIT;
					 END IF;
				END IF;
			END IF;
		END IF;	
	
    END$$

DELIMITER ;