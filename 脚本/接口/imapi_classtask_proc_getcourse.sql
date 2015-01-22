DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_classtask_proc_getcourse`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `imapi_classtask_proc_getcourse`(p_class_id INT,p_userid INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE currentyear VARCHAR(100) DEFAULT '';
	SELECT t.year INTO @cuyear FROM term_info t WHERE NOW() BETWEEN t.semester_begin_date AND t.semester_end_date;
	SET currentyear = @cuyear;
	IF p_class_id IS NOT NULL THEN 
		SET tmp_sql = 'select * from (SELECT DISTINCT tc.`COURSE_ID` courseid,cc.subject_id coursesubjectid,tc.`COURSE_NAME` coursename,cc.begin_time coursedate ,sub.`SUBJECT_NAME` coursesubject';
		SET tmp_sql = CONCAT(tmp_sql,",(SELECT COUNT(*)
						FROM tp_task_info ta LEFT JOIN tp_task_allot_info al ON ta.`TASK_ID`=al.`task_id`
						WHERE ta.`TASK_TYPE`=10
						AND al.`b_time`<NOW()
						AND al.`e_time`>NOW()
						AND ta.`COURSE_ID`=tc.course_id
						AND al.`user_type_id`=",p_class_id,") islive");
		IF p_userid IS NOT NULL THEN 
			SET tmp_sql = CONCAT(tmp_sql," FROM tp_course_info tc,tp_j_course_class cc,user_info u,class_info ci,j_class_user ju,subject_info sub 
						WHERE tc.course_id=cc.course_id  
						 AND cc.subject_id = sub.subject_id 
						 AND cc.class_id = ci.class_id	
						 AND ci.year='",currentyear,"'
						AND tc.local_status=1");
		END IF;
		IF p_userid IS NULL THEN
			SET tmp_sql = CONCAT(tmp_sql," FROM tp_course_info tc,tp_j_course_class cc,subject_info sub
						      WHERE tc.course_id=cc.course_id  
						     AND cc.subject_id = sub.subject_id
						    AND tc.local_status=1");
		END IF;
		
		SET tmp_sql = CONCAT(tmp_sql," and cc.class_id=",p_class_id);
		IF p_userid IS NOT NULL THEN 
			SET tmp_sql = CONCAT(tmp_sql," AND ju.subject_id = cc.subject_id  
						AND cc.class_id=ju.class_id 
						AND ju.user_id=u.ref 
						AND ju.relation_type='任课老师' 
						AND u.user_id=tc.cuser_id 
						AND tc.cuser_id=",p_userid);
		END IF;
		SET tmp_sql = CONCAT(tmp_sql," order by tc.c_time desc");
		SET tmp_sql = CONCAT(tmp_sql,") a");
	END IF;
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
    END$$

DELIMITER ;