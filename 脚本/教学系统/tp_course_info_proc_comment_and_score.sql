DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_comment_and_score`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_comment_and_score`(
				            p_course_id LONG,
				            p_score float,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET affect_row = 0;
	update tp_course_info set AVG_SCORE=(AVG_SCORE*COMMENT_NUM+p_score)/(COMMENT_NUM+1),COMMENT_NUM=COMMENT_NUM+1 where COURSE_ID=p_course_id;
	SET affect_row = 1;
	
END $$

DELIMITER ;
