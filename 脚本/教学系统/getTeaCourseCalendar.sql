DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `getTeaCourseCalendar`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `getTeaCourseCalendar`(
					  p_user_id INT,
					  p_class_id INT,
					   p_school_id INT,
					  p_year INT,
				          p_month INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	
	IF p_user_id IS NOT NULL AND
	   p_school_id IS NOT NULL AND 
	   p_year IS NOT NULL AND 
	    p_month IS NOT NULL THEN
	      
	      IF p_class_id IS NULL THEN
		      SET tmp_sql=CONCAT("
			SELECT e.e_day,IF(e.e_date<NOW(),1,0)flag,(SELECT COUNT(*)
			  FROM tp_course_info tc,tp_j_course_class cc,user_info u
			 WHERE tc.course_id=cc.course_id AND u.user_id=tc.cuser_id
			   AND tc.cuser_id=",p_user_id,"
			   AND u.dc_school_id=",p_school_id,"
			   AND (DATE_FORMAT(cc.`begin_time`,'%y-%m-%d') = e.e_date  OR DATE_FORMAT(cc.`end_time`,'%y-%m-%d') = e.e_date  OR (cc.begin_time <e.e_date AND IFNULL(cc.end_time,DATE_ADD(NOW(),INTERVAL 500 YEAR)) >e.e_date) )
			   ) hascourse
			   FROM every_day e WHERE  e.e_year=",p_year," AND e.e_month=",p_month,"");	
	       END IF;
        END IF;
	
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
