DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_performance_num2`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_performance_num2`(p_taskid BIGINT,p_classid BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql=CONCAT(tmp_sql,'SELECT  COUNT(tj.user_id) totalnum,(SELECT COUNT(DISTINCT p.ref) FROM tp_task_performance p,tp_task_allot_info tta ,tp_group_info g,tp_j_group_student ts,user_info ui WHERE ui.user_id=ts.user_id AND p.user_id=ui.ref AND  ts.group_id=tta.user_type_id 	
	AND tta.task_id=p.task_id AND p.task_id=u.`TASK_ID` AND  p.creteria_type=u.criteria AND tta.user_type_id=ta.`user_type_id` and g.class_id=',p_classid,' and g.group_id=ts.group_id) finishnum,
			(SELECT COUNT(tf.USER_ID) FROM tp_task_performance tf WHERE tf.IS_RIGHT=1 AND tf.TASK_ID=ta.task_id
			and (tf.user_id in(select jc.user_id from j_class_user jc where jc.class_id=',p_classid,') 
								or tf.user_id=(select ref from user_info where user_id in(select jv.user_id from tp_j_virtual_class_student jv where jv.virtual_class_id=',p_classid,')))) rightnum
		FROM tp_task_allot_info ta inner join tp_task_info u on ta.task_id=u.task_id
		INNER JOIN tp_group_info ts ON ta.user_type_id=ts.group_id 
		INNER JOIN tp_j_group_student tj ON ts.group_id=tj.group_id
		WHERE 1=1 ');
		/*AND ta.course_id=-1390705034930';*/
	SET tmp_sql=CONCAT(tmp_sql," AND ta.task_id= ",p_taskid);
	SET tmp_sql=CONCAT(tmp_sql," and ts.class_id=",p_classid);
	 
     
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
