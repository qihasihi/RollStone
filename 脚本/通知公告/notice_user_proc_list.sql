DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `notice_user_proc_list`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `notice_user_proc_list`(p_userid VARCHAR(60),
						p_userrole INT,
						p_usergrade VARCHAR(100),
					       p_noticetype INT,
					        p_dc_school_id INT,
					       p_current_page INT,
						p_page_size	INT,
						p_sort_column VARCHAR(50),
						OUT totalNum INT
						)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' n.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(50) DEFAULT 'notice_info n';
	DECLARE userroleid VARCHAR(1000) DEFAULT '';
	DECLARE idx INT DEFAULT 0;
	DECLARE size INT DEFAULT 0;
	IF p_userid IS NOT NULL THEN 
		IF p_userrole IS NOT NULL THEN
			SET userroleid=REPLACE(p_userrole,'|',',');			
		    ELSE 
			SELECT GROUP_CONCAT(DISTINCT d.identity_name) INTO userroleid FROM identity_info d WHERE ROLE_ID IN (SELECT role_id FROM j_role_user WHERE user_id=p_userid);
			
		END IF;
		SET tmp_search_condition = CONCAT(tmp_search_condition," and (n.c_user_id = '",p_userid,"'");
		SET size = get_split_string_total(userroleid,',');
		SET idx = 0;
		WHILE idx<size DO
			SET tmp_search_condition = CONCAT(tmp_search_condition," or find_in_set(get_split_string('",userroleid,"',',',",idx+1,"),replace(n.notice_role,'|',','))>0");
			SET idx = idx+1;
		END WHILE;
	END IF;
	
	
	SET tmp_search_condition=CONCAT(tmp_search_condition,")");
	IF p_dc_school_id IS NOT NULL AND p_dc_school_id>0 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and n.dc_school_id=",p_dc_school_id);
	END IF; 
	
	SET tmp_search_condition = CONCAT(tmp_search_condition," and (is_time=1 or (n.begin_time<NOW() AND n.end_time > NOW()))");
	SET tmp_sql = CONCAT(tmp_sql,"select ",tmp_search_column," from ",tmp_tbl_name," where " ,tmp_search_condition);
	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	
	SET tmp_sql=CONCAT(" select count(n.ref) into @totalcount from ",tmp_tbl_name," where ",tmp_search_condition);
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET totalNum = @totalcount;
    END$$

DELIMITER ;