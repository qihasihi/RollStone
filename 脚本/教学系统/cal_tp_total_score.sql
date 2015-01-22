DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_tp_total_score`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `cal_tp_total_score`(IN in_dc_school_id INT, IN in_class_id BIGINT, IN in_course_id BIGINT, IN in_subject_id INT ,OUT out_flag INT)
BEGIN
        DECLARE  v_num1 INT; -- 组内成员全部出勤且无迟到早退 （1 是 0否）
	DECLARE	v_num2 INT; -- 本组笑脸总数
	DECLARE	v_num3 INT; -- 本组红旗数
	DECLARE	v_num4 INT; -- 本组违反纪律次数
  DECLARE	v_num5 FLOAT; -- 小组任务完成率
	DECLARE	v_group_id BIGINT;  -- 小组id
  DECLARE	v_user_id BIGINT;  -- 学员id
	
	DECLARE	v_taskcount INT;  -- 小组发的任务数*小组人数
  DECLARE	v_real_taskcount INT;-- 小组实际完成任务数
	DECLARE	v_group_total INT; -- 小组数量
	DECLARE	v_g_item_total INT;-- 统计项的总数
	DECLARE  v_subject_id INT;-- 小组学科
	DECLARE	v_sql VARCHAR(2000) DEFAULT '';
	DECLARE	v_count_1 INT;-- 计数器
	DECLARE	v_group_score_num INT DEFAULT 0;-- 小组得分记录数量	
	  -- 需要定义接收游标数据的变量 
	  -- 遍历数据结束标志
	  DECLARE done INT DEFAULT 0;
	  -- 游标 --  找到此课程，此班级班级对应的N个小组
	 DECLARE  curA CURSOR FOR	    SELECT group_id ,subject_id FROM tp_group_info  WHERE class_id=in_class_id AND subject_id=in_subject_id;
    -- 游标
 -- 3 A 组内成员全部出勤且无迟到早退
   DECLARE  cur3A CURSOR FOR	  SELECT a.*  FROM (
    SELECT SCORE1_FLAG ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY SCORE1_FLAG)a ORDER BY a.score1_flag DESC   ;
    -- 3 B 本组笑脸总数排全班第一
    DECLARE cur3B CURSOR FOR	SELECT a.* FROM (
    SELECT score2_total ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY score2_total) a ORDER BY a.score2_total DESC ;
  
    -- 3 C 本组小红旗总数排全班第一
      
    DECLARE cur3C CURSOR FOR SELECT a.* FROM (
    SELECT award_number ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY award_number) a  ORDER BY award_number DESC ;
    -- 3 D 本组违反纪律次数排全班第一
        -- 惩罚项 --负值  ORDER BY  不用 desc
     DECLARE cur3D CURSOR FOR SELECT a.* FROM (
    SELECT score4_total ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY score4_total)a ORDER BY score4_total  ;
   
   
   
  -- 4 查找上过此课程的所有学员
 DECLARE curB CURSOR FOR SELECT group_id,user_id FROM tp_stu_score WHERE course_id=in_course_id;
	  -- 将结束标志绑定到游标
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	  
   SET out_flag=0;         
  -- 查询小组数量
    SELECT COUNT(*) INTO v_group_total FROM tp_group_info WHERE class_id=in_class_id AND subject_id=in_subject_id;
	
	 
	  
	  -- 打开游标
	  OPEN curA;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH curA INTO v_group_id,v_subject_id;
		    -- 声明结束的时候
		    IF done=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		       -- 1 查询小组是否有小组得分记录，没有添加一条。
		      SELECT COUNT(*) INTO v_group_score_num FROM tp_group_score WHERE course_id=in_course_id AND group_id=v_group_id ;
		      IF v_group_score_num=0 THEN 
			 INSERT INTO tp_group_score (class_id,group_id,course_id,dc_school_id,subject_id) VALUES 
			 (in_class_id,v_group_id,in_course_id,in_dc_school_id,v_subject_id	);
		      END IF;
		    
	  END LOOP;
	  -- 关闭游标
	  CLOSE curA;
	  -- ----------------------------------------------------------------------------------------------------------
	  
	  -- 打开游标
     SET done=0;
	  OPEN curA;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH curA INTO v_group_id,v_subject_id;
        
        -- 声明结束的时候
		    IF done=1 THEN
		      LEAVE read_loop;
		    END IF;
		    -- 2 计算单项得分 ，修改小组得分表的对应的统计记录
		    -- 2 A 组内成员全部出勤且无迟到早退
      SELECT ((SELECT COUNT(*) FROM tp_stu_score WHERE class_id=in_class_id AND group_id=v_group_id
      AND course_id=in_course_id AND attendance_num=2)-
      (SELECT COUNT(*) FROM tp_stu_score WHERE class_id=in_class_id AND course_id=in_course_id  AND group_id=v_group_id))
      INTO v_num1 FROM DUAL;
      IF v_num1=0 THEN
         --  0，组内成员全部出勤且无迟到早退
         UPDATE tp_group_score SET SCORE1_FLAG=1 WHERE
         group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
      END IF;
  -- 2 B 本组笑脸总数
         SELECT IFNULL (SUM(smiling_num),0) INTO v_num2 FROM  tp_stu_score
         WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ;
         UPDATE tp_group_score tgs SET SCORE2_TOTAL =v_num2  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
  -- 2 C 本组小红旗总数 --教师或小组组长添加，已有数据 award_number
      -- 2 D 本组违反纪律次数
         SELECT IFNULL(SUM(violation_dis_num),0) INTO v_num4 FROM  tp_stu_score
         WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ;
         UPDATE tp_group_score SET SCORE4_TOTAL =v_num4  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
     -- 2 E 本组完成网上任务完成率
    -- 待完善            (任务只能分给班级?)
      SELECT (SELECT  COUNT(*) FROM tp_task_info t,tp_task_allot_info ttai
      WHERE t.TASK_ID=ttai.task_id
      AND (
	(
		ttai.user_type=0 AND ttai.user_type_id=in_class_id
	) OR(
		ttai.user_type=2 AND ttai.user_type_id=v_group_id
	)
      )
      AND t.COURSE_ID=in_course_id
      AND ttai.e_time<NOW())
      *
      (SELECT COUNT(*) FROM tp_j_group_student tjgs WHERE tjgs.GROUP_ID=v_group_id)
       INTO v_taskcount  FROM   DUAL   ;
       
       
       
       
                
      IF v_taskcount=0 THEN
               
           UPDATE tp_group_score SET SCORE5_AVG =0  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
        ELSE  
           SELECT  COUNT(DISTINCT(ttp.ref))  INTO v_real_taskcount FROM tp_task_performance ttp,tp_task_info tti,
            tp_task_allot_info ttai,tp_j_group_student tjgs,user_info ui WHERE tjgs.GROUP_ID=v_group_id
            AND ui.USER_ID=tjgs.user_id
            AND ui.ref=ttp.USER_ID
            AND ttai.task_id=tti.TASK_ID
            AND ttp.TASK_ID=tti.TASK_ID
            AND tti.TASK_ID IN (
            SELECT  t.TASK_ID FROM tp_task_info t,tp_task_allot_info ttai
            WHERE t.TASK_ID=ttai.task_id
            AND ((ttai.user_type=0 
            AND ttai.user_type_id=in_class_id
		    ) OR (
			ttai.user_type=2 AND ttai.user_type_id=v_group_id
		    )
            )
            
            AND t.COURSE_ID=in_course_id
            AND ttai.e_time<NOW()) ;
            UPDATE tp_group_score SET SCORE5_AVG =v_real_taskcount/v_taskcount  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
        END IF;
      
	  END LOOP;
	  -- 关闭游标
    CLOSE curA;
-- ----------------------------------------------------------------------------
   -- 3 计算单项得分 ，修改小组得分表的对应的排名得分
    -- 3 A 组内成员全部出勤且无迟到早退
    SET v_num1=0;
    SET v_g_item_total=0;
    SET done=0;
 -- 打开游标
   SET  v_count_1=0;
	  OPEN cur3A;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH cur3A INTO v_num1,v_g_item_total;
		    -- 声明结束的时候
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- 如果所有小组的分数一致，则不给分
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 THEN
                 -- 修改所有排名第一的给奖励积分
                  UPDATE tp_group_score   SET SCORE1=1 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND SCORE1_FLAG=v_num1 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- 关闭游标
	  CLOSE cur3A;
 -- 3 B 本组笑脸总数排全班第一
       SET v_num2:=0;
       SET v_g_item_total:=0;
       SET done=0;
       SET v_count_1:=0;
    
          OPEN cur3B;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH cur3B INTO v_num2,v_g_item_total;
		    -- 声明结束的时候
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- 如果所有小组的分数一致，则不给分
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 THEN
                 -- 修改所有排名第一的给奖励积分
                  UPDATE tp_group_score   SET SCORE2=3 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND SCORE2_TOTAL=v_num2 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- 关闭游标
	  CLOSE cur3B;
   -- 3 C 本组小红旗总数排全班第一
   SET v_num3:=0;
   SET   v_g_item_total:=0;
   SET done=0;
   SET v_count_1:=0;
        OPEN cur3C;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH cur3C INTO v_num3,v_g_item_total;
		    -- 声明结束的时候
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- 如果所有小组的分数一致，则不给分
               LEAVE read_loop;
           ELSE
              IF v_count_1=0  THEN
                   -- 修改所有排名第一的给奖励积分
                  UPDATE tp_group_score   SET SCORE3=3 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND award_number=v_num3 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- 关闭游标
	  CLOSE cur3C;
    
   -- 3 D 本组违反纪律次数排全班第一
        -- 惩罚项
   SET v_num4:=0;
   SET  v_g_item_total:=0;
   SET  v_count_1:=0;
   SET done=0;
      OPEN cur3D;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH cur3D INTO v_num4,v_g_item_total;
		    -- 声明结束的时候
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- 如果所有小组的分数一致，则不给分
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 AND v_g_item_total=1 THEN
                   -- 修改排名第一的一个小组进行惩罚
                  UPDATE tp_group_score   SET SCORE4=-1 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND score4_total=v_num4 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- 关闭游标
	  CLOSE cur3D;
     
   -- 3 E 本组完成网上任务完成率排全班第一
   BEGIN
       -- 3 E 本组完成网上任务平均数排全班第一
    DECLARE cur3E CURSOR FOR  SELECT a.* FROM (
    SELECT SCORE5_AVG ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY SCORE5_AVG)a ORDER BY SCORE5_AVG DESC ;
   
 SET v_num5:=0;
   SET  v_g_item_total:=0;
   SET  v_count_1:=0;
   SET done=0;
      OPEN cur3E;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH cur3E INTO v_num5,v_g_item_total;
		    -- 声明结束的时候
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- 如果所有小组的分数一致，则不给分
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 THEN
                   
                   -- 修改所有排名第一的给奖励积分
                  UPDATE tp_group_score   SET SCORE5=3 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND SCORE5_AVG=v_num5 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- 关闭游标
	  CLOSE cur3E;
	END;  
          -- -------------------------------------------------------------------------------------------------------
  -- 4 查找上过此课程的所有学员，更新学员的小组得分，课程总分=（网上+网下+小组）
     SET done=0;
     OPEN curB;
	  -- 开始循环
	  read_loop: LOOP
		    -- 提取游标里的数据
		    FETCH curB INTO v_group_id,v_user_id;
		    -- 声明结束的时候
		    IF done=1  THEN
		      LEAVE read_loop;
		    END IF;
		     -- 修改学员课程分数表 小组得分
       UPDATE tp_stu_score SET GROUP_SCORE=(
       SELECT SUM(IFNULL(SCORE1,0)+IFNULL(SCORE2,0)+IFNULL(SCORE3,0)+IFNULL(SCORE4,0)+IFNULL(SCORE5,0))  FROM tp_group_score WHERE
       class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score1=(SELECT IFNULL(SCORE1,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score2=(SELECT IFNULL(SCORE2,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score3=(SELECT IFNULL(SCORE3,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score4=(SELECT IFNULL(SCORE4,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score5=(SELECT IFNULL(SCORE5,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id )
       WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id  AND user_id=v_user_id;
       -- 修改总得分 还得有网上得分（完成任务数，课堂评价，课堂建议）
       UPDATE tp_stu_score SET COURSE_TOTAL_SCORE=(IFNULL(GROUP_SCORE,0)+IFNULL(attendance_num,0)+
        IFNULL(smiling_num,0)+IFNULL(violation_dis_num,0)+IFNULL(task_score,0)+IFNULL(comment_score,0)
       )WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id  AND user_id=v_user_id;
		        
		    
	  END LOOP;
	  -- 关闭游标
	  CLOSE curB;
  -- -------------------------------------------------------------------------------------------------------
    	SET out_flag=1;  
END $$

DELIMITER ;
