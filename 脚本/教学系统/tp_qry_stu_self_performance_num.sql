DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_self_performance_num`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_self_performance_num`(p_user_id INT,
							p_course_id varchar(3000),
							p_group INT,
							p_termid VARCHAR(50),
							p_subjectid INT)
BEGIN
	DECLARE tmp_sql VARCHAR(30000) DEFAULT '';
	set tmp_sql=concat(tmp_sql," SELECT a.course_id,a.course_name,COUNT(a.task_id) num FROM(SELECT DISTINCT tc.COURSE_ID,tc.COURSE_NAME,ta.task_id,");
	set tmp_sql=concat(tmp_sql," (SELECT COUNT(*) FROM tp_task_performance t WHERE t.task_id=ta.task_id AND t.user_id=(SELECT ref FROM user_info WHERE user_id=",p_user_id,")) STATUS");
	set tmp_sql=concat(tmp_sql," FROM tp_task_allot_info ta	");
	set tmp_sql=concat(tmp_sql,"  INNER JOIN tp_task_info t ON ta.task_id=t.task_id");
	set tmp_sql=concat(tmp_sql," INNER JOIN tp_course_info tc ON t.course_id=tc.course_id ");
	set tmp_sql=concat(tmp_sql," INNER JOIN tp_j_course_class tcls ON tcls.course_id=tc.course_id");
	set tmp_sql=concat(tmp_sql," LEFT JOIN tp_task_performance tf ON tf.TASK_ID=ta.task_id");
	set tmp_sql=concat(tmp_sql," LEFT JOIN tp_ques_answer_record tr ON tr.TASK_ID=ta.task_id");
	set tmp_sql=concat(tmp_sql," WHERE ta.user_type_id IN (SELECT NULL class_id FROM DUAL");
	set tmp_sql=concat(tmp_sql," UNION ALL SELECT c.class_id FROM j_class_user cu,class_info c,user_info u WHERE cu.class_id=ta.user_type_id AND u.user_id=",p_user_id," AND cu.relation_type='Ñ§Éú' AND cu.class_id=c.class_id AND cu.user_id = u.ref ");
	set tmp_sql=concat(tmp_sql,"  UNION ALL SELECT tv.virtual_class_id class_id FROM tp_j_virtual_class_student vs,tp_virtual_class_info tv WHERE tv.virtual_class_id=vs.virtual_class_id AND vs.VIRTUAL_CLASS_ID=ta.user_type_id AND vs.user_id=",p_user_id);
	set tmp_sql=concat(tmp_sql,"   UNION ALL SELECT g.group_id class_id FROM tp_j_group_student ts,tp_group_info g,class_info c WHERE ts.group_id=g.group_id AND c.class_id=g.class_id AND ts.group_id=ta.user_type_id AND ts.user_id=",p_user_id,")");
	set tmp_sql=concat(tmp_sql," AND tcls.term_id='",p_termid,"'");
	set tmp_sql=concat(tmp_sql," AND tcls.subject_id=",p_subjectid," and t.status=1 AND ta.b_time<now() AND ta.e_time>NOW()");
	set tmp_sql=concat(tmp_sql," )a");
	set tmp_sql=concat(tmp_sql," WHERE a.status=0");
	set tmp_sql=concat(tmp_sql,"  AND a.course_id IN(",p_course_id,")");	
	SET tmp_sql=CONCAT(tmp_sql," GROUP BY a.course_name");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
