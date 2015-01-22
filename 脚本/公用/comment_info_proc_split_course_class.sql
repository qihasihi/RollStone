DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `comment_info_proc_split_course_class`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `comment_info_proc_split_course_class`(p_course_id bigint,
				          p_classid int,
				          p_classtype int,
				          p_tch_id int,
							p_current_page INT,
							p_page_size INT,
							p_sort_column VARCHAR(100),
				          OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	declare tmp_sql2 varchar(20000) default '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' cm.COMMENT_CONTEXT,stu.STU_NO,stu.STU_NAME,cm.C_TIME,sc.SCORE ';  
	DECLARE tmp_search_condition1 VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_search_condition2 VARCHAR(2000) DEFAULT ' 1=1 ';
	DECLARE tmp_tbl_name1 VARCHAR(2000) DEFAULT 'comment_info cm 
							LEFT JOIN user_info u ON cm.COMMENT_USER_ID=u.USER_ID
							LEFT JOIN student_info stu ON u.ref=stu.USER_ID
							LEFT JOIN score_info sc ON sc.COMMENT_ID=cm.COMMENT_ID
							LEFT JOIN tp_j_course_class jc ON jc.course_id=cm.COMMENT_OBJECT_ID
							LEFT JOIN j_class_user ju ON jc.class_id=ju.CLASS_ID'; 
	declare tmp_tbl_name2 varchar(2000) default 'comment_info cm 
							LEFT JOIN user_info u ON cm.COMMENT_USER_ID=u.USER_ID
							LEFT JOIN student_info stu ON u.ref=stu.USER_ID
							LEFT JOIN score_info sc ON sc.COMMENT_ID=cm.COMMENT_ID
							LEFT JOIN tp_j_course_class jc ON jc.course_id=cm.COMMENT_OBJECT_ID
							LEFT JOIN tp_j_virtual_class_student jv ON jc.class_id=jv.VIRTUAL_CLASS_ID';
	SET tmp_search_condition1=CONCAT(tmp_search_condition1," and ju.USER_ID = u.REF");
	SET tmp_search_condition1=CONCAT(tmp_search_condition1," and cm.COMMENT_TYPE=2");
	SET tmp_search_condition1=CONCAT(tmp_search_condition1," and cm.COMMENT_OBJECT_ID=",p_course_id);
	SET tmp_search_condition2=CONCAT(tmp_search_condition2," and jv.USER_ID = cm.COMMENT_USER_ID");
	SET tmp_search_condition2=CONCAT(tmp_search_condition2," and cm.COMMENT_TYPE=2");
	SET tmp_search_condition2=CONCAT(tmp_search_condition2," and cm.COMMENT_OBJECT_ID=",p_course_id);
	if p_classid is not null then
		if p_classtype=1 THEN	
			SET tmp_search_condition1=CONCAT(tmp_search_condition1," AND ju.CLASS_ID = ",p_classid);
		end if;	
		IF p_classtype=2 THEN	
			SET tmp_search_condition2=CONCAT(tmp_search_condition2," AND jv.VIRTUAL_CLASS_ID = ",p_classid);
		END IF;	
	end if;
	
	if p_classtype=1 then
		set tmp_sql=concat("SELECT ",tmp_search_column," FROM ",tmp_tbl_name1," WHERE ",tmp_search_condition1);
	elseif p_classtype=2 then
	        SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name2," WHERE ",tmp_search_condition2);
	else 
		SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name1," WHERE ",tmp_search_condition1," UNION ","SELECT ",tmp_search_column," FROM ",tmp_tbl_name2," WHERE ",tmp_search_condition2);
	end if;
	
	SET tmp_sql2=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_sql,") a");
	SET @tmp_sql=tmp_sql2;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;	
	SET sumCount=@tmp_sumCount; 
	
	SET tmp_sql = CONCAT("SELECT a.* FROM (",tmp_sql,") a");
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY a.C_TIME");	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @tmp_sql =tmp_sql;   
	PREPARE s1 FROM  @tmp_sql;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1; 
	
END $$

DELIMITER ;
