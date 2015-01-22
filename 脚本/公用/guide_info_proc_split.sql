DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `guide_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `guide_info_proc_split`(
				       p_ref BIGINT,
					p_op_table VARCHAR(100),
					p_op_user BIGINT,
					p_op_type INT,     				         
					p_current_page INT(10),
					p_page_size INT(10),	
					p_order_by VARCHAR(200)	,					
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'guide_info u'; 
	DECLARE p_sort_column VARCHAR(2000) DEFAULT 'ctime desc';
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF=",p_ref);
	END IF;
	
	
	IF p_op_table IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.op_table='",p_op_table,"'");
	END IF;
	
	IF p_op_user IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.op_user=",p_op_user);
	END IF;
	
	IF p_op_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.op_type=",p_op_type);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	IF p_order_by IS NOT NULL THEN
	set  p_sort_column=p_order_by;	 
	END IF;
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY ",p_sort_column);
	
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
