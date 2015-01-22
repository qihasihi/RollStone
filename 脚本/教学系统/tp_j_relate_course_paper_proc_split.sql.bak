DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_relate_course_paper_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_j_relate_course_paper_proc_split`(
				          p_course_id BIGINT,
				            p_is_cloud INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_count_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,p.paper_name,(select count(*) from tp_task_info t where t.task_value_id=u.paper_id and t.course_id=u.course_id)task_flag,
	(SELECT COUNT(*) FROM question_info q,j_paper_question pq
	WHERE q.`question_id`=pq.question_id AND pq.`paper_id`=u.paper_id AND q.question_type IN (3,4,7,8))objective1,
	 (SELECT COUNT(*) FROM j_ques_team_rela t,j_paper_question p,question_info q 
 WHERE t.team_id=p.question_id AND t.ques_id=q.`question_id`
 AND p.paper_id=u.paper_id AND q.question_type IN (3,4,7,8))objective2,
 (SELECT COUNT(*) FROM question_info q,j_paper_question pq
	WHERE q.`question_id`=pq.question_id AND pq.`paper_id`=u.paper_id AND q.question_type <3)subjective1,
	 (SELECT COUNT(*) FROM j_ques_team_rela t,j_paper_question p,question_info q 
 WHERE t.team_id=p.question_id AND t.ques_id=q.`question_id`
 AND p.paper_id=u.paper_id AND q.question_type <3)subjective2';
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 and u.paper_id=p.paper_id and u.course_id=c.course_id and c.course_id=cr.related_course_id and u.local_status=1 and c.local_status=1  ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_j_course_paper u,paper_info p,tp_course_info c,tp_j_course_related_info cr'; 
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cr.COURSE_ID=",p_course_id);
	END IF;
	
	
	IF p_is_cloud IS NOT NULL AND p_is_cloud=1 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID > 0 ");
	ELSEIF p_is_cloud=2 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID < 0 ");
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET tmp_count_sql=tmp_sql;
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT t.*,(objective1+objective2)objectivenum,(subjective1+subjective2)subjectivenum FROM ( ",tmp_sql,") t");	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
	
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql," )t ");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;