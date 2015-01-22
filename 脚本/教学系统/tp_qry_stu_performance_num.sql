DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_performance_num`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_performance_num`(p_taskid BIGINT,p_classid BIGINT,p_classtype int)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql='SELECT (CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc left join user_info u on jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id and jc.relation_type="学生" and u.state_id=0)
				  WHEN 1 THEN (SELECT COUNT(*) FROM tp_j_virtual_class_student tc WHERE tc.virtual_class_id=ta.user_type_id)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts left join user_info u on ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id and u.state_id=0)
			END) totalnum, ( CASE ta.user_type WHEN 0 THEN (SELECT COUNT(DISTINCT p.ref) FROM 
 tp_task_performance p,tp_task_allot_info tta,j_class_user cu,user_info ui WHERE ui.ref=cu.user_id AND ui.state_id=0 AND p.user_id=ui.ref AND  cu.relation_type=''学生'' AND cu.class_id=tta.user_type_id AND tta.user_type_id=ta.`user_type_id`  AND tta.task_id=p.task_id AND p.task_id=u.`TASK_ID` AND p.creteria_type=u.criteria) 
   WHEN 2 THEN (
	SELECT COUNT(DISTINCT p.ref) FROM tp_task_performance p,tp_task_allot_info tta ,tp_j_group_student ts,user_info ui WHERE ui.user_id=ts.user_id AND p.user_id=ui.ref AND  ts.group_id=tta.user_type_id 
	
	AND tta.task_id=p.task_id AND p.task_id=u.`TASK_ID` AND  p.creteria_type=u.criteria AND tta.user_type_id=ta.`user_type_id`
	
   ) END 
 ) finishnum,
			(CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp WHERE tp.IS_RIGHT=1 AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc left join user_info u on jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id and u.state_id=0))
					   WHEN 1 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp WHERE tp.IS_RIGHT=1 AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_virtual_class_student tc ON u.user_id=tc.user_id WHERE tc.virtual_class_id=ta.user_type_id))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp WHERE tp.IS_RIGHT=1 AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id and u.state_id=0))
			END) rightnum
		FROM tp_task_allot_info ta,tp_task_info u
		WHERE 1=1 and ta.task_id=u.task_id ';
		
	SET tmp_sql=CONCAT(tmp_sql," AND ta.task_id= ",p_taskid);
	if p_classid is not null and p_classid<>0 then
		set tmp_sql=concat(tmp_sql," and ta.user_type_id=",p_classid);
	end if;
	 
     
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
