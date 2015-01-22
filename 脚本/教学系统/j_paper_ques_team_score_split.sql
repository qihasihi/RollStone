DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_ques_team_score_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_ques_team_score_split`(
							ques_ref INT,
							paper_id VARCHAR(1000),				        
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(500),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(50) DEFAULT ' j_paper_ques_team_score u '; 
	
	
	
	IF ques_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ques_ref='",ques_ref,"'");
	END IF;
	
	IF paper_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.paper_id=",paper_id);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT u.* FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
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
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
