DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `admin_performance_proc_stu`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `admin_performance_proc_stu`(p_grade_id INT,p_subject_id INT,p_class_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT 'stu.`STU_NAME` stuname,SUM(sp.`score`) totalscore';
	DECLARE tmp_tbl_sql VARCHAR(10000) DEFAULT 'user_info u,j_class_user ju,student_info stu,tp_task_info ta,tp_task_allot_info tll,tp_task_performance per,stu_paper_logs sp,tp_course_info tc,tp_j_course_class tj,term_info tm';
	-- 定义paperid
	DECLARE v_paperid BIGINT;
	-- 定义循环试卷中试题id的变量
	DECLARE idx INT DEFAULT 0;
	DECLARE total INT DEFAULT 0;
	-- 定义游标，查询所有试卷id
 
	DECLARE done INT DEFAULT 0;  -- 游标的跳出标识
        DECLARE curPaper CURSOR FOR SELECT ta.`TASK_VALUE_ID`
						FROM tp_task_info ta,tp_task_allot_info tll,tp_task_performance per,tp_course_info tc,tp_j_course_class tj,term_info tm
						WHERE tc.`COURSE_ID`=tj.`course_id`
						AND tc.`COURSE_ID`=ta.`COURSE_ID`
						AND ta.`TASK_ID`=tll.`task_id`
						AND ta.task_id=per.task_id
						AND (ta.`TASK_TYPE`=4 OR ta.`TASK_TYPE`=5)
						AND tll.e_time<NOW()
						AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date
						AND per.c_time BETWEEN tm.semester_begin_date AND tm.semester_end_date
						AND (tll.`user_type_id`=p_class_id OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=p_class_id))
						AND tj.`grade_id`=p_grade_id
						AND tj.`subject_id`=p_subject_id
						GROUP BY tc.course_id;  
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
        
        OPEN curPaper;  
	        paper_loop: LOOP  
		        FETCH curPaper INTO v_paperid;
		       
			IF done=1 THEN  
				    LEAVE paper_loop;  -- 如果没有数据，跳出循环
			END IF;
			
			SET idx=idx+1;
		        SET tmp_column_sql = CONCAT(tmp_column_sql,",ifnull((SELECT score FROM stu_paper_logs WHERE paper_id=",v_paperid," AND user_id=u.`USER_ID` AND task_id=ta.`TASK_ID`),0) 'col",idx,"'");
		        
		        -- 任务类型  1：资源学习  2：互动交流  3： 课后作业  4:成卷测试  5：自主测试   6:微视频  7-9:移动端任务 7:图片  8：文字  9：视频
	   
	        END LOOP paper_loop;  
	CLOSE curPaper;
	SET tmp_sql = CONCAT("select ",tmp_column_sql," from ",tmp_tbl_sql," WHERE tc.`COURSE_ID`=tj.`course_id`
										AND tc.`COURSE_ID`=ta.`COURSE_ID`
										AND tj.`class_id`=ju.`CLASS_ID`
										AND u.ref=ju.`USER_ID`
										AND stu.`USER_ID`=u.`REF`
										AND ta.`TASK_ID`=tll.`task_id`
										AND ta.task_id=per.task_id
										AND (ta.`TASK_TYPE`=4 OR ta.`TASK_TYPE`=5)
										AND tll.e_time<NOW()
										AND NOW()  BETWEEN tm.semester_begin_date AND tm.semester_end_date
										AND per.c_time BETWEEN tm.semester_begin_date AND tm.semester_end_date
										AND ta.`TASK_ID`=sp.`task_id`
										AND sp.`user_id`=u.`USER_ID`
										AND sp.`is_marking`=0
										AND (ju.`CLASS_ID`=tll.`user_type_id` OR tll.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=ju.`CLASS_ID`))"); 
	SET tmp_sql =CONCAT(tmp_sql," AND tj.`grade_id`=",p_grade_id);
	SET tmp_sql =CONCAT(tmp_sql," AND tj.`subject_id`=",p_subject_id);
	SET tmp_sql =CONCAT(tmp_sql," AND ju.`CLASS_ID`=",p_class_id);
	SET tmp_sql = CONCAT(tmp_sql," group by u.user_id");
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
    END$$

DELIMITER ;