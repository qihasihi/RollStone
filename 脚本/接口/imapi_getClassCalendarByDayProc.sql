DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_getClassCalendarByDayProc`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_getClassCalendarByDayProc`(
                                          p_class_id int,
					  p_user_id INT,
					  p_school_id INT,
					  p_user_type int,
					  p_datetime varchar(50)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE currentyear VARCHAR(100) DEFAULT '';
	SELECT t.year INTO @cuyear FROM term_info t WHERE NOW() BETWEEN t.semester_begin_date AND t.semester_end_date;
	SET currentyear = @cuyear;
	
	IF p_user_type IS NOT NULL AND p_user_type=2 THEN 
	      SET tmp_sql=CONCAT("
	      SELECT DISTINCT tc.course_id,tc.course_name,cc.begin_time,cc.end_time, CASE cc.class_type WHEN 1 THEN (SELECT class_type FROM class_info WHERE class_id=cc.class_id)
		      WHEN 2 THEN 1 END class_type,u.dc_school_id,cc.class_id,CASE cc.class_type WHEN 1 THEN (SELECT concat(class_grade,class_name) FROM class_info WHERE class_id=ju.class_id)
		      END classname
		  FROM tp_course_info tc,tp_j_course_class cc,user_info u,class_info ci,j_class_user ju
		 WHERE tc.course_id=cc.course_id  AND u.user_id=tc.cuser_id and cc.class_id=ju.class_id and ju.class_id=ci.class_id and ju.user_id=u.ref and ju.relation_type='»ŒøŒ¿œ ¶' AND cc.subject_id = ju.subject_id
		 and ci.year='",currentyear,"'
		   AND tc.local_status=1
		   AND tc.cuser_id=",p_user_id,"
		   AND u.dc_school_id=",p_school_id,"
		   AND DATE_FORMAT(cc.`begin_time`,'%y-%m-%d')<=DATE_FORMAT('",p_datetime,"','%y-%m-%d') AND IF(cc.end_time IS NULL,TRUE,DATE_FORMAT(cc.end_time,'%y-%m-%d')>=DATE_FORMAT('",p_datetime,"','%y-%m-%d'))");	   	
		   
		SET tmp_sql = CONCAT(tmp_sql," order by tc.c_time");
          		   
        		   
        END IF;
        
        
        
        IF p_user_type IS NOT NULL AND p_user_type<>2 THEN 
	      SET tmp_sql=CONCAT("
	        SELECT DISTINCT tc.course_id,tc.course_name,cc.begin_time,cc.end_time, CASE cc.class_type WHEN 1 THEN (SELECT class_type FROM class_info WHERE class_id=cc.class_id)
		      WHEN 2 THEN 1 END class_type,u.dc_school_id,cc.class_id,CASE cc.class_type WHEN 1 THEN (SELECT concat(class_grade,class_name) FROM class_info WHERE class_id=cc.class_id)
		      END classname
		  FROM tp_course_info tc,tp_j_course_class cc,user_info u
		 WHERE tc.course_id=cc.course_id AND u.user_id=tc.cuser_id
		 AND tc.local_status=1
		   AND u.dc_school_id=",p_school_id,"
		   and cc.class_id=",p_class_id,"
		   AND DATE_FORMAT(cc.`begin_time`,'%y-%m-%d')<=DATE_FORMAT('",p_datetime,"','%y-%m-%d') AND IF(cc.end_time IS NULL,TRUE,DATE_FORMAT(cc.end_time,'%y-%m-%d')>=DATE_FORMAT('",p_datetime,"','%y-%m-%d'))");	   
		   
		   SET tmp_sql = CONCAT(tmp_sql," order by tc.c_time");
        END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
