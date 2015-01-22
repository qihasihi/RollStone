DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_paper_marking_proc_list`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_paper_marking_proc_list`(p_paperid BIGINT,p_classid INT,p_classtype INT,p_task_id BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_paperid IS NOT NULL THEN 
		SET tmp_sql = CONCAT("SELECT t.questionid,(SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid AND s.task_id=",p_task_id);
		IF p_classtype = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')");
		END IF;		
		IF p_classtype=2 THEN
			SET tmp_sql = CONCAT(tmp_sql," AND s.user_id IN(SELECT  user_id FROM  tp_j_virtual_class_student tpjc WHERE tpjc.`VIRTUAL_CLASS_ID`= ",p_classid,")");
		END IF;	
		SET tmp_sql=CONCAT(tmp_sql,") submitnum,");
		SET tmp_sql = CONCAT(tmp_sql,"(SELECT COUNT(*) FROM stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid AND s.is_marking=0 AND s.task_id=",p_task_id);
		IF p_classtype = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')");
		END IF;		
		IF p_classtype=2 THEN
			SET tmp_sql = CONCAT(tmp_sql," AND s.user_id IN(SELECT  user_id FROM  tp_j_virtual_class_student tpjc WHERE tpjc.`VIRTUAL_CLASS_ID`= ",p_classid,")");
		END IF;	
		SET tmp_sql=CONCAT(tmp_sql,") markingnum,ifnull(round((case q.question_type 
							when 1 then ((select avg(score) from stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid AND s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生'))/
									(select score from j_paper_question jp where jp.paper_id=t.paper_id and jp.question_id=t.questionid)) 
							when 2 then ((select avg(score) from stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid AND s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生'))/
									(select score from j_paper_question jp where jp.paper_id=t.paper_id and jp.question_id=t.questionid))
							when 3 then ((SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.is_right=1 and s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生'))/(SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')))
							when 4 then ((SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.is_right=1 and s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生'))/(SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')))
							when 7 then ((SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.is_right=1 and s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生'))/(SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')))
							when 8 then ((SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.is_right=1 and s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生'))/(SELECT COUNT(DISTINCT s.user_id) FROM stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid and s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref 
									WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')))
							when 9 then ((select avg(score) from stu_paper_ques_logs s WHERE s.paper_id=t.paper_id AND s.ques_id=t.questionid AND s.task_id=",p_task_id," 
									AND s.user_id IN(SELECT DISTINCT u.user_id FROM j_class_user jc LEFT JOIN user_info u ON jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生'))/
									(select score from j_paper_question jp where jp.paper_id=t.paper_id and jp.question_id=t.questionid)) 
							end),2),0.00) correctrate
		 FROM  (
			SELECT DISTINCT q.question_id questionid ,pq.order_idx order_idx,pq.paper_id
								FROM question_info q,j_paper_question pq		
								 WHERE q.`question_id` = pq.question_id AND q.`question_type`<>6 AND pq.paper_id=",p_paperid,"
			UNION 
			(
			
			SELECT DISTINCT qtr.ques_id,t.order_idx,t.paper_id FROM 
			j_ques_team_rela qtr,
			question_info q,
			(
			SELECT  q1.question_id ,pq.order_idx,pq.paper_id
			FROM question_info q1,j_paper_question pq		
			WHERE q1.question_id = pq.question_id AND q1.question_type=6 AND pq.paper_id=",p_paperid,"
			) 			
			t WHERE t.question_id=qtr.team_id AND qtr.ques_id=q.question_id
			
			ORDER BY team_id,qtr.order_id
			
			
			
			
		   
			)
			) t,question_info q where t.questionid=q.question_id
			ORDER BY t.order_idx
		");
		
		
		SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;   
	END IF;
    END$$

DELIMITER ;