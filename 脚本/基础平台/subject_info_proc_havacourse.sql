DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `subject_info_proc_havacourse`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `subject_info_proc_havacourse`(p_termid VARCHAR(60),
							p_userref varchar(60),
							p_userid int
							)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	if p_userid is not null and p_termid is not null then
		set tmp_sql = concat(tmp_sql,"SELECT * FROM subject_info s right join (");
		SET tmp_sql = CONCAT(tmp_sql,"SELECT DISTINCT(a.subject_id) FROM (");
		SET tmp_sql = CONCAT(tmp_sql," SELECT tc.subject_id  FROM tp_j_course_class tc,j_class_user ju ,tp_course_info tci WHERE");
		SET tmp_sql = CONCAT(tmp_sql," ju.CLASS_ID=tc.class_id AND ju.USER_ID='",p_userref,"' AND tci.course_id=tc.course_id AND tci.local_status=1");
		SET tmp_sql = CONCAT(tmp_sql," AND term_id= '",p_termid,"' ");
		SET tmp_sql = CONCAT(tmp_sql," UNION");
		SET tmp_sql = CONCAT(tmp_sql," SELECT tc.subject_id  FROM tp_j_course_class tc,tp_j_virtual_class_student ju ,tp_course_info tci WHERE ");
		SET tmp_sql = CONCAT(tmp_sql," ju.virtual_CLASS_ID=tc.class_id AND ju.USER_ID='",p_userid,"' AND tci.course_id=tc.course_id AND tci.local_status=1");
		SET tmp_sql = CONCAT(tmp_sql," AND term_id= '",p_termid,"' ) a ORDER BY a.subject_id ) b on s.subject_id=b.subject_id");
		
		SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;   
		DEALLOCATE PREPARE s1;  
	end if;
END $$

DELIMITER ;
