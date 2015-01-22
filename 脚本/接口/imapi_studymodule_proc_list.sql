DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_studymodule_proc_list`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `imapi_studymodule_proc_list`(p_user_id INT,p_user_type INT,p_school_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(10000) DEFAULT '';
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql,"(SELECT DISTINCT u.ref,c.CLASS_ID classid,CONCAT(c.class_grade,c.CLASS_NAME) classname,c.dc_type classtype,0 isvirtual
						FROM j_class_user jc LEFT JOIN class_info c ON jc.CLASS_ID=c.CLASS_ID
					LEFT JOIN user_info u ON jc.USER_ID=u.REF
					WHERE u.USER_ID=",p_user_id," AND c.isflag=1 AND jc.relation_type<>'班主任'
					AND c.year=(SELECT YEAR FROM term_info WHERE NOW() BETWEEN semester_begin_date AND semester_end_date)) a");
		IF p_user_type <> 2 THEN
			SET tmp_sql2 = CONCAT(tmp_sql2,",((SELECT COUNT(ti.task_id) FROM tp_course_info tc LEFT JOIN tp_task_info ti ON tc.course_id=ti.course_id  LEFT JOIN tp_task_allot_info allot ON ti.task_id=allot.task_id WHERE 
			( EXISTS ( SELECT 1 FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref AND cu.relation_type='学生' AND u.user_id=",p_user_id,"  AND u.state_id=0 AND allot.user_type_id=cu.class_id AND allot.e_time > cu.c_time AND cu.class_id=a.classid)
							OR
							EXISTS (SELECT 1 FROM tp_group_info g,tp_j_group_student gs,user_info u WHERE u.user_id=gs.user_id AND u.state_id=0 AND g.group_id=gs.group_id AND allot.user_type_id=gs.group_id AND allot.e_time > gs.c_time AND gs.user_id=",p_user_id," AND g.class_id=a.classid)
							)
			AND allot.b_time<NOW() AND allot.e_time>NOW() AND tc.local_status=1)-(SELECT COUNT(ti.task_id) FROM tp_task_performance p RIGHT JOIN tp_task_allot_info allot ON p.TASK_ID=allot.task_id
						RIGHT JOIN tp_task_info ti ON allot.task_id=ti.task_id
						RIGHT JOIN tp_course_info tc ON ti.course_id=tc.course_id
						WHERE (EXISTS ( SELECT 1 FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref AND cu.relation_type='学生' AND u.user_id=",p_user_id,"  AND u.state_id=0 AND allot.user_type_id=cu.class_id AND allot.e_time > cu.c_time AND cu.class_id=a.classid)
							OR
							EXISTS (SELECT 1 FROM tp_group_info g,tp_j_group_student gs,user_info u WHERE u.user_id=gs.user_id AND u.state_id=0 AND g.group_id=gs.group_id AND allot.user_type_id=gs.group_id AND allot.e_time > gs.c_time AND gs.user_id=",p_user_id," AND g.class_id=a.classid)
							)  
						AND allot.b_time<NOW()
						AND allot.e_time>NOW()
						AND allot.b_time<p.c_time
						AND tc.local_status=1
						AND p.USER_ID=a.ref)) notifynum"
						);
		
		END IF;
		SET tmp_sql = CONCAT(" select a.*",tmp_sql2," from " ,tmp_sql);
		SET @sql1 =tmp_sql;
		PREPARE s1 FROM  @sql1;
		EXECUTE s1;
		DEALLOCATE PREPARE s1;
	END IF;
    END$$

DELIMITER ;