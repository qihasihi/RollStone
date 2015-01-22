DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_paper_marking_proc_getlogs`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_paper_marking_proc_getlogs`(p_paperid BIGINT,p_quesid BIGINT,p_classid INT,p_classtype INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_paperid IS NOT NULL AND
	   p_quesid IS NOT NULL THEN
	      SET tmp_sql = CONCAT(tmp_sql,"SELECT DISTINCT jp.score,(SELECT AVG(l.score) FROM stu_paper_ques_logs l WHERE l.paper_id=pl.paper_id AND l.ques_id=pl.ques_id ");
	      IF p_classtype = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," and l.user_id in(SELECT distinct u.user_id FROM j_class_user jc left join user_info u on jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')");
		END IF;
		IF p_classtype=2 THEN
			SET tmp_sql = CONCAT(tmp_sql," and l.user_id in(SELECT  user_id FROM  tp_j_virtual_class_student tpjc WHERE tpjc.`VIRTUAL_CLASS_ID`= ",p_classid,")");
		END IF;
	      
	      
	      SET tmp_sql=CONCAT(tmp_sql," AND l.is_marking=0) avgscore,");
	      SET tmp_sql = CONCAT(tmp_sql," (SELECT COUNT(l.ref) FROM stu_paper_ques_logs l WHERE l.paper_id=pl.paper_id AND l.ques_id=pl.ques_id AND l.is_marking=0 ");
	         IF p_classtype = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," and l.user_id in(SELECT distinct u.user_id FROM j_class_user jc left join user_info u on jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')");
		END IF;
		IF p_classtype=2 THEN
			SET tmp_sql = CONCAT(tmp_sql," and l.user_id in(SELECT  user_id FROM  tp_j_virtual_class_student tpjc WHERE tpjc.`VIRTUAL_CLASS_ID`= ",p_classid,")");
		END IF;	      
	      
	      SET tmp_sql=CONCAT(tmp_sql,") markingnum,");
	      
	      SET tmp_sql = CONCAT(tmp_sql," (SELECT COUNT(l.ref) FROM stu_paper_ques_logs l WHERE l.paper_id=pl.paper_id AND l.ques_id=pl.ques_id ");
	       IF p_classtype = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," and l.user_id in(SELECT distinct u.user_id FROM j_class_user jc left join user_info u on jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')");
		END IF;
		IF p_classtype=2 THEN
			SET tmp_sql = CONCAT(tmp_sql," and l.user_id in(SELECT  user_id FROM  tp_j_virtual_class_student tpjc WHERE tpjc.`VIRTUAL_CLASS_ID`= ",p_classid,")");
		END IF;	      
	      
	       SET tmp_sql=CONCAT(tmp_sql,") submitnum");
	      SET tmp_sql = CONCAT(tmp_sql," FROM stu_paper_ques_logs pl LEFT JOIN j_paper_question jp ON pl.paper_id=jp.paper_id AND pl.ques_id=jp.question_id");
              SET tmp_sql = CONCAT(tmp_sql," WHERE pl.paper_id=",p_paperid);
              SET tmp_sql = CONCAT(tmp_sql," AND pl.ques_id=",p_quesid);
              IF p_classtype = 1 THEN
			SET tmp_sql = CONCAT(tmp_sql," and pl.user_id in(SELECT distinct u.user_id FROM j_class_user jc left join user_info u on jc.user_id=u.ref WHERE jc.`CLASS_ID`=",p_classid," AND jc.`RELATION_TYPE`='学生')");
		END IF;
		IF p_classtype=2 THEN
			SET tmp_sql = CONCAT(tmp_sql," and pl.user_id in(SELECT  user_id FROM  tp_j_virtual_class_student tpjc WHERE tpjc.`VIRTUAL_CLASS_ID`= ",p_classid,")");
		END IF;
              SET @sql1 =tmp_sql;   
	      PREPARE s1 FROM  @sql1;   
	      EXECUTE s1;  
	END IF;
END $$

DELIMITER ;
