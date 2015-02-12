DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `subject_proc_getbyUserYear`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `subject_proc_getbyUserYear`(
				          p_userid BIGINT,
				          p_year VARCHAR(20)
				          )
BEGIN
	 SELECT * FROM subject_info WHERE subject_id IN (
		SELECT DISTINCT cu.subject_id FROM j_class_user cu,class_info c WHERE
		  cu.class_id=c.class_id AND
		 user_id=(SELECT ref FROM user_info WHERE user_id=p_userid)
		 AND c.`YEAR`=p_year
	 ) ;
    END$$

DELIMITER ;