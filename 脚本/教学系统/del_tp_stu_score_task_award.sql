DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `del_tp_stu_score_task_award`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `del_tp_stu_score_task_award`(
					  p_task_id BIGINT,
				          OUT sumCount INT
				          )
BEGIN
	-- 监测CURSOR的变量
	DECLARE done INT DEFAULT 0;
	-- 接收变量	
		-- user_id
	DECLARE tmp_user_id INT;
		-- 相关的course_id
	DECLARE tmp_course_id BIGINT;
		-- 此任务奖励过的分数
	DECLARE tmp_sumScore BIGINT;  
	-- 需要删除，返还积分的学生( http://192.168.10.8:8080/browse/PJXPT-1269中修改，不去除积分修改)
	/*DECLARE cur_user CURSOR FOR SELECT u.user_id,tp.course_id FROM `tp_task_performance` tp,user_info u WHERE tp.user_id=u.ref AND task_id=p_task_id;*/
	-- 监听CURSOR的数据，如果没有，则返回
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	-- 初始化输出参数
	SET sumCount=0;
	-- ------------------------------------------- 遍历做完任务的人(不扣除积分 http://192.168.10.8:8080/browse/PJXPT-1269)----------------------------------------------- --
	/*
	OPEN cur_user;
	 -- 开始循环
	  user_loop: LOOP
		    -- 提取游标里的数据
		    FETCH cur_user INTO tmp_user_id,tmp_course_id;
			    -- 声明结束的时候
			    IF done=1 OR tmp_user_id IS NULL THEN
			      LEAVE user_loop;
			    END IF;
			    -- 查询加分情况
			    SELECT SUM(score) INTO tmp_sumScore FROM tp_stu_score_logs WHERE user_id=tmp_user_id AND task_id=p_task_id;	
				    -- 更新此前的分数  task_score-tmp_sumScore and course_total_score-tmp_sumScore
				    UPDATE  tp_stu_score SET 
						task_score=task_score-tmp_sumScore,course_total_score=course_total_score-tmp_sumScore
						WHERE course_id=tmp_course_id AND user_id=tmp_user_id;
			    -- 更新任务积分日志表修改分数为 0
			    UPDATE tp_stu_score_logs SET score=0 WHERE user_id=tmp_user_id AND task_id=p_task_id;
		     END LOOP;
	CLOSE cur_user;
	*/
	-- 删除所有的相关任务完成情况	
	DELETE FROM tp_task_performance WHERE  task_id=p_task_id;
	SET sumCount=1;
    END$$

DELIMITER ;