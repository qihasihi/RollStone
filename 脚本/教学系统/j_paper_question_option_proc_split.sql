DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_question_option_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_question_option_proc_split`(
					  p_ref	INT,
				          p_question_id BIGINT,
				          p_option_type VARCHAR(1000),
				          p_is_right INT,
				          p_paper_id	bigint,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,q.question_type ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' u.question_id = q.question_id AND p.question_id = u.question_id  ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'j_question_option u,question_info q,j_paper_question p'; 
	
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_ID=",p_question_id);
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.is_right=",p_is_right);
	END IF;
	
	IF p_option_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.OPTION_TYPE='",p_option_type,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ref=",p_ref);
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.paper_id=",p_paper_id);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_sql=CONCAT(tmp_sql," UNION SELECT u.*,q.question_type
		   FROM j_question_option u 
		   INNER JOIN question_info q ON u.question_id=q.question_id
		   INNER JOIN j_ques_team_rela t ON t.ques_id=u.question_id
		   INNER JOIN j_paper_question p ON  t.team_id=p.question_id WHERE 1=1 and  p.paper_id=",p_paper_id,"");	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_sql,")t ");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
