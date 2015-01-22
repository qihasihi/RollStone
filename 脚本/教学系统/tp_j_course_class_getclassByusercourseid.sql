DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_getclassByusercourseid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_getclassByusercourseid`(
				         p_course_id   BIGINT,				       
				         p_userid iNT,
				         p_termid VARCHAR(100)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
        SET tmp_sql=CONCAT('
		SELECT DISTINCT cc.* FROM tp_j_course_class cc
		LEFT JOIN j_class_user cu ON cu.CLASS_ID=cc.class_id AND cc.class_type=1
		LEFT JOIN tp_j_virtual_class_student vc ON vc.VIRTUAL_CLASS_Id=cc.class_id AND cc.class_type=2
		 WHERE course_id=',p_course_id,' AND cc.TERM_ID="',p_termid,'"
		 AND (vc.user_id=',p_userid,' OR cu.user_id =(SELECT ref FROM user_info WHERE user_id=',p_userid,'))
	');
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  	
	
END $$

DELIMITER ;
