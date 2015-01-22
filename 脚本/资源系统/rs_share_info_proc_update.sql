DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_share_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_share_info_proc_update`(
				          p_user_id INT,
				          p_ref VARCHAR(1000),
				          p_res_id VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE rs_share_info set m_time=NOW()';
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_REF=",p_user_id);
	END IF;
	IF p_state IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",STATE=",p_state);		
	END IF;
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_ID='",p_res_id,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND REF='",p_ref,"'");
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
