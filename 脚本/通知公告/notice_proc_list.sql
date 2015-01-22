DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `notice_proc_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `notice_proc_list`(p_ref VARCHAR(60),
                                          p_dc_school_id INT,
					  p_current_page INT,
					  p_page_size	INT,
					  p_sort_column VARCHAR(50),
					  OUT totalNum INT
					)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' n1.*,IFNULL(t.teacher_name,s.stu_name) AS realname FROM (SELECT n.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(300) DEFAULT 'notice_info n';
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition = CONCAT(tmp_search_condition," and n.ref='",p_ref,"'");
	END IF;
	IF p_dc_school_id>0 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and n.dc_school_id=",p_dc_school_id);
	END IF;	
	
	SET tmp_sql = CONCAT("select ",tmp_search_column," from ",tmp_tbl_name," where ",tmp_search_condition);
	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	SET tmp_sql = CONCAT(tmp_sql,") n1 LEFT JOIN teacher_info t ON n1.c_user_id = t.user_id LEFT JOIN student_info s ON n1.c_user_id=s.user_id");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	
	SET tmp_sql=CONCAT(" select count(n.ref) into @totalcount from ",tmp_tbl_name," where ",tmp_search_condition);
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET totalNum = @totalcount;
END $$

DELIMITER ;
