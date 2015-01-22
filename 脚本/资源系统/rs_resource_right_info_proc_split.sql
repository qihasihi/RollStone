DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_right_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_right_info_proc_split`(
				         p_ref VARCHAR(1000),
					 p_c_user_id VARCHAR(100),
					  p_right_type INT,
					    p_right_roletype INT,
					     p_right_subject VARCHAR(1000),
				            p_right_user_ref VARCHAR(1000),
				            res_id VARCHAR(100),
				              subjectid VARCHAR(100),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),							
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' rr.*,IFNULL(tea.teacher_name,IFNULL(stu.stu_name,u.user_name)) realname,u.USER_ID rightuserid ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_resource_right_info rr INNER JOIN user_info u ON u.REF=rr.right_user_ref'; 		
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,' LEFT JOIN student_info stu ON stu.USER_ID=rr.right_user_ref');
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,' LEFT JOIN teacher_info tea ON tea.USER_ID=rr.right_user_ref');
	
	
	
	IF p_right_user_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.RIGHT_USER_REF='",p_right_user_ref,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.C_USER_ID='",p_c_user_id,"'");
	END IF;
		IF subjectid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.subjectid='",subjectid,"'");
	END IF;
	
	IF p_right_subject IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.RIGHT_SUBJECT='",p_right_subject,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.REF='",p_ref,"'");
	END IF;
	
	IF p_right_roletype IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.RIGHT_ROLETYPE=",p_right_roletype);
	END IF;
	
	IF p_right_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.RIGHT_TYPE=",p_right_type);
	END IF;
	IF res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and rr.res_id='",res_id,"'");
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
