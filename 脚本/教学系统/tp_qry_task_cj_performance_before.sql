DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_task_cj_performance_before`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_qry_task_cj_performance_before`(p_task_id BIGINT,OUT affect_row INT)
BEGIN
	DECLARE paperid BIGINT DEFAULT 0;-- 试卷id
	DECLARE stu_paper_logs_ref INT;-- 试卷主键，用于跟新
	DECLARE stu_paper_logs_userid INT;-- 试卷中得分是0的学生id
	
	DECLARE stu_paper_ques_logs_score FLOAT;-- 试卷中试题
	
	
	-- 定义游标，查询得分是0分，并且没有批阅的学生
	
	DECLARE done INT DEFAULT 0;  -- 游标的跳出标识
        DECLARE curPaperLogs CURSOR FOR SELECT sl.ref, sl.user_id FROM stu_paper_logs sl WHERE sl.paper_id=paperid AND sl.is_marking=1;  
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1; 
        DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0; 	
	
	-- 查询试卷id
	IF p_task_id IS NOT NULL THEN
		SELECT ta.`TASK_VALUE_ID` INTO paperid FROM tp_task_info ta WHERE ta.`TASK_ID`= p_task_id;
	END IF;
	
	IF paperid <> 0 THEN 
		OPEN curPaperLogs;  
		     paper_loop: LOOP  
			 FETCH curPaperLogs INTO stu_paper_logs_ref,stu_paper_logs_userid;  
			 -- 查询统计学生试卷试题的得分
			 SELECT SUM(score) INTO stu_paper_ques_logs_score FROM stu_paper_ques_logs WHERE paper_id=paperid AND user_id=stu_paper_logs_userid AND is_marking=0;
			 -- 如果得分大于0 ，那么更新试卷记录的得分
			 IF stu_paper_ques_logs_score > 0 THEN
				UPDATE stu_paper_logs SET score=stu_paper_ques_logs_score WHERE ref=stu_paper_logs_ref;
			 END IF;
			 
			 IF done=1 THEN  
			     LEAVE paper_loop;  -- 如果没有数据，跳出循环
			 END IF; 
			 
		     END LOOP paper_loop;  
		CLOSE curPaperLogs;  
	END IF;
	
	SET affect_row = 1;
    END$$

DELIMITER ;