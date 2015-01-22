DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_idx_grouper_coursescore_isover`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_idx_grouper_coursescore_isover`(p_cls_id INT,p_subject_id INT,p_courseidstr VARCHAR(10000),p_groupidstr VARCHAR(10000),p_role_type INT)
BEGIN
	DECLARE tmp_sql VARCHAR(50000) DEFAULT '';
	
	
	IF p_role_type IS NULL OR p_role_type=1 THEN
		SET tmp_sql=CONCAT('
			SELECT 0 group_id,t.GROUPCOUNT,t1.STUSCORECOUNT,t1.course_id FROM (
				SELECT 0 group_id,COUNT(distinct cu.user_id) groupcount FROM j_class_user cu WHERE cu.`RELATION_TYPE`="Ñ§Éú" AND class_id=',p_cls_id,') t,
			(SELECT 0 group_id,course_id,COUNT(DISTINCT user_id) stuScoreCount FROM tp_stu_score WHERE class_id=',p_cls_id,'  AND subject_id=',p_subject_id,'  AND submit_flag=1
				AND course_id IN (
						',p_courseidstr,'
				)
				GROUP BY course_id
			) t1
			WHERE t.group_id=t1.group_id
		');
		IF p_groupidstr IS NOT NULL THEN
		  SET tmp_sql=CONCAT(tmp_sql,' AND t.GROUP_ID=',p_groupidstr);
		END IF;
	ELSE
		SET tmp_sql=CONCAT('
			SELECT t.GROUP_ID,t.GROUPCOUNT,t1.STUSCORECOUNT,t1.course_id FROM (
				SELECT gs.GROUP_ID,COUNT(DISTINCT gs.user_id) groupCount FROM tp_j_group_student gs,tp_group_info g
				WHERE gs.group_id=g.group_id AND g.class_id=',p_cls_id,' AND g.subject_id=',p_subject_id,'
				GROUP BY GROUP_ID) t,
			(SELECT GROUP_ID,course_id,COUNT(DISTINCT user_id) stuScoreCount FROM tp_stu_score WHERE class_id=',p_cls_id,'  AND subject_id=',p_subject_id,' AND submit_flag=1
				AND course_id IN (
						',p_courseidstr,'
				)
				GROUP BY group_id,course_id
			) t1
			WHERE t.group_id=t1.group_id AND t.GROUP_ID IN (
			',p_groupidstr,'
			)
		');	
	
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
