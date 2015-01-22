DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_getCourseName`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_getCourseName`(
				          p_res_id VARCHAR(1000)
				          )
BEGIN

	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	set tmp_sql=CONCAT("SELECT DISTINCT c.COURSE_ID,COURSE_NAME FROM tp_course_info c,tp_j_course_resource_info cr
			WHERE c.COURSE_ID=cr.course_id AND cr.res_id=",p_res_id);
	SET @sql1=tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;	
	
END $$

DELIMITER ;
