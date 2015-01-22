DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_score_rank_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_score_rank_proc_split`(
				          p_school_name VARCHAR(1000),
				           p_model_id BIGINT,
				               p_school_id BIGINT,
				              p_score FLOAT,
				          p_type_id BIGINT,
				         p_operate_type INT,
				          p_rank BIGINT,
				          p_ref BIGINT,
				       
				         
				         
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(150),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'school_score_rank u'; 
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TYPE_ID=",p_type_id);
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SCHOOL_ID=",p_school_id);
	END IF;
	
	IF p_rank IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RANK=",p_rank);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.OPERATE_TYPE=",p_operate_type);
	END IF;
	
	IF p_score IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SCORE=",p_score);
	END IF;
	
	IF p_model_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.MODEL_ID=",p_model_id);
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
