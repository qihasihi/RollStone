DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dictionary_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dictionary_info_proc_split`(
				          p_ref VARCHAR(1000),
				          p_dictionary_name VARCHAR(1000),
				          p_dictionary_value VARCHAR(1000),
				          p_dictionary_type VARCHAR(1000),
				          p_order_idx INT,
							p_current_page INT(10),
							p_page_size INT(10),							
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'dictionary_info u'; 
	DECLARE p_sort_column VARCHAR(2000) DEFAULT 'c_time desc';
	IF p_order_idx IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ORDER_IDX=",p_order_idx);
	END IF;
	
	
	IF p_dictionary_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.DICTIONARY_TYPE='",p_dictionary_type,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
	END IF;
	
	IF p_dictionary_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.DICTIONARY_NAME='",p_dictionary_name,"'");
	END IF;
	
	IF p_dictionary_value IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.DICTIONARY_VALUE='",p_dictionary_value,"'");
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	
	
	
	If p_dictionary_type is not null then 
	      IF p_dictionary_type='TP_QUESTION_TYPE' THEN
		SET tmp_sql=CONCAT(tmp_sql," and dictionary_value<5 ORDER BY CASE dictionary_value WHEN 1 THEN 4 WHEN 2 THEN 3 WHEN 3 THEN 1 WHEN 4 THEN 2 END");	
	       elseif p_dictionary_type='RES_TYPE' THEN
	         SET tmp_sql=CONCAT(tmp_sql," ORDER BY CASE dictionary_value WHEN 3 THEN 1 WHEN 4 THEN 2 WHEN 2 THEN 3 WHEN 5 THEN 4 WHEN 1 THEN 5 END");	
	       ELSEIF p_dictionary_type='TP_TASK_TYPE' THEN
                 SET tmp_sql=CONCAT(tmp_sql," and (dictionary_value<7 or dictionary_value >9)  ORDER BY DICTIONARY_VALUE+0 ");	     			
	       END IF;
	else 
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY DICTIONARY_TYPE,DICTIONARY_VALUE");
	end if;
	
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
