DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `roleuser_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `roleuser_proc_search_split`(
				          p_ref VARCHAR(50),
				          p_user_ref VARCHAR(50),
				          p_user_id INT,
				          p_user_name VARCHAR(50),
				          p_user_realname VARCHAR(50),
				          p_roleid_str varchar(500),
				          p_grade_id int,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	declare	in_sql varchar(500) default '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' ru.*,r.*,u.user_id ruserid,u.user_name,g.grade_name';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(500) DEFAULT ' j_role_user ru inner join role_info r on r.role_id=ru.role_id inner join user_info u on u.ref=ru.user_id LEFT JOIN grade_info g ON ru.grade_id=g.grade_id '; 
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ru.REF='",p_ref,"'");
	END IF;
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ru.USER_ID='",p_user_ref,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID=",p_user_id);
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_NAME='",p_user_name,"'");
	END IF;
	
	IF p_user_realname IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_NAME like '%",p_user_realname,"%'");
	END IF;
	
	
	IF p_roleid_str IS NOT NULL THEN
		set in_sql=get_split_insql(p_roleid_str,',');
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ru.ROLE_ID ",in_sql);
	END IF;
	
	IF p_grade_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and ru.grade_id=",p_grade_id);
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
