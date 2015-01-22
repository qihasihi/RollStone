DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_report_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_report_proc_split`(
				          p_ref INT,
				          p_user_id INT,
				          p_content VARCHAR(1000),
				          p_res_id BIGINT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' rr.*,
	IFNULL(IFNULL(si.STU_NAME,ti.TEACHER_NAME),u.USER_NAME) REALNAME';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_resource_report rr
	INNER JOIN user_info u on u.USER_ID=rr.USER_ID
	LEFT JOIN teacher_info ti ON ti.user_id=u.ref 
	LEFT JOIN student_info si on si.user_id=u.ref'; 
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.USER_ID=",p_user_id);
	END IF;
	
	
	IF p_content IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.CONTENT='",p_content,"'");
	END IF;
	
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.RES_ID=",p_res_id);
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
