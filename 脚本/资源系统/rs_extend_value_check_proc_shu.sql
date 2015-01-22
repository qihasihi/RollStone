DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_extend_value_check_proc_shu`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_extend_value_check_proc_shu`(
				          p_root_id VARCHAR(1000)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT '  ev.value_id,ev.extend_id,ev.VALUE_NAME ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(1000) DEFAULT 'rs_extend_value_info ev'; 
	
	IF p_root_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND FIND_IN_SET(extend_ID, getChildList_extendValue_ALL('",p_root_id,"'))");	
	END IF;	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1; 
	
END $$

DELIMITER ;
