DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_question_bank_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_question_bank_proc_split`(
					  p_question_id varchar(50),	
				          p_parent_ques_id varchar(50),
				          p_c_user_id varchar(50),
				          p_ques_type INT,
				          p_ques_name VARCHAR(1000),
				          p_is_right VARCHAR(2),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_question_bank u'; 
	
	IF p_parent_ques_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PARENT_QUES_ID='",p_parent_ques_id,"'");
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.IS_RIGHT='",p_is_right,"'");
	END IF;
	
	IF p_ques_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUES_TYPE=",p_ques_type);
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUESTION_ID='",p_question_id,"'");
	END IF;
	
	IF p_ques_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUES_NAME like '%",p_ques_name,"%'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
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
