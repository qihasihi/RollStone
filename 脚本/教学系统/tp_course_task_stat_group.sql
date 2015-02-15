DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `tp_course_task_stat_group`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_task_stat_group`(p_course_id BIGINT,p_subject_id INT,p_class_id INT,p_sel_type INT,p_group_id BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT ' ';
	DECLARE tmp_sum_sql VARCHAR(10000) DEFAULT ' ';
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '  s.stu_name ';
	DECLARE tmp_tbl_sql VARCHAR(10000) DEFAULT '  tp_j_group_student gs 
	INNER JOIN tp_group_info g   ON g.group_id = gs.group_id 
	INNER JOIN j_class_user cu ON cu.`CLASS_ID`=g.`CLASS_ID`
	INNER JOIN user_info u ON u.`REF`=cu.`USER_ID` AND gs.`USER_ID`=u.`USER_ID`
	INNER JOIN student_info s ON s.`USER_ID`=u.`REF`
	INNER JOIN tp_j_course_class cc ON cc.`class_id`=cu.`CLASS_ID`
	INNER JOIN term_info tm ON tm.`REF`=cc.`term_id`
	LEFT JOIN tp_task_info t ON t.`COURSE_ID`=cc.`course_id`
	LEFT JOIN tp_task_allot_info ta ON ta.`task_id`=t.`TASK_ID`  ';
	
	DECLARE v_taskid BIGINT;
	DECLARE v_taskidx INT;
	DECLARE v_tasktype INT;
	-- 定义循环试卷中试题id的变量
	DECLARE idx INT DEFAULT 0;
	DECLARE total INT DEFAULT 0;
	-- 定义游标，查询所有试卷id
 
	DECLARE done INT DEFAULT 0;  -- 游标的跳出标识
        DECLARE curPaper CURSOR FOR SELECT DISTINCT ta.order_idx,ta.`TASK_ID`,ta.task_type
				FROM tp_task_info ta,tp_task_allot_info tll,tp_course_info tc,tp_j_course_class tj,term_info tm
				WHERE tc.`COURSE_ID`=tj.`course_id`
				AND tc.`COURSE_ID`=ta.`COURSE_ID`
				AND ta.`TASK_ID`=tll.`task_id`
				AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date
				AND IF(p_sel_type=NULL,
				(tll.`user_type_id`=p_class_id OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=p_class_id)),
				CASE p_sel_type WHEN 0 THEN (tll.`user_type_id`=p_class_id AND tll.user_type=0) 
				ELSE (tll.`user_type_id`=p_class_id OR tll.user_type_id=p_group_id)END )
				AND tj.`subject_id`=p_subject_id
				AND tc.`COURSE_ID`=p_course_id
				ORDER BY ta.c_time;  
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
        
        OPEN curPaper;  
	        paper_loop: LOOP  
		        FETCH curPaper INTO v_taskidx,v_taskid,v_tasktype;
		       
			IF done=1 THEN  
				    LEAVE paper_loop;  -- 如果没有数据，跳出循环
			END IF;
			
			SET idx=idx+1;
		        SET tmp_column_sql = CONCAT(tmp_column_sql,",IFNULL(MAX(CASE t.task_id WHEN '",v_taskid,"' THEN (select count(*) from tp_task_performance tp where tp.user_id=s.user_id and tp.task_id=t.task_id and t.criteria=tp.CRETERIA_TYPE) ELSE NULL END),'--')taskflag",idx," ");
		        
		        
		        -- 任务类型  1：资源学习  2：互动交流  3： 课后作业  4:成卷测试  5：自主测试   6:微视频  7-9:移动端任务 7:图片  8：文字  9：视频
	   
	        END LOOP paper_loop;  
	CLOSE curPaper;
	SET tmp_sql = CONCAT("select ",tmp_column_sql," from ",tmp_tbl_sql," WHERE 1=1
										AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date
										AND u.state_id=0  "); 
	SET tmp_sql =CONCAT(tmp_sql," AND cc.`subject_id`=",p_subject_id);
	SET tmp_sql =CONCAT(tmp_sql," AND cc.`class_id`=",p_class_id);
	SET tmp_sql =CONCAT(tmp_sql," AND cc.`course_id`=",p_course_id);
	IF p_sel_type IS NULL THEN 
		SET tmp_sql =CONCAT(tmp_sql," AND ( ta.`user_type_id` =",p_class_id," OR ta.`user_type_id` IN (SELECT group_id FROM tp_group_info WHERE class_id=",p_class_id,"))");
	ELSEIF p_sel_type=0 THEN 
		SET tmp_sql =CONCAT(tmp_sql," AND  ta.`user_type_id` =",p_class_id," and ta.user_type=0 ");
	ELSEIF p_sel_type=2 THEN 
		SET tmp_sql =CONCAT(tmp_sql," AND  gs.group_id=",p_group_id," ");
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," GROUP BY s.stu_name ORDER BY ta.c_time,s.stu_no");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
    END$$

DELIMITER ;