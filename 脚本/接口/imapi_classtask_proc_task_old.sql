DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_classtask_proc_task_old`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_classtask_proc_task_old`(p_course_id VARCHAR(20000),p_userid INT,p_classid INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_course_id IS NOT NULL THEN 
		SET tmp_sql = 'select * from(SELECT task.`COURSE_ID` courseid,task.`TASK_ID` taskid,allot.b_time starttime,task.order_idx orderidx,task.`TASK_TYPE` tasktype,ifnull(task.remote_type,0) remotetype,TIMESTAMPDIFF(SECOND,NOW(),allot.`e_time`) lefttime,(case task.task_type when 1 then (task.`criteria`)
		when 2 then (task.`criteria`)
		when 3 then (2)
		when 4 then (2)
		when 5 then (2) 
		when 6 then (task.`criteria`)
		when 7 then (2)
		when 8 then (2)
		when 9 then(2) end )FINISHSTANDARD';
		SET tmp_sql = CONCAT(tmp_sql,",(CASE task.task_type 
					WHEN 1 AND task.remote_type IS NOT NULL  THEN  resource_name
					WHEN 1 AND task.remote_type IS NULL THEN  (SELECT CONCAT(res_name,file_suffixname) FROM rs_resource_info r WHERE r.res_id=task.TASK_VALUE_ID)
					WHEN 2 THEN (SELECT topic_title FROM tp_topic_info t WHERE t.topic_id=task.TASK_VALUE_ID)
					WHEN 3 THEN (SELECT content FROM question_info q WHERE q.question_id=task.TASK_VALUE_ID)
					WHEN 4 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=task.TASK_VALUE_ID)
					WHEN 5 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=task.TASK_VALUE_ID)
					WHEN 6 THEN (SELECT res_name FROM rs_resource_info r  WHERE r.res_id=task.TASK_VALUE_ID)
					when 7 then (task.task_name)
					when 8 then (task.task_name)
					when 9 then (task.task_name)
					WHEN 10 THEN TASK_NAME
					 END
				 )taskname");
		IF p_classid IS NOT NULL THEN
			SET tmp_sql = CONCAT(tmp_sql,",(select ifnull(if(tl.e_time>now(),0,1),0) from tp_task_allot_info tl where tl.ref=allot.ref and (tl.user_type_id=",p_classid," or tl.user_type_id in (SELECT g.GROUP_ID FROM tp_group_info g WHERE g.class_id = ",p_classid,"))) isover");
			SET tmp_sql = CONCAT(tmp_sql,",(select ifnull(if(tl.b_time>now(),0,1),0) from tp_task_allot_info tl where tl.ref=allot.ref and (tl.user_type_id=",p_classid,"  or tl.user_type_id in (SELECT g.GROUP_ID FROM tp_group_info g WHERE g.class_id = ",p_classid,"))) isstart");
		END IF;
		IF p_userid IS NOT NULL THEN 
			SET tmp_sql = CONCAT(tmp_sql,",(SELECT IF(COUNT(*)>0,1,0) FROM tp_task_performance per,user_info u,tp_task_allot_info tl WHERE per.task_id=task.`TASK_ID` and per.task_id=tl.task_id and tl.b_time<per.c_time and per.user_id=u.ref and u.user_id=",p_userid,") isdone");
		END IF;
		SET tmp_sql = CONCAT(tmp_sql,",ifnull((case task.task_type when 3 then (SELECT IF(q.`question_type`=1,1,0) FROM question_info q WHERE q.`question_id`=task.task_value_id) end),0) questype");
		SET tmp_sql=CONCAT(tmp_sql," FROM tp_task_info task,tp_task_allot_info allot WHERE task.`TASK_ID`=allot.`task_id`");
		IF p_userid IS NOT NULL THEN
			SET tmp_sql = CONCAT(tmp_sql," and allot.b_time<now()");
		END IF;
		
		SET tmp_sql = CONCAT(tmp_sql," and task.`COURSE_ID` in (",p_course_id,")");
		IF p_userid IS NOT NULL THEN
			SET tmp_sql = CONCAT(tmp_sql," and (
							allot.user_type=2 and allot.user_type_id not in (SELECT g.GROUP_ID FROM tp_group_info g,tp_j_group_student gs,user_info u WHERE u.user_id=gs.user_id and u.state_id=0 and g.group_id=gs.group_id AND allot.user_type_id=gs.group_id AND allot.e_time > gs.c_time AND gs.user_id=",p_userid," and g.class_id=",p_classid,")
							)");
		END IF;
		SET tmp_sql = CONCAT(tmp_sql,"  GROUP BY task.`TASK_ID` ORDER BY task.`order_idx`) a where a.isdone=1");
	END IF;
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
END $$

DELIMITER ;
