DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `getCourseCalendar`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `getCourseCalendar`(
					  p_user_id INT,
					  p_user_type INT,
					   p_school_id INT,
					  p_year VARCHAR(50),
				          p_month VARCHAR(50),
				          p_grade_id VARCHAR(50),
				          p_subject_id VARCHAR(50),
				          p_class_id INT,
				          p_termid VARCHAR(50),
				          OUT total INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_condition VARCHAR(20000) DEFAULT '';
	DECLARE tmp_tblname VARCHAR(20000) DEFAULT '';
	DECLARE tmp_column VARCHAR(20000) DEFAULT '';
	
	
	
	IF p_school_id IS NOT NULL AND 
	   p_year IS NOT NULL AND 
	    p_month IS NOT NULL THEN
	    
	    
	    IF p_user_type=1 THEN 	
		SET tmp_sql="SELECT e.e_day,IF(e.e_date<DATE_FORMAT(NOW(),'%y-%m-%d'),1,0)flag,(SELECT COUNT(*)
			  FROM tp_course_info tc,tp_j_course_class cc,user_info u,j_class_user cu
			 WHERE tc.course_id=cc.course_id 
			   AND cc.`class_id`=cu.`CLASS_ID`
			   AND u.ref=cu.user_id 
			   AND tc.local_status=1 ";
			   
			   IF p_termid IS NOT NULL THEN 
				SET tmp_sql=CONCAT(tmp_sql," AND cc.term_id='",p_termid,"'");
			   END IF;
			   
			   IF p_user_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND u.USER_ID=",p_user_id);
			   END IF;
			   
			   IF p_school_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND u.dc_school_id=",p_school_id);
			   END IF;
			   SET tmp_sql=CONCAT(tmp_sql," AND (DATE_FORMAT(cc.`begin_time`,'%y-%m-%d') = e.e_date  OR DATE_FORMAT(cc.`end_time`,'%y-%m-%d') = e.e_date 
			    OR (cc.begin_time <e.e_date AND IFNULL(cc.end_time,DATE_ADD(NOW(),INTERVAL 500 YEAR)) >e.e_date) )");
			    
			    
			   IF p_class_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND cu.class_id=",p_class_id);
			   END IF;
			   
			   SET tmp_sql=CONCAT(tmp_sql," ) hascourse FROM every_day e WHERE 1=1 ");
			   
			   IF p_year IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," and e.e_year= ",p_year);
			   END IF;
			   
			   IF p_month IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," and e.e_month= ",p_month);
			   END IF;
			   
	    
	    
	    ELSEIF p_user_type=2 THEN
		  SET tmp_sql="SELECT e.e_day,IF(e.e_date<DATE_FORMAT(NOW(),'%y-%m-%d'),1,0)flag,(SELECT COUNT(*)
			  FROM tp_course_info tc,tp_j_course_class cc,user_info u
			 WHERE tc.course_id=cc.course_id AND u.user_id=tc.cuser_id  AND tc.local_status=1   ";
			 
			 IF  p_user_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,"  AND tc.cuser_id=",p_user_id);
			 END IF;
			 
			 IF p_termid IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND cc.term_id='",p_termid,"'");
			 END IF;
			   
			 IF p_school_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND u.dc_school_id=",p_school_id);
			 END IF;
			  
			 IF p_grade_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND cc.grade_id=",p_grade_id);
			 END IF;
			 
			 IF p_subject_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND cc.subject_id=",p_subject_id);
			 END IF;
			 
			 SET tmp_sql=CONCAT(tmp_sql,"  AND (DATE_FORMAT(cc.`begin_time`,'%y-%m-%d') = e.e_date  OR DATE_FORMAT(cc.`end_time`,'%y-%m-%d') = e.e_date  OR (cc.begin_time <e.e_date AND IFNULL(cc.end_time,DATE_ADD(NOW(),INTERVAL 500 YEAR)) >e.e_date) )
			   ) hascourse FROM every_day e WHERE 1=1 ");
			   
			   
			 
			   
			   IF p_year IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," and e.e_year= ",p_year);
			   END IF;
			   
			   IF p_month IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," and e.e_month= ",p_month);
			   END IF;
			   
	    
	    ELSEIF p_user_type=3 THEN 
	    
		     SET tmp_sql="SELECT e.e_day,IF(e.e_date<DATE_FORMAT(NOW(),'%y-%m-%d'),1,0)flag,(SELECT COUNT(*)
			  FROM tp_course_info tc,tp_j_course_class cc,user_info u,class_info c
			 WHERE tc.course_id=cc.course_id AND u.user_id=cc.user_id and c.class_id=cc.class_id AND tc.local_status=1  ";
			 
			  
			 IF p_termid IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND cc.term_id='",p_termid,"'");
			 END IF;
			 
			 IF p_user_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,"   AND (cc.class_id IN (SELECT j.class_id FROM  j_class_user j,user_info ui WHERE ui.ref=j.user_id AND 
			    j.relation_type='°àÖ÷ÈÎ' AND ui.user_id=",p_user_id," )OR cc.user_id=",p_user_id,") ");
			 END IF;
			 
			IF p_grade_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND c.class_grade=(select grade_value from grade_info where grade_id=",p_grade_id,")");
			 END IF;
			 
			 
			 IF p_school_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," AND u.dc_school_id=",p_school_id);
			 END IF;
			 
			 SET tmp_sql=CONCAT(tmp_sql,"  AND (DATE_FORMAT(cc.`begin_time`,'%y-%m-%d') = e.e_date  OR DATE_FORMAT(cc.`end_time`,'%y-%m-%d') = e.e_date  OR (cc.begin_time <e.e_date AND IFNULL(cc.end_time,DATE_ADD(NOW(),INTERVAL 500 YEAR)) >e.e_date) )
			   ) hascourse FROM every_day e WHERE 1=1 ");
			 
			 IF p_year IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," and e.e_year= ",p_year);
			 END IF;
			   
			 IF p_month IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," and e.e_month= ",p_month);
			 END IF;		
		
	    
	    END IF;
		
	      
        END IF;
        
        SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
	SET total=0;
    END$$

DELIMITER ;