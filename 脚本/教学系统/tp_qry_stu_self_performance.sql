DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_self_performance`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_self_performance`(p_user_id INT,
							p_course_id BIGINT,
							p_group INT,
							p_termid VARCHAR(50),
							p_subjectid INT)
BEGIN
	DECLARE tmp_sql VARCHAR(30000) DEFAULT '';
	SET tmp_sql=CONCAT("SELECT DISTINCT t.task_value_id,t.criteria,tc.COURSE_ID,tc.COURSE_NAME,ta.task_id,t.TASK_TYPE,t.resource_type,t.remote_type,min(ifnull(tr.c_time,tf.c_time))c_time,ta.b_time,ta.e_time,");
	SET tmp_sql=CONCAT(tmp_sql," CASE t.task_type WHEN 2 THEN (SELECT GROUP_CONCAT(theme.theme_title) FROM tp_topic_theme_info theme WHERE theme.topic_id=t.task_value_id and theme.c_time>ta.b_time and theme.c_user_id=",p_user_id,") ");
	SET tmp_sql= CONCAT(tmp_sql," WHEN 1 THEN (SELECT tp.answer_content FROM tp_ques_answer_record tp left join user_info u on tp.user_id=u.ref WHERE tp.ques_id=0 AND tp.ref=tr.ref and u.user_id=",p_user_id,")");
	SET tmp_sql= CONCAT(tmp_sql," ELSE  (SELECT GROUP_CONCAT(tp.ANSWER_CONTENT) FROM tp_ques_answer_record tp left join user_info u on tp.user_id=u.ref WHERE tp.task_id=tr.task_id and u.user_id=",p_user_id,") END answercontent,");
	SET tmp_sql = CONCAT(tmp_sql," (select count(*) from tp_task_performance t where t.task_id=ta.task_id and t.user_id=(select ref from user_info where user_id=",p_user_id,")) status,");
	SET tmp_sql = CONCAT(tmp_sql," (SELECT is_right FROM tp_task_performance t WHERE t.task_id=ta.task_id AND t.user_id=(SELECT ref FROM user_info WHERE user_id=",p_user_id,")) is_right");
	SET tmp_sql = CONCAT(tmp_sql," from tp_task_allot_info ta INNER JOIN tp_task_info t ON ta.task_id=t.task_id");
	SET tmp_sql = CONCAT(tmp_sql," INNER JOIN tp_course_info tc ON t.course_id=tc.course_id ");
	SET tmp_sql = CONCAT(tmp_sql," INNER JOIN tp_j_course_class tcls ON tcls.course_id=tc.course_id");
	SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN tp_task_performance tf ON tf.TASK_ID=ta.task_id");
	SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN tp_ques_answer_record tr ON tr.TASK_ID=ta.task_id");
	SET tmp_sql = CONCAT(tmp_sql," WHERE tc.local_status=1 AND t.status=1 and tcls.class_id in (SELECT DISTINCT jj.class_Id  FROM j_class_user jj ,
		(SELECT jc.class_id FROM j_class_user jc LEFT JOIN class_info c ON jc.class_id=c.class_id
		 WHERE jc.user_id =(SELECT ref FROM user_info WHERE user_id=",p_user_id,")
		 AND c.year=(SELECT YEAR FROM term_info WHERE ref='",p_termid,"')) bb
		 WHERE  jj.subject_id = ",p_subjectid," AND jj.class_id=bb.class_id AND jj.relation_type='任课老师') AND ta.user_type_id IN (SELECT NULL class_id FROM DUAL");	
	SET tmp_sql=CONCAT(tmp_sql," UNION ALL SELECT c.class_id FROM j_class_user cu,class_info c,user_info u WHERE cu.class_id=ta.user_type_id AND u.user_id=",p_user_id," AND cu.relation_type='学生' AND cu.class_id=c.class_id AND cu.user_id = u.ref");
	SET tmp_sql=CONCAT(tmp_sql," UNION ALL SELECT g.group_id class_id FROM tp_j_group_student ts,tp_group_info g,class_info c WHERE ts.c_time<tf.c_time and ts.group_id=g.group_id AND c.class_id=g.class_id AND ts.group_id=ta.user_type_id AND ts.user_id=",p_user_id,")");
	
	IF p_termid IS NOT NULL AND p_subjectid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," and tcls.term_id='",p_termid,"' and tcls.subject_id=",p_subjectid);
	END IF;
	
	IF p_course_id IS NOT NULL AND p_course_id<>0 THEN
		SET tmp_sql = CONCAT(tmp_sql," AND tc.course_id=",p_course_id);
	END IF;
	
	IF p_group IS  NULL OR p_group<>0 THEN
		SET tmp_sql = CONCAT(tmp_sql," GROUP BY t.task_id");
	ELSE
		SET tmp_sql = CONCAT(tmp_sql," GROUP BY tc.course_name");
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," ORDER BY tc.course_name,t.order_idx");
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
