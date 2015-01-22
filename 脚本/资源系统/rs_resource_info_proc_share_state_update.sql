DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_share_state_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_info_proc_share_state_update`(
					  p_updatestate INT,
				          p_sharestatus INT,	
				          p_resdegree INt,
				          p_netsharestatus INT,				        
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE rs_resource_info set m_time=NOW()';
	
	
	
	IF p_updatestate IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",NET_SHARE_STATUS=",p_updatestate);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	SET tmp_sql=CONCAT(tmp_sql," AND SHARE_STATUS=",p_sharestatus," AND RES_DEGREE=",p_resdegree," AND NET_SHARE_STATUS=",p_netsharestatus);
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
