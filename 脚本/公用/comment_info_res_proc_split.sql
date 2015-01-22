DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `comment_info_res_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `comment_info_res_proc_split`(
				          p_comment_id VARCHAR(1000),
				          p_comment_type INT,
				          p_comment_object_id VARCHAR(1000),
				          p_comment_user_id INT,
				          p_report_user_id INT,
				          p_comment_parent_id VARCHAR(50),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE order_tmp VARCHAR(2000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' t.*,rs.res_name,rs.FILE_SUFFIXNAME,rs.file_size,IFNULL(IFNULL(sr.stu_name,tr.teacher_name),
		r.user_name)rrealname,IFNULL(IFNULL(sc.stu_name,tc.teacher_name),c.user_name)crealname,
		IFNULL(IFNULL(tc.img_heard_src,c.head_image),''images/defaultheadsrc.jpg'')headimage  ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT '  comment_info t INNER JOIN rs_resource_info rs ON t.COMMENT_OBJECT_ID=rs.res_id 
	LEFT JOIN user_info r ON r.user_id=t.REPORT_USER_ID 
	
	LEFT JOIN student_info sr ON sr.`USER_ID`=r.`REF` AND r.`USER_ID`=t.REPORT_USER_ID 
	
	LEFT JOIN teacher_info tr ON tr.USER_ID= r.`REF` AND r.`USER_ID`=t.REPORT_USER_ID
	
	INNER JOIN user_info c ON c.user_id=t.COMMENT_USER_ID
	
	LEFT JOIN student_info sc  ON sc.`USER_ID`=c.`REF`
	
	LEFT JOIN teacher_info tc ON tc.user_id= c.`REF`   '; 
        	
	
	
	IF p_comment_object_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.COMMENT_OBJECT_ID='",p_comment_object_id,"'");
	END IF;
	
	
	IF p_report_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.REPORT_USER_ID=",p_report_user_id);
	END IF;
	
	IF p_comment_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.COMMENT_ID='",p_comment_id,"'");
	END IF;
	
	
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.COMMENT_USER_ID=",p_comment_user_id);
	END IF;
	
	IF p_comment_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.COMMENT_TYPE=",p_comment_type);
	END IF;
	
	IF p_comment_parent_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.COMMENT_PARENT_ID='",p_comment_parent_id,"'");
	END IF;
	
	SET order_tmp=CONCAT(order_tmp," ORDER BY t.c_time");
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET order_tmp=CONCAT(order_tmp," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
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
	
    END$$

DELIMITER ;