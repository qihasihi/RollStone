DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_task_remind_proc_detail`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `imapi_task_remind_proc_detail`(p_task_id BIGINT,p_userid INT,p_classid INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_task_id IS NOT NULL THEN 
		SET tmp_sql = 'select * from(SELECT task.`TASK_ID` taskid,task.im_task_content taskcontent,task.IM_TASK_ATTACH attachs,task.IM_TASK_ATTACHTYPE attachtype,task.IM_TASK_ANALYSIS taskanalysis,task.order_idx orderidx,task.course_id courseid,task.`TASK_TYPE` tasktype,ifnull(task.remote_type,0) remotetype,ifnull(TIMESTAMPDIFF(SECOND,NOW(),allot.`e_time`),0) lefttime,(case task.task_type when 1 then (task.`criteria`)
		when 2 then (task.`criteria`)
		when 3 then (2)
		when 4 then (2)
		when 5 then (2) 
		when 6 then (task.`criteria`)
		when 7 then (2)
		when 8 then (2)
		when 9 then(2)
		when 10 then (task.`criteria`) end )FINISHSTANDARD
				,(SELECT COUNT(*) FROM tp_task_performance per WHERE per.task_id=task.`TASK_ID`) donum,
				SUM((CASE allot.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc WHERE jc.class_id=allot.user_type_id AND jc.relation_type="学生")
				  WHEN 1 THEN (SELECT COUNT(*) FROM tp_j_virtual_class_student tc WHERE tc.virtual_class_id=allot.user_type_id)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts WHERE ts.group_id=allot.user_type_id)
							END)) totalnum,(CASE task.task_type 
					WHEN 1 AND task.remote_type IS NOT NULL  THEN  resource_name
					WHEN 1 AND task.remote_type IS NULL THEN  (SELECT CONCAT(res_name,file_suffixname) FROM rs_resource_info r WHERE r.res_id=task.TASK_VALUE_ID)
					WHEN 2 THEN (SELECT topic_title FROM tp_topic_info t WHERE t.topic_id=task.TASK_VALUE_ID)
					WHEN 3 THEN (SELECT content FROM question_info q WHERE q.question_id=task.TASK_VALUE_ID)
					WHEN 4 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=task.TASK_VALUE_ID)
					WHEN 5 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=task.TASK_VALUE_ID)
					WHEN 6 THEN (SELECT res_name FROM rs_resource_info r  WHERE r.res_id=task.TASK_VALUE_ID)
					when 7 then task.task_name
					when 8 then task.task_name
					when 9 then task.task_name
					WHEN 10 THEN TASK_NAME
					 END
				 )taskname';
		IF p_classid IS NOT NULL THEN
			SET tmp_sql = CONCAT(tmp_sql,",ifnull((select ifnull(if(tl.e_time>now(),0,1),0) from tp_task_allot_info tl where tl.ref=allot.ref and (tl.user_type_id=",p_classid," or tl.user_type_id in (SELECT g.GROUP_ID FROM tp_group_info g WHERE g.class_id = ",p_classid,"))),0) isover");
		END IF;
		IF p_userid IS NOT NULL THEN 
			SET tmp_sql = CONCAT(tmp_sql,",(SELECT IF(COUNT(*)>0,1,0) FROM tp_task_performance per,user_info u,tp_task_allot_info tl WHERE per.task_id=task.`TASK_ID` and per.task_id=tl.task_id and tl.b_time<per.c_time and per.user_id=u.ref and u.user_id=",p_userid,") isdone");
		END IF;
		SET tmp_sql = CONCAT(tmp_sql,",ifnull((case task.task_type when 3 then (SELECT IF(q.`question_type`=1,1,0) FROM question_info q WHERE q.`question_id`=task.task_value_id) end),0) questype");
		SET tmp_sql=CONCAT(tmp_sql," FROM tp_task_info task,tp_task_allot_info allot WHERE task.`TASK_ID`=allot.`task_id`");
		IF p_userid IS NOT NULL THEN
			SET tmp_sql = CONCAT(tmp_sql," and allot.b_time<now()");
		END IF;
		#SET tmp_sql = CONCAT(tmp_sql," and allot.user_type_id=",p_classid);
		SET tmp_sql = CONCAT(tmp_sql," and task.task_id=",p_task_id);
		IF p_userid IS NOT NULL THEN
			SET tmp_sql = CONCAT(tmp_sql," and (allot.user_type_id in ( SELECT cu.CLASS_ID FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref AND cu.relation_type='学生' AND u.user_id=",p_userid,"  and u.state_id=0 AND allot.user_type_id=cu.class_id AND allot.e_time > cu.c_time )
							OR
							allot.user_type_id in (SELECT vc.VIRTUAL_CLASS_ID FROM tp_virtual_class_info vc,tp_j_virtual_class_student vcs,user_info u WHERE u.user_id=vcs.user_id and u.state_id=0 and vc.virtual_class_id=vcs.virtual_class_id  AND vcs.user_id=",p_userid," AND allot.user_type_id=vc.virtual_class_id AND allot.e_time > vcs.c_time)
							OR
							allot.user_type_id in (SELECT g.GROUP_ID FROM tp_group_info g,tp_j_group_student gs,user_info u WHERE u.user_id=gs.user_id and u.state_id=0 and g.group_id=gs.group_id AND allot.user_type_id=gs.group_id AND allot.e_time > gs.c_time AND gs.user_id=",p_userid,")
							)");
		END IF;
		SET tmp_sql = CONCAT(tmp_sql," ) a");
	END IF;
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
    END$$

DELIMITER ;