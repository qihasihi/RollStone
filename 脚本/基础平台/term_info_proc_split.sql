DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `term_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `term_info_proc_split`(
					  p_ref VARCHAR(1000),
				          p_term_name VARCHAR(1000),
				          p_year VARCHAR(50),
				          p_dyyear VARCHAR(50),
				          d_xyyear VARCHAR(50),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " u.*,(SELECT 1 FROM dual  WHERE NOW() BETWEEN u.semester_begin_date AND u.semester_end_date)flag,
	IFNULL((SELECT CASE term_name 
        WHEN '第一学期'
        THEN get_split_string (YEAR, '~', 1) 
        ELSE get_split_string (YEAR, '~', 2) 
      END 
    FROM
      term_info a 
    WHERE NOW() NOT BETWEEN a.SEMESTER_BEGIN_DATE 
      AND a.SEMESTER_END_DATE 
      AND a.ref = u.ref),
    DATE_FORMAT(NOW(), '%Y')
  ) auto_year ";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'term_info u'; 
	
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
	END IF;
	
	IF p_term_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TERM_NAME='",p_term_name,"'");
	END IF;
	
	IF p_year IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.year='",p_year,"'");
	END IF;
	IF p_dyyear IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.year>'",p_dyyear,"'");
	END IF;
	IF d_xyyear IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.year<'",d_xyyear,"'");
	END IF;
	
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	    ELSE
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  u.year desc  ");
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
	
    END$$

DELIMITER ;