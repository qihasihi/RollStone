DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_ck_allComplateInput`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_ck_allComplateInput`(
				          p_courseid BIGINT,
				          p_classid INT,
				          p_subjectid INT,
				          p_school_id INT,
				         OUT afficeRows  INT				          
				          )
BEGIN
	SET afficeRows=1;
	
	SELECT COUNT(DISTINCT s.user_id) INTO @inputCount FROM tp_stu_score s,j_class_user cu,class_info cla
		WHERE s.class_id=cu.class_id AND cu.user_id=(SELECT ref FROM user_info WHERE user_id=s.user_id) 
			AND cla.class_id=cu.class_id 
			AND cla.dc_school_id=s.dc_school_id 
			AND s.course_id=p_courseid 
			AND s.subject_id=p_subjectid
			AND s.class_id=p_classid AND s.dc_school_id=p_school_id
			AND s.submit_flag=1;
	
	SELECT COUNT(DISTINCT cu.user_id) INTO @clsCount FROM j_class_user cu,class_info c WHERE c.class_id=cu.class_id
		AND cu.class_id=p_classid AND dc_school_id=p_school_id AND relation_type='Ñ§Éú';
	
	IF @inputCount<>0 AND @inputCount=@clsCount THEN
	    CALL cal_tp_total_score(p_school_id,p_classid,p_courseid,p_subjectid,afficeRows);
	END IF;
		
END $$

DELIMITER ;
