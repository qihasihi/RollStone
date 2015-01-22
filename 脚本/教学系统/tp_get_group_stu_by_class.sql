DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_get_group_stu_by_class`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_get_group_stu_by_class`(
				          p_groupid BIGINT,
				          p_user_id INT,
				          p_class_id	INT,
				          p_subject_id INT,
				          		p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' gi.*,s.stu_no,s.stu_name,u.user_id ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' gs.user_id=u.user_id and gs.group_id=gi.group_id and u.ref=s.user_id ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_j_group_student gs,user_info u,student_info s,tp_group_info gi '; 
	
	IF p_groupid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.GROUP_ID=",p_groupid);
	END IF;
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.C_USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.CLASS_ID=",p_class_id);
	END IF;
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.subject_id=",p_subject_id);
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
