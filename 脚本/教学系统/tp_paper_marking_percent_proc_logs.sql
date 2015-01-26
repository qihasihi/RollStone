DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_paper_marking_percent_proc_logs`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_paper_marking_percent_proc_logs`(p_paper_id BIGINT,p_bignum INT,p_smallnum INT,p_classid INT,p_taskid BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_paper_id IS NOT NULL THEN
	      SET tmp_sql =CONCAT(tmp_sql,"SELECT COUNT(*) num,(select count(*) from stu_paper_logs where paper_id=",p_paper_id,")totalnum");
	      SET tmp_sql = CONCAT(tmp_sql," FROM stu_paper_logs paperlogs");
	      SET tmp_sql = CONCAT(tmp_sql," WHERE paperlogs.`score`<=",p_bignum);
	      SET tmp_sql = CONCAT(tmp_sql," AND paperlogs.`score`>=",p_smallnum);
	      SET tmp_sql = CONCAT(tmp_sql," AND paperlogs.`paper_id`=",p_paper_id);
	      SET tmp_sql = CONCAT(tmp_sql," AND paperlogs.`task_id`=",p_taskid);	      
	      SET tmp_sql=CONCAT(tmp_sql," AND paperlogs.user_id IN (
			SELECT u.user_id FROM j_class_user cu,user_info u WHERE cu.user_id=u.ref  AND cu.relation_type='学生' AND cu.class_id=",p_classid,")");
              SET @sql1 =tmp_sql;   
	      PREPARE s1 FROM  @sql1;   
	      EXECUTE s1;  
	END IF;
    END$$

DELIMITER ;