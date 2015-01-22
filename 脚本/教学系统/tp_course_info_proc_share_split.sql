DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_share_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_share_split`(				        
				          p_share_type INT,
				          p_course_level INT,
				          p_cloud_status INT,
				          p_local_status INT,				       
					  p_current_page INT(10),
					  p_page_size INT(10),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_condition VARCHAR(20000) DEFAULT ' 1=1 ';
	DECLARE tmp_tbl_name VARCHAR(1000) DEFAULT ' tp_course_info c ';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' c.* '; 
	
	IF p_share_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.SHARE_TYPE=",p_share_type);
	END IF;
	IF p_course_level IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.COURSE_LEVEL=",p_course_level);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.CLOUD_STATUS =",p_cloud_status);
		ELSE 
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.CLOUD_STATUS IS NULL ");
	END IF;
	IF p_local_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c.LOCAL_STATUS=",p_local_status);
	END IF;		
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition," GROUP BY c.COURSE_ID ");	
	
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY  c.course_id asc ");
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
		
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(t.course_id) INTO @tmp_sumCount FROM (",tmp_sql,")t");
	SET @tmp_sql2=tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2  ;
	EXECUTE stmt2;
	DEALLOCATE PREPARE stmt2;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
