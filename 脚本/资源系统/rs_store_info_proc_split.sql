DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_store_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_store_info_proc_split`(
				          p_ref VARCHAR(1000),
				          p_res_id bigint,
				          p_user_id INT,
				          p_res_name VARCHAR(200),
				          p_subject INT,
				          p_grade INT,
				          p_res_type INT,
				          p_file_type INT,
				          p_schoolname VARCHAR(200),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_store_info u INNER JOIN rs_resource_info r 
			ON r.RES_ID = u.RES_ID'; 
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID=",p_user_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
	END IF;
	
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RES_ID=",p_res_id);
	END IF;
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_NAME LIKE '",p_res_name,"%'");
	END IF;
	IF p_schoolname IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SCHOOL_NAME ='",p_schoolname,"'");
	END IF;
	
	IF p_grade IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.GRADE=",p_grade);
	END IF;
	
	IF p_subject IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SUBJECT=",p_subject);
	END IF;
	
	IF p_res_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_TYPE=",p_res_type);
	END IF;
	
	IF p_file_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.FILE_TYPE=",p_file_type);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY u.C_TIME DESC");
	    
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT r.*,
		IFNULL(sub.SUBJECT_NAME,'--') SUBJECT_NAME,
		IFNULL(g.GRADE_NAME,'--') GRADE_NAME,
		IFNULL(d1.DICTIONARY_NAME,'--') RES_TYPE_NAME,
		IFNULL(d2.DICTIONARY_NAME,'--') FILE_TYPE_NAME FROM (",tmp_sql,") aa
		INNER JOIN rs_resource_info r ON r.RES_ID=aa.RES_ID AND r.RES_STATUS<>3
		LEFT JOIN subject_info sub ON sub.SUBJECT_ID=r.SUBJECT
		LEFT JOIN grade_info g ON g.GRADE_ID=r.GRADE
		LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=r.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'  
		LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=r.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE' ");
	
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
