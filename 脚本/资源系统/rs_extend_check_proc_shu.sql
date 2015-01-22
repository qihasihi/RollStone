DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_extend_check_proc_shu`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_extend_check_proc_shu`(p_root_id VARCHAR(1000))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql=" SELECT ev.VALUE_ID ,(SELECT DEPEND_EXTEND_VALUE_ID FROM rs_extend_info WHERE EXTEND_ID=ev.EXTEND_ID) EXTEND_ID";
	 SET tmp_sql=CONCAT(tmp_sql,",ev.VALUE_NAME FROM rs_extend_value_info ev WHERE 1=1 ");	
	IF p_root_id IS NOT NULL THEN
	  SET tmp_sql=CONCAT(tmp_sql," AND  FIND_IN_SET(extend_id,getChildList_extendValue_ALL('",p_root_id,"'))>0");			  
	END IF;			
	SET tmp_sql=CONCAT(tmp_sql,"  ORDER BY C_TIME ");
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
