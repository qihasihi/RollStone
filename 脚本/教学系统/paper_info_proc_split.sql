DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `paper_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `paper_info_proc_split`(
				          p_ref INT,
				          p_paper_id BIGINT,
				          p_paper_name VARCHAR(1000),
				          p_c_user_id INT,
				          p_score INT,
				          p_paper_type INT,
				          p_parentpaperid BIGINT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'paper_info u'; 
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_ID=",p_paper_id);
	END IF;
	IF p_parentpaperid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.parent_paperid=",p_parentpaperid);
	END IF;
	
	IF p_paper_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_NAME='",p_paper_name,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID=",p_c_user_id);
	END IF;
	
	
	IF p_score IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SCORE=",p_score);
	END IF;
	
	IF p_paper_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_TYPE=",p_paper_type);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
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
