DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_group_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_group_proc_split`(
				          p_ref INT,
				          p_group_id varchar(50),
				          p_task_id VARCHAR(1000),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' tg.*,g.group_name,g.class_id  ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 and tg.group_id=g.group_id ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_task_group tg,tp_group_info g '; 
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tg.REF=",p_ref);
	END IF;
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tg.GROUP_ID='",p_group_id,"'");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tg.TASK_ID='",p_task_id,"'");
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
