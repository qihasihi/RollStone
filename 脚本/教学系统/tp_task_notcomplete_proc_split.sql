DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_notcomplete_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_notcomplete_proc_split`(
				          p_task_id BIGINT,
				          p_course_id BIGINT,
				          p_user_id INT,
				          p_flag INT,
				          p_class_id INT,
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_tbl VARCHAR(20000) DEFAULT ' select null user_id,null taskobjname,null class_id from dual ';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_task_performance u'; 
	DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET sumCount =0;
	
	BEGIN
	
	DECLARE tmp_user_type INT;
	DECLARE tmp_user_type_id BIGINT;
	DECLARE cursor_done INT DEFAULT 0;
	DECLARE tmp_cursor CURSOR FOR SELECT user_type,user_type_id FROM tp_task_allot_info WHERE task_id=p_task_id ORDER BY user_type;
	DECLARE class_cursor CURSOR FOR SELECT user_type,user_type_id FROM tp_task_allot_info WHERE task_id=p_task_id  AND (user_type_id=p_class_id OR (user_type_id IN (SELECT group_id FROM tp_group_info  WHERE class_id=p_class_id) AND user_type=2)) ORDER BY user_type;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done=1;
	
	IF p_class_id IS NULL THEN 
	
		OPEN tmp_cursor;
			tmp_taskloop:LOOP FETCH tmp_cursor INTO tmp_user_type,tmp_user_type_id;
			
				IF cursor_done=1 THEN
					LEAVE tmp_taskloop;
				END IF;
				
			       IF tmp_user_type =0 THEN
					SET tmp_tbl=CONCAT(tmp_tbl," union all SELECT u.user_id,concat(c.class_grade,c.class_name)taskobjname,c.class_id FROM j_class_user cu,class_info c,user_info u WHERE cu.class_id=",tmp_user_type_id," and cu.relation_type='学生'  and cu.class_id=c.class_id and cu.user_id = u.ref");
					IF p_user_id IS NOT NULL THEN
						SET tmp_tbl=CONCAT(tmp_tbl," AND EXISTS (SELECT 1 FROM j_class_user j,user_info ui WHERE j.user_id=ui.ref and j.class_id=cu.class_id AND ui.user_id=",p_user_id,") ");
					END IF;
					 
			       END IF;
										
			       IF tmp_user_type =1 THEN
					SET tmp_tbl=CONCAT(tmp_tbl," union all SELECT vs.user_id,virtual_class_name taskobjname,tv.virtual_class_id class_id FROM tp_j_virtual_class_student vs,tp_virtual_class_info tv WHERE tv.virtual_class_id=vs.virtual_class_id and vs.VIRTUAL_CLASS_ID=",tmp_user_type_id);
					IF p_user_id IS NOT NULL THEN
						SET tmp_tbl=CONCAT(tmp_tbl," AND EXISTS (SELECT 1 FROM tp_j_virtual_class_student s WHERE s.VIRTUAL_CLASS_ID=vs.VIRTUAL_CLASS_ID AND s.user_id=",p_user_id,")");
					END IF;
					
			       END IF;
			       
			       IF tmp_user_type =2 THEN
					SET tmp_tbl=CONCAT(tmp_tbl," union all SELECT ts.user_id,concat(c.class_grade,c.class_name,g.group_name)taskobjname,g.group_id class_id FROM tp_j_group_student ts,tp_group_info g,class_info c WHERE ts.group_id=g.group_id and c.class_id=g.class_id and ts.group_id=",tmp_user_type_id);
					IF p_user_id IS NOT NULL THEN
						SET tmp_tbl=CONCAT(tmp_tbl," and exists(select 1 from tp_j_group_student s where s.group_id=ts.group_id and s.user_id=",p_user_id,")");
					END IF;
			       END IF;
			  END LOOP tmp_taskloop;
		CLOSE tmp_cursor;
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		OPEN class_cursor;
			class_taskloop:LOOP FETCH class_cursor INTO tmp_user_type,tmp_user_type_id;
			
				IF cursor_done=1 THEN
					LEAVE class_taskloop;
				END IF;
				
			       IF tmp_user_type =0 THEN
					SET tmp_tbl=CONCAT(tmp_tbl," union all SELECT u.user_id,concat(c.class_grade,c.class_name)taskobjname,c.class_id FROM j_class_user cu,class_info c,user_info u WHERE cu.class_id=",tmp_user_type_id," and cu.relation_type='学生'  and cu.class_id=c.class_id and cu.user_id = u.ref");
					IF p_user_id IS NOT NULL THEN
						SET tmp_tbl=CONCAT(tmp_tbl," AND EXISTS (SELECT 1 FROM j_class_user j,user_info ui WHERE j.user_id=ui.ref and j.class_id=cu.class_id AND ui.user_id=",p_user_id,") ");
					END IF;
					 
			       END IF;
										
			       IF tmp_user_type =1 THEN
					SET tmp_tbl=CONCAT(tmp_tbl," union all SELECT vs.user_id,virtual_class_name taskobjname,tv.virtual_class_id class_id FROM tp_j_virtual_class_student vs,tp_virtual_class_info tv WHERE tv.virtual_class_id=vs.virtual_class_id and vs.VIRTUAL_CLASS_ID=",tmp_user_type_id);
					IF p_user_id IS NOT NULL THEN
						SET tmp_tbl=CONCAT(tmp_tbl," AND EXISTS (SELECT 1 FROM tp_j_virtual_class_student s WHERE s.VIRTUAL_CLASS_ID=vs.VIRTUAL_CLASS_ID AND s.user_id=",p_user_id,")");
					END IF;
					
			       END IF;
			       
			       IF tmp_user_type =2 THEN
					SET tmp_tbl=CONCAT(tmp_tbl," union all SELECT ts.user_id,concat(c.class_grade,c.class_name,g.group_name)taskobjname,g.group_id class_id FROM tp_j_group_student ts,tp_group_info g,class_info c WHERE ts.group_id=g.group_id and c.class_id=g.class_id and ts.group_id=",tmp_user_type_id);
					IF p_user_id IS NOT NULL THEN
						SET tmp_tbl=CONCAT(tmp_tbl," and exists(select 1 from tp_j_group_student s where s.group_id=ts.group_id and s.user_id=",p_user_id,")");
					END IF;
			       END IF;
			  END LOOP class_taskloop;
		CLOSE class_cursor;
	END IF;
	
	END;
	
	
	SET tmp_sql=CONCAT(tmp_sql,"select u.user_id,u.ett_user_id,a.taskobjname,a.class_id,ifnull(ifnull(t.teacher_name,s.stu_name),u.user_name)real_name from user_info u inner join (",tmp_tbl,
	")a  on u.user_id=a.user_id left join teacher_info t on u.ref=t.user_id 
	left join student_info s on u.ref=s.user_id where 1=1 and u.state_id=0");
	
	IF p_flag IS NOT NULL THEN 
		SET tmp_sql=CONCAT(tmp_sql," and  NOT EXISTS (SELECT user_id FROM tp_task_performance tp WHERE tp.user_id=u.ref and tp.task_id=",p_task_id,")");
	END IF;
	
	
	
	
	
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
