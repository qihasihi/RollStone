DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_team_question_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_team_question_proc_split`(
					  p_ref INT,
				          p_paper_id BIGINT,
				          p_question_id BIGINT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'j_paper_question u'; 
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID=",p_paper_id);
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_ID=",p_question_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	    ELSE
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  order_idx asc");
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	
	SET tmp_sql=CONCAT(' SELECT pq.ref,ifnull((select score from j_paper_ques_team_score pqts where pqts.paper_id=pq.paper_id and pqts.ques_ref=t.ref limit 1),t.score)score,t.order_id,pq.paper_id,q.*,p.paper_name,p.score pscore,p.paper_type FROM (
		',tmp_sql,'
		) pq,question_info q,paper_info p,j_ques_team_rela t  WHERE t.ques_id=q.question_id AND t.team_id=pq.question_id AND p.paper_id=pq.paper_id order by order_id 
	');
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_sql,") a");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
