DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activitysite_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activitysite_proc_search_split`(p_ref int,
							p_sitename varchar(600),
							p_sitecontain int,
							p_sitecontain2 int,
							p_current_page INT,
							p_page_size	INT,
							p_sort_column VARCHAR(50),
							OUT totalNum INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' at.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(50) DEFAULT 'at_site_info at';
	if p_ref is not null then 
		set tmp_search_condition = concat(tmp_search_condition," and at.ref=",p_ref);
	end if;
	if p_sitename is not null then
		set tmp_search_condition = concat(tmp_search_condition," and at.site_name like '%",p_sitename,"%'");
	end if;
	if p_sitecontain is not null and p_sitecontain2 is  null then
		SET tmp_search_condition = CONCAT(tmp_search_condition," and at.site_contain >= ",p_sitecontain);
	end if;
	if p_sitecontain is null and p_sitecontain2 is not null then
		set tmp_search_condition = CONCAT(tmp_search_condition," and at.site_contain <= ",p_sitecontain2);
	end if;
	if p_sitecontain is not null and p_sitecontain2 is not null then 
		SET tmp_search_condition = CONCAT(tmp_search_condition," and at.site_contain >= ",p_sitecontain," and at.site_contain <= ",p_sitecontain2);
	end if;
	
	set tmp_sql = concat(tmp_sql,"select ",tmp_search_column," from ",tmp_tbl_name," where " ,tmp_search_condition);
	
	
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
	
	
	SET tmp_sql=CONCAT(" select count(at.ref) into @totalcount from ",tmp_tbl_name," where ",tmp_search_condition);
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET totalNum = @totalcount;
END $$

DELIMITER ;
