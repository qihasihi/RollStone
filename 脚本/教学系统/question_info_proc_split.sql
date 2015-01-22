DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `question_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `question_info_proc_split`(
							  p_question_id BIGINT,
							  p_question_level INT,
							  p_question_type INT,
							  p_paper_type_id INT,
							  p_c_user_id VARCHAR(1000),
							  p_cloud_status INT,
							  p_status INT,
							  p_extension INT,
							  p_question_id_str VARCHAR(1000),  
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(1000),
							OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'question_info u'; 
	
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_ID=",p_question_id);
	END IF;
	IF LENGTH(p_question_id_str)>0 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_ID IN (",p_question_id_str,")");
	END IF;
	IF p_extension IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_column_sql,"and u.extension=",p_extension);
	END IF;
	
	
	IF p_paper_type_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PAPER_TYPE_ID=",p_paper_type_id);
	END IF;
	
	
	
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	
	
	IF p_question_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_TYPE=",p_question_type);
	END IF;
	
	
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.STATUS=",p_status);
	END IF;
	
	IF p_question_level IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_LEVEL=",p_question_level);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF LENGTH(p_question_id_str)>0 THEN
		SET p_sort_column=CONCAT(" FIELD(question_id,",p_question_id_str,") ");
	END IF;
	
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
	
    END$$

DELIMITER ;