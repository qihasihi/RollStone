DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_statices_getmyrank`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_statices_getmyrank`(
				          p_user_id INT,				         
				          p_current_page INT(10),
					  p_page_size INT(10),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(40000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' res_id,res_name,r.FILE_SUFFIXNAME,
					IF(NET_CLICKS<1,CLICKS,NET_CLICKS) clicks,
					IF(NET_PRAISENUM<1,r.PRAISENUM,NET_PRAISENUM) praisenum,
					IF(NET_RECOMENDNUM<1,r.RECOMENDNUM,NET_RECOMENDNUM) recomendnum,
					IF(NET_STORENUM<1,r.storenum,NET_STORENUM) storenum,
					IF(NET_DOWNLOADNUM<1,r.DOWNLOADNUM,NET_DOWNLOADNUM) downloadnum 
			';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' r.RES_STATUS<>3 ';  
	DECLARE tmp_tbl_name VARCHAR(50) DEFAULT 'rs_resource_info r'; 
	IF p_user_id IS NOT NULL THEN
	  SET tmp_search_condition=CONCAT(tmp_search_condition,' AND r.user_id=',p_user_id);
	END IF;
	 SET tmp_sql=CONCAT("SELECT * FROM (SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition,")t");
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY clicks DESC,praisenum DESC,recomendnum DESC,storenum DESC,downloadnum DESC ");
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(distinct r.res_id) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
