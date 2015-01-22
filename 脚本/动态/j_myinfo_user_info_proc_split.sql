DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_myinfo_user_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_myinfo_user_info_proc_split`(
				          p_msg_name VARCHAR(1000),
				          p_operate_id VARCHAR(1000),
				          p_msg_id INT,
				          p_user_ref VARCHAR(1000),
				          p_ref INT,
				          p_template_id VARCHAR(1000),
				          p_my_date VARCHAR(1000),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' mu.*,t.template_name,t.template_searator,t.template_content,t.template_url ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '  t.template_id=mu.TEMPLATE_ID ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' SELECT mu.* FROM j_myinfo_user_info mu WHERE 1=1 '; 
	DECLARE tmp_order VARCHAR(500) DEFAULT '';
	
	IF p_msg_name IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and mu.MSG_NAME='",p_msg_name,"'");
	END IF;
	
	IF p_operate_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and mu.OPERATE_ID='",p_operate_id,"'");
	END IF;
	
	IF p_msg_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and mu.MSG_ID=",p_msg_id);
	END IF;
	
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and mu.USER_REF='",p_user_ref,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and mu.REF=",p_ref);
	END IF;
	
	IF p_template_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and mu.TEMPLATE_ID='",p_template_id,"'");
	END IF;
	
	IF p_my_date IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and mu.MY_DATE='",p_my_date,"'");
	END IF;
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_order=CONCAT(tmp_order," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_order=CONCAT(tmp_order," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM (",tmp_tbl_name,tmp_order,") mu,myinfo_template_info t WHERE ",tmp_search_condition);	
	
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_tbl_name,") mu,myinfo_template_info t WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
