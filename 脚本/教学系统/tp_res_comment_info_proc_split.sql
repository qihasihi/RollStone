DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_res_comment_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_res_comment_info_proc_split`(
					  p_ref varchar(50),
				          p_to_user_id varchar(50),
				          p_reply_user_id varchar(50),
				          p_course_id varchar(50),
				          p_c_user_id varchar(50),
				          p_type INT,
				          p_parent_comment_id varchar(50),
				          p_res_detail_id varchar(50),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' t.*,rs.file_name,rs.file_size,ifnull(ifnull(sr.stu_name,tr.teacher_name),
		r.user_name)rrealname,ifnull(ifnull(sc.stu_name,tc.teacher_name),c.user_name)crealname,
		ifnull(ifnull(tc.img_heard_src,c.head_image),''images/defaultheadsrc.jpg'')headimage ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' '; 
	
	set tmp_tbl_name='  tp_res_comment_info t inner join tp_resource_base_info b on t.res_id=b.resource_id inner join rs_resource_file_info rs on t.res_id=rs.res_id 
	left join user_info r on r.ref=t.reply_user_id left join student_info sr on sr.user_id=t.reply_user_id 
	left join teacher_info tr on tr.user_id=t.reply_user_id
	inner join user_info c on c.ref=t.c_user_id 
	left join student_info sc on sc.user_id =t.c_user_id 
	left join teacher_info tc on tc.user_id= t.c_user_id ';
	
	IF p_to_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.TO_USER_ID='",p_to_user_id,"'");
	END IF;
	
	IF p_reply_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.REPLY_USER_ID='",p_reply_user_id,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.REF='",p_ref,"'");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.COURSE_ID='",p_course_id,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	IF p_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.TYPE=",p_type);
	END IF;
	
	IF p_parent_comment_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.PARENT_COMMENT_ID='",p_parent_comment_id,"'");
	END IF;
	
	IF p_res_detail_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.RES_ID='",p_res_detail_id,"'");
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
