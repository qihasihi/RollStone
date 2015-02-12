DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `grade_proc_getbyUserYear`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `grade_proc_getbyUserYear`(
				          p_userid BIGINT,
				          p_year VARCHAR(20)
				          )
BEGIN
	SELECT * FROM grade_info WHERE grade_value IN (
	SELECT DISTINCT c.`CLASS_GRADE` FROM j_class_user cu,class_info c WHERE
	 c.class_id=cu.class_id AND
	 user_id=(SELECT ref FROM user_info WHERE user_id=p_userid)
	 AND c.`YEAR`=p_year
	);
 
    END$$

DELIMITER ;