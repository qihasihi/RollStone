DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_by_username_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_by_username_split`(
					  p_username VARCHAR(150),
					  p_userid INT(50),
				          p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column VARCHAR(50),
					  p_dc_school_id int,
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " DISTINCT r.user_id,r.user_name,school_name,r.res_degree ";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 AND r.RES_STATUS=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_resource_info r '; 
	IF p_username IS NOT NULL THEN
	    SET tmp_search_condition=CONCAT(tmp_search_condition,"  AND r.user_name LIKE '",p_username,"%' ");
	END IF;
	IF p_userid IS NOT NULL THEN
	    SET tmp_search_condition=CONCAT(tmp_search_condition,"  AND r.user_id=",p_userid);
	END IF;
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," and ((r.RES_DEGREE=3 AND r.SHARE_STATUS=1 and r.dc_school_id=",p_dc_school_id," ) OR (r.RES_DEGREE <>3 AND r.res_id>0))")	;
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column," DESC ");
	END IF;
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT aa.*,
		(select count(distinct r.res_id) FROM rs_resource_info r where user_id=aa.user_id  AND((r.RES_DEGREE=3 AND r.SHARE_STATUS=1 and r.dc_school_id=",p_dc_school_id," ) OR (r.RES_DEGREE <>3 AND r.res_id>0))) resnum
		 FROM (",tmp_sql,") aa ");
     	
	SET @sql1=tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
