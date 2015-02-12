DELIMITER $$

DROP PROCEDURE IF EXISTS `admin_performance_proc_stu`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `admin_performance_proc_stu`(p_grade_id INT,p_subject_id INT,p_class_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT ' ';
	DECLARE tmp_sum_sql VARCHAR(10000) DEFAULT ' ';
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '  stu.stu_name ';
	DECLARE tmp_tbl_sql VARCHAR(10000) DEFAULT '  tp_task_info t,tp_j_course_class cc,tp_task_allot_info ta,term_info tm,j_class_user cu,student_info stu,user_info u  ';
	-- 定义paperid
	DECLARE v_paperid BIGINT;
	DECLARE v_taskid BIGINT;
	-- 定义循环试卷中试题id的变量
	DECLARE idx INT DEFAULT 0;
	DECLARE total INT DEFAULT 0;
	-- 定义游标，查询所有试卷id
 
	DECLARE done INT DEFAULT 0;  -- 游标的跳出标识
        DECLARE curPaper CURSOR FOR SELECT DISTINCT ta.`TASK_ID`
				FROM tp_task_info ta,tp_task_allot_info tll,tp_course_info tc,tp_j_course_class tj,term_info tm
				WHERE tc.`COURSE_ID`=tj.`course_id`
				AND tc.`COURSE_ID`=ta.`COURSE_ID`
				AND ta.`TASK_ID`=tll.`task_id`
				AND (ta.`TASK_TYPE`=4 OR ta.`TASK_TYPE`=5)
				AND tll.e_time<NOW()
				AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date
				AND (tll.`user_type_id`=p_class_id OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=p_class_id))
				AND tj.class_id=p_class_id
				AND tj.`grade_id`=p_grade_id
				AND tj.`subject_id`=p_subject_id ORDER BY ta.task_id;  
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
        
        OPEN curPaper;  
	        paper_loop: LOOP  
		        FETCH curPaper INTO v_taskid;
		       
			IF done=1 THEN  
				    LEAVE paper_loop;  -- 如果没有数据，跳出循环
			END IF;
			
			SET idx=idx+1;
		        SET tmp_column_sql = CONCAT(tmp_column_sql,",IFNULL(MAX(CASE t.task_id WHEN '",v_taskid,"' THEN (SELECT score FROM stu_paper_logs a WHERE a.`task_id`=t.task_id AND a.`user_id`=u.user_id) ELSE NULL END ),'--') col",idx,"");
		        SET tmp_sum_sql=CONCAT(tmp_sum_sql," +col",idx,"");
		        -- 任务类型  1：资源学习  2：互动交流  3： 课后作业  4:成卷测试  5：自主测试   6:微视频  7-9:移动端任务 7:图片  8：文字  9：视频
	   
	        END LOOP paper_loop;  
	CLOSE curPaper;
	SET tmp_sql = CONCAT("select ",tmp_column_sql," from ",tmp_tbl_sql," WHERE t.`COURSE_ID`=cc.course_id
										AND t.task_id=ta.`task_id` 
										AND tm.ref=cc.`term_id` 
										AND u.ref=cu.user_id
										AND cu.class_id=cc.class_id
										AND stu.user_id=cu.user_id
										AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date
										AND ta.`e_time`<NOW()
										AND t.`TASK_TYPE` IN (4,5)  "); 
	SET tmp_sql =CONCAT(tmp_sql," AND cc.`grade_id`=",p_grade_id);
	SET tmp_sql =CONCAT(tmp_sql," AND cc.`subject_id`=",p_subject_id);
	SET tmp_sql =CONCAT(tmp_sql," AND cc.`class_id`=",p_class_id);
	SET tmp_sql =CONCAT(tmp_sql," AND ( ta.`user_type_id` =",p_class_id," OR ta.`user_type_id` IN (SELECT group_id FROM tp_group_info WHERE class_id=",p_class_id,"))");
	SET tmp_sql = CONCAT(tmp_sql," GROUP BY stu.stu_name ORDER BY ta.task_id");
	IF LENGTH(tmp_sum_sql)>0 THEN
		SET tmp_sql=CONCAT("select t.*,sum(",tmp_sum_sql,")total from (",tmp_sql,")t group by stu_name order by stu_name");
	END IF;
	IF idx=0 THEN
		SET tmp_sql='select null from dual';
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
    END$$

DELIMITER ;