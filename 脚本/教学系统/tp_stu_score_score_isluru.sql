DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_score_isluru`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_stu_score_score_isluru`(
				        p_user_id INT,
				        p_course_id BIGINT,
				        OUT afficeRows  INT
				          )
BEGIN
		SELECT COUNT(REF) INTO @affRows FROM tp_stu_score WHERE 1=1 AND user_id=p_user_id
		AND course_id=p_course_id AND submit_flag=1;
		
		SET afficeRows=@affRows;
 END$$

DELIMITER ;