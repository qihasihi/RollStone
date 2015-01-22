DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_collect_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_collect_proc_update`(
				          p_user_id INT,
				          p_collect_id INT,
				          p_res_detail_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_resource_collect set m_time=NOW()';
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	IF p_collect_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COLLECT_ID=",p_collect_id);
	END IF;
	
	
	IF p_res_detail_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_DETAIL_ID=",p_res_detail_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
