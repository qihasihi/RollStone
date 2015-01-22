DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_paper_marking_proc_getdetail`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_paper_marking_proc_getdetail`(p_paperid BIGINT,p_question_id BIGINT,p_quesid BIGINT,p_ismark INT,p_classid INT,p_classtype INT,p_taskid BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_paperid IS NOT NULL AND
	   p_quesid IS NOT NULL THEN
	      SET tmp_sql = CONCAT(tmp_sql,"SELECT s.stu_name,pl.ref,pl.answer,pl.AnnexName,q.content,q.correct_answer,q.analysis,q.question_type,q.extension");
	      SET tmp_sql = CONCAT(tmp_sql,",q.city,q.exam_year,q.axam_area,q.exam_type");
	      IF p_question_id IS NOT NULL THEN 
		  SET tmp_sql = CONCAT(tmp_sql,",(SELECT content FROM question_info q WHERE q.question_id=",p_question_id,") content2");
		  SET tmp_sql = CONCAT(tmp_sql,",(SELECT order_id FROM j_ques_team_rela WHERE team_id=",p_question_id," AND ques_id = ",p_quesid,") orderidx");
		  SET tmp_sql = CONCAT(tmp_sql,",(SELECT score FROM j_ques_team_rela WHERE team_id=",p_question_id," AND ques_id = ",p_quesid,") score");
		  SET tmp_sql = CONCAT(tmp_sql,",(SELECT question_type FROM question_info q WHERE q.question_id=",p_question_id,") question_type2");
		  SET tmp_sql = CONCAT(tmp_sql,",(SELECT extension FROM question_info q WHERE q.question_id=",p_question_id,") extension2");
	      END IF;
	      SET tmp_sql = CONCAT(tmp_sql," FROM stu_paper_ques_logs pl LEFT JOIN user_info u ON pl.user_id=u.USER_ID");
	      SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN student_info s ON u.ref=s.USER_ID");
	      SET tmp_sql = CONCAT(tmp_sql," LEFT JOIN question_info q ON pl.ques_id=q.question_id");
              SET tmp_sql = CONCAT(tmp_sql," WHERE pl.paper_id=",p_paperid);
              SET tmp_sql = CONCAT(tmp_sql," AND pl.ques_id=",p_quesid);
              SET tmp_sql = CONCAT(tmp_sql," AND pl.is_marking=",p_ismark);
              IF p_taskid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," AND pl.TASK_ID=",p_taskid);  
              END IF;
              IF p_classtype = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," and u.user_id in(SELECT distinct u.user_id FROM j_class_user jc left join user_info u on jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')");
		END IF;
		IF p_classtype=2 THEN
			SET tmp_sql = CONCAT(tmp_sql," and u.ref in(SELECT  user_id FROM  tp_j_virtual_class_student tpjc WHERE tpjc.`VIRTUAL_CLASS_ID`= ",p_classid,")");
		END IF;
              SET @sql1 =tmp_sql;   
	      PREPARE s1 FROM  @sql1;   
	      EXECUTE s1;  
	END IF;
    END$$

DELIMITER ;