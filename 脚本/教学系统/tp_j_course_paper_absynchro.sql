DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_paper_absynchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_paper_absynchro`(
				          p_course_id BIGINT,				     
				          p_papertype INT,
				    
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.*,paper_name,paper_type ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT 'p.paper_id=u.paper_id';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT '  tp_j_course_paper u,paper_info p  '; 
	
	IF p_papertype IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and p.PAPER_TYPE=",p_papertype);
	END IF;	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT t.* FROM ( ",tmp_sql,") t");	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
