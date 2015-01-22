DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_dept_user_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_dept_user_proc_split`(
				          p_ref VARCHAR(150),
				          p_dept_id INT,
				          p_role_id INT,
				          p_user_id INT,
				          p_user_ref VARCHAR(150),
				          p_type_id INT,
				          p_otheruserref VARCHAR(150),
				          p_role_flag int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' DISTINCT d.dept_name, du.ref,du.role_id,du.dept_id,du.user_id userref,du.c_time,u.user_id,IFNULL(tea.TEACHER_NAME,u.USER_NAME) realname,u.HEAD_IMAGE ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'j_user_dept_info du '; 
	
	SET tmp_search_column=CONCAT(tmp_search_column,',(SELECT role_name FROM role_info r where r.role_id=du.role_id) rolename');	
	
	
	SET tmp_tbl_name="(SELECT du.* FROM j_user_dept_info du WHERE 1=1 ";	
	IF p_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and du.REF='",p_ref,"'");
	END IF;
	
	IF p_dept_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and du.DEPT_ID=",p_dept_id);
	END IF;
	
	IF p_role_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and du.ROLE_ID=",p_role_id);
	END IF;
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and du.USER_ID='",p_user_ref,"'");
	END IF;	
	
	IF p_role_flag IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and du.ROLE_ID IS NOT NULL ");
	END IF;	
	
	
	IF p_otheruserref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and du.DEPT_ID IN (");
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"SELECT DISTINCT du.dept_id FROM j_user_dept_info du ");
		SET tmp_tbl_name=CONCAT(tmp_tbl_name,"INNER JOIN dept_info d ON d.dept_id=du.dept_id ");
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," WHERE 1=1 ");
		IF p_type_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND d.TYPE_ID=",p_type_id);
		END IF;
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND du.user_id='",p_otheruserref,"')");
	END IF;
	set tmp_tbl_name=CONCAT(tmp_tbl_name,") du INNER JOIN(SELECT u.* FROM user_info u where 1=1 ");
	IF p_user_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.USER_ID=",p_user_id);  
	END IF;
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,") u ON u.ref=du.USER_ID INNER JOIN (SELECT d.* FROM dept_info d WHERE 1=1 ");
	IF p_type_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and d.TYPE_ID=",p_type_id);
	END IF;
	set tmp_tbl_name=CONCAT(tmp_tbl_name,") d ON d.dept_id=du.dept_id ");
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," LEFT JOIN teacher_info tea  ON tea.USER_ID=du.USER_ID  WHERE ",tmp_search_condition);	
	
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
