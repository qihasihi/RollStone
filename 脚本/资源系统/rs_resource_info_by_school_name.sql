DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_by_school_name`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_by_school_name`(
					  p_school_name VARCHAR(150),
					  p_iseq INT,     
					  p_current_page INT,					  
					  p_page_size INT,
					  OUT  sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " DISTINCT school_name,user_id,user_name,r.RES_DEGREE  ";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 AND r.RES_STATUS=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_resource_info r '; 
	
	IF p_school_name IS NOT NULL THEN
	   IF p_iseq =1 THEN
	        SET tmp_search_condition=CONCAT(tmp_search_condition,"  AND r.school_name='",p_school_name,"'");	
	      ELSE
	        SET tmp_search_condition=CONCAT(tmp_search_condition,"  AND r.school_name LIKE '",p_school_name,"%' ");	
	   END IF;	    
	END IF;
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY  r.user_name ASC ");	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	SET tmp_sql=CONCAT("SELECT aa.*,
		(select count(*) FROM rs_resource_info r where user_id=aa.user_id AND r.RES_STATUS<>3 AND  ((r.RES_DEGREE=3 AND r.SHARE_STATUS=1) OR (r.RES_DEGREE <>3))) resnum
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
