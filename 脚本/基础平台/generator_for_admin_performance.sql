DELIMITER $$

USE `school`$$

DROP PROCEDURE IF EXISTS `generator_for_admin_performance`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `generator_for_admin_performance`()
BEGIN
	DECLARE tmp_sql VARCHAR(4000) DEFAULT '';
	DECLARE tmp_sql_columns VARCHAR(2000) DEFAULT '';
	DECLARE tmp_sql_values VARCHAR(2000) DEFAULT '';
	#定义统计表的每一列变量，用来接收查询的数据
	DECLARE v_course_id BIGINT;-- 专题id
	DECLARE v_course_name VARCHAR(1000);-- 专题名称
	DECLARE v_class_id INT;-- 班级id
	DECLARE v_grade_id INT;-- 年级
	DECLARE v_subject_id INT;-- 学科
	DECLARE v_course_time DATETIME;-- 专题开始时间
	DECLARE v_task_num INT;-- 任务数量
	DECLARE v_end_task_num INT;-- 结束任务数量
	DECLARE v_complete_rate VARCHAR(100);-- 任务完成率
	DECLARE v_evaluation VARCHAR(100);-- 专题评价平均分
	DECLARE v_resource_task VARCHAR(500);-- 资源学习类任务
	DECLARE v_interactive_task VARCHAR(500);-- 互动交流类任务
	DECLARE v_micro_task VARCHAR(500);-- 微视频任务
	DECLARE v_coilingtest_task VARCHAR(500);-- 成卷测试类任务
	DECLARE v_selftest_task VARCHAR(500);-- 自主测试类任务
	DECLARE v_live_task VARCHAR(500);-- 直播课类任务
	DECLARE v_ques_task VARCHAR(500);-- 试题类任务
	DECLARE v_general_task VARCHAR(500);-- 一般任务
	
	 -- 定义游标，查询所有班级，所有专题
 
	DECLARE done INT DEFAULT 0;  -- 游标的跳出标识
        DECLARE curClassCourse CURSOR FOR SELECT c.class_id,tc.course_id,tc.course_name,tj.grade_id,tj.subject_id,tj.begin_time
						FROM class_info c,tp_j_course_class tj,tp_course_info tc
						WHERE c.class_id = tj.class_id
						AND tj.course_id = tc.course_id
						AND tc.`LOCAL_STATUS`=1
						GROUP BY c.`CLASS_ID`,tc.`COURSE_ID`;  
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
        
        OPEN curClassCourse;  
	        class_loop: LOOP  
		        FETCH curClassCourse INTO v_class_id,v_course_id,v_course_name,v_grade_id,v_subject_id,v_course_time;
		        -- 初始化拼接sql
		        SET tmp_sql = '';
		        SET tmp_sql_columns = '';
		        SET tmp_sql_values = '';
		        -- 首先查询当前班级，当前专题，有没有记录，如果有，删除，再添加
		        SELECT COUNT(*) INTO @sign FROM admin_performance_teacher a WHERE a.`CLASS_ID`=v_class_id AND a.course_id = v_course_id AND a.grade_id=v_grade_id AND a.subject_id=v_subject_id;
		        IF @sign>0 THEN
				DELETE FROM admin_performance_teacher WHERE class_id=v_class_id AND course_id = v_course_id AND grade_id=v_grade_id AND subject_id=v_subject_id;
		        END IF;
		        
		        SET tmp_sql_columns = CONCAT(tmp_sql_columns,"c_time,class_id,course_id,course_name,grade_id,subject_id,course_time");-- 拼接列sql
		        SET tmp_sql_values = CONCAT(tmp_sql_values,"now(),",v_class_id,",",v_course_id,",'",v_course_name,"',",v_grade_id,",",v_subject_id,",'",v_course_time,"'");  -- 拼接值sql
		    
		        -- 查询专题下任务 
		        SELECT COUNT(DISTINCT ta.task_id) INTO v_task_num
			FROM tp_task_info ta,tp_task_allot_info tl
			WHERE ta.`TASK_ID`=tl.`task_id`
			AND ta.course_id=v_course_id
			AND (tl.`user_type_id`=v_class_id OR tl.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id));
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",task_num");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",",IFNULL(v_task_num,0));
			
			-- 查询专题下已结束任务
			SELECT COUNT(DISTINCT ta.task_id) INTO v_end_task_num
			FROM tp_task_info ta,tp_task_allot_info tl
			WHERE ta.`TASK_ID`=tl.`task_id`
			AND ta.course_id=v_course_id
			AND tl.`e_time`<NOW()
			AND (tl.`user_type_id`=v_class_id OR tl.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id));
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",end_task_num");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",",IFNULL(v_end_task_num,0));
			
			-- 查询专题下任务完成率
			SELECT CONCAT(ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2),"%") INTO v_complete_rate			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW();
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",complete_rate");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_complete_rate,"0.00%"),"'");
			
			-- 查询专题评价人数，和平均分
			SELECT CONCAT(ROUND(AVG(s.score),2),"|",COUNT(s.score_user_id)) INTO v_evaluation
			FROM score_info s,j_class_user ju,user_info u
			WHERE s.score_user_id = u.`USER_ID`
			AND ju.user_id = u.ref
			AND s.score_object_id=v_course_id
			AND ju.`CLASS_ID`=v_class_id;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",evaluation");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_evaluation,"0.00|0"),"'");
			
			-- 查询专题下资源学习任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_resource_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type=1;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",resource_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_resource_task,"--|--"),"'");
			
			-- 查询专题下互动交流任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_interactive_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type=2;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",interactive_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_interactive_task,"--|--"),"'");
			
			-- 查询专题下试题任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_ques_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type=3;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",ques_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_ques_task,"--|--"),"'");
			
			-- 查询专题下成卷测试任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_coilingtest_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type=4;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",coilingtest_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_coilingtest_task,"--|--"),"'");
			
			-- 查询专题下自主测试任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_selftest_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type=5;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",selftest_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_selftest_task,"--|--"),"'");
			
			-- 查询专题下微视频任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|(",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_micro_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type=6;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",micro_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_micro_task,"--|--"),"'");
			
			-- 查询专题下直播课任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_live_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type=10;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",live_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_live_task,"--|--"),"'");
			
			-- 查询专题下其余任务
			SELECT CONCAT(COUNT(DISTINCT t.`TASK_ID`),"|",ROUND(SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT jc.user_id FROM  j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND u.state_id=0))
					   WHEN 2 THEN (SELECT COUNT(user_id) FROM tp_task_performance tp,tp_task_info t  WHERE tp.creteria_type=t.criteria AND tp.task_id=t.task_id AND tp.task_id=ta.task_id AND tp.user_id IN(SELECT u.ref FROM user_info u LEFT JOIN tp_j_group_student ts ON u.user_id=ts.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0))
			END))/SUM((CASE ta.user_type WHEN 0 THEN (SELECT COUNT(*) FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.class_id=ta.user_type_id AND jc.relation_type="学生" AND u.state_id=0)
				  WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts LEFT JOIN user_info u ON ts.user_id=u.user_id WHERE ts.group_id=ta.user_type_id AND u.state_id=0)
			END))*100,2)) INTO v_general_task			
			FROM tp_task_allot_info ta,tp_task_info t
			WHERE ta.`task_id`=t.`TASK_ID`
			AND t.`STATUS`=1
			AND t.`COURSE_ID`=v_course_id
			AND (ta.`user_type_id`=v_class_id OR ta.`user_type_id` IN(SELECT group_id FROM tp_group_info WHERE class_id=v_class_id))
			AND ta.`e_time`<NOW()
			AND t.task_type>6
			AND t.task_type<10;
			SET tmp_sql_columns = CONCAT(tmp_sql_columns,",general_task");
			SET tmp_sql_values = CONCAT(tmp_sql_values,",'",IFNULL(v_general_task,"--|--"),"'");
			
			-- 插入记录表
			SET tmp_sql = CONCAT("insert into admin_performance_teacher(",tmp_sql_columns,")values(",tmp_sql_values,")");
			SET @tmp_sql = tmp_sql;
			PREPARE stmt FROM @tmp_sql;
			EXECUTE stmt;
		        IF done=1 THEN  
			    LEAVE class_loop;  -- 如果没有数据，跳出循环
		        END IF;
		        
		        -- 任务类型  1：资源学习  2：互动交流  3： 课后作业  4:成卷测试  5：自主测试   6:微视频  7-9:移动端任务 7:图片  8：文字  9：视频
	   
	        END LOOP class_loop;  
	CLOSE curClassCourse; 
	
    END$$

DELIMITER ;