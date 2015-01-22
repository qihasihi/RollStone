DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_task_cj_performance`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_qry_task_cj_performance`(p_task_id BIGINT,p_class_id INT,p_class_type INT,p_subject_id INT)
BEGIN
	-- 定义试题id
	DECLARE paperid BIGINT DEFAULT 0;
	
	DECLARE tmp_sql VARCHAR(20000) DEFAULT 'SELECT ifnull(t.group_id,0),ifnull(t.group_name,"--"),a.* FROM (select stu.stu_name,sp.user_id,';
	
	
	-- 定义循环试卷中试题id的变量
	DECLARE idx INT DEFAULT 0;
	DECLARE total INT DEFAULT 0;
	-- 定义临时变量试题id用来查询试题的具体信息
	DECLARE quesid BIGINT DEFAULT 0;
	-- 定义正确答案
	DECLARE rightAnswer VARCHAR(100) DEFAULT '';
	-- 定义试题类型
	DECLARE questype INT DEFAULT 0;
	
	-- 定义变量接收试卷中的所有试题id，排好序的,用逗号隔开
	DECLARE quesids VARCHAR(1000) DEFAULT '';
	-- 根据传入参数任务id查询试卷id
	SELECT task_value_id INTO paperid FROM tp_task_info  WHERE task_id=p_task_id;
	
	IF paperid =0 THEN 
		SET paperid=-3759849429;
	END IF;
	
	-- 查询学习时间
	SET tmp_sql = CONCAT(tmp_sql,"(select c_time from stu_paper_logs where user_id=sp.user_id and paper_id=sp.paper_id) studytime,");
	SET tmp_sql = CONCAT(tmp_sql,"(SELECT 
					CASE WHEN (SELECT COUNT(*)  FROM (
					SELECT 
					    CASE
					      WHEN q.question_type >= 6 
					      THEN 
					      (SELECT group_concat(
						ques_id )
					      FROM
						j_ques_team_rela qtr,
						question_info qt 
					      WHERE qtr.ques_id = qt.question_id 
						AND team_id = q.question_id) 
					      ELSE q.question_id 
					    END question_id
					FROM
					  (SELECT 
					    question_id 
					  FROM
					    j_paper_question 
					  WHERE paper_id = ",paperid,"
					  ORDER BY order_idx) pq,
					  question_info q 
					WHERE pq.question_id = q.question_id 
					) t WHERE EXISTS(
						SELECT 1 FROM stu_paper_ques_logs spql WHERE spql.paper_id = ",paperid," and spql.`ques_id` in(t.question_id) AND spql.`user_id`=sp.user_id AND `is_marking`=1
					)
					)<1 THEN (select score from stu_paper_logs where user_id=sp.user_id and paper_id=",paperid,") ELSE '--'  END
					FROM DUAL) score");
	-- 如果选择全部，需要查询班级名称
	IF p_class_type=0 THEN
		SET tmp_sql = CONCAT(tmp_sql,",(SELECT CONCAT(c.class_grade,c.class_name) FROM class_info c INNER JOIN j_class_user cu ON c.class_id=cu.class_id LEFT JOIN tp_group_info g ON g.class_id=c.class_id
 WHERE cu.user_id=u.ref AND  (c.class_id=tll.user_type_id OR g.group_id=tll.user_type_id) ) clsname");
	END IF;
	
	-- 查询试卷中所有试题id	
	SELECT GROUP_CONCAT(CASE WHEN
		q.question_type>=6 THEN
		(SELECT GROUP_CONCAT(ques_id) FROM j_ques_team_rela qtr,question_info qt WHERE qtr.ques_id=qt.question_id AND  team_id=q.question_id)
		ELSE
		q.question_id
		END) allQuesId INTO quesids
		 FROM (
		SELECT question_id FROM j_paper_question WHERE paper_id=paperid ORDER BY order_idx
		) pq,question_info q 
		WHERE pq.question_id=q.question_id ;
	-- 循环试题id，实现行转列
	IF quesids IS NOT NULL THEN
		SET total = get_split_string_total(quesids,',');
		SET idx = 0;
		WHILE idx<total DO
			SET tmp_sql=CONCAT(tmp_sql,",ifnull((select group_concat(if(is_marking=0,score,'--')");			
			SET quesid = get_split_string(quesids,',',idx+1);
			SELECT question_type INTO questype FROM question_info WHERE question_id=quesid;
			IF questype=3 OR questype=7 THEN
				SET tmp_sql=CONCAT(tmp_sql,",'|',is_right,'|',1");
			ELSEIF questype=4 OR questype=8 THEN
				SELECT REPLACE(GROUP_CONCAT(option_type),',','|') INTO rightAnswer FROM j_question_option WHERE question_id=quesid  AND is_right=1;
				SET tmp_sql=CONCAT(tmp_sql,",'|',if(answer='",rightAnswer,"',1,0),'|',1");
			ELSE
				SET tmp_sql=CONCAT(tmp_sql,",'|',ifnull(is_right,1),'|',0");
			END IF;
			
			SET tmp_sql=CONCAT(tmp_sql,") from stu_paper_ques_logs where ques_id=",quesid," and user_id=sp.user_id and paper_id=sp.paper_id),'--|0|");
				IF questype=3 OR questype=7 THEN
					SET tmp_sql=CONCAT(tmp_sql,"1");
				ELSEIF questype=4 OR questype=8 THEN
					SELECT GROUP_CONCAT(option_type,"|") INTO rightAnswer FROM j_question_option WHERE question_id=quesid  AND is_right=1;
					SET tmp_sql=CONCAT(tmp_sql,"1");
				ELSE
					SET tmp_sql=CONCAT(tmp_sql,"0");
				END IF;
			SET tmp_sql=CONCAT(tmp_sql,"') 'col",idx+1,"'");
			SET idx = idx+1;
		END WHILE;
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," from stu_paper_ques_logs sl right join stu_paper_logs sp on sl.paper_id=sp.paper_id and sl.task_id=sp.task_id and sl.user_id=sp.user_id ");
	SET tmp_sql = CONCAT(tmp_sql," left join tp_task_info ta on sp.paper_id = ta.task_value_id ");
	SET tmp_sql = CONCAT(tmp_sql," left join tp_task_allot_info tll on tll.task_id=ta.task_id ");
	SET tmp_sql = CONCAT(tmp_sql," left join tp_task_performance tp on ta.task_id = tp.task_id ");
	SET tmp_sql = CONCAT(tmp_sql," left join user_info u on sp.user_id = u.user_id ");
	SET tmp_sql = CONCAT(tmp_sql," left join student_info stu on u.ref = stu.user_id ");
	IF p_class_id IS NOT NULL AND p_class_id>0 THEN
		SET tmp_sql = CONCAT(tmp_sql," left join j_class_user jc on u.ref = jc.user_id ");
	END IF;
	SET tmp_sql = CONCAT(tmp_sql," where sp.paper_id=",paperid," and u.ref=tp.user_id");
	IF p_class_id IS NOT NULL AND p_class_id>0 THEN
		SET tmp_sql = CONCAT(tmp_sql," and jc.class_id= ",p_class_id);
	END IF;	
	SET tmp_sql=CONCAT(tmp_sql," group by sp.user_id)a LEFT JOIN (
						SELECT DISTINCT t.group_id,t.group_name,ts.user_id FROM tp_j_group_student ts INNER JOIN tp_group_info t ON t.group_id=ts.group_id 
						WHERE t.subject_id=",p_subject_id," AND t.class_id=",p_class_id,"
					) t ON a.user_id= t.user_id  order by IF(a.score='--',0,CAST(a.score as DECIMAL)) desc");
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
    END$$

DELIMITER ;