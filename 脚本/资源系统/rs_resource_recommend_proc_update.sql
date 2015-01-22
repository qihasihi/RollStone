DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_recommend_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_recommend_proc_update`(
				          p_end_time DATE,
				          p_ref VARCHAR(1000),
				          p_res_id bigint,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE rs_resource_recommend set m_time=NOW()';
	
	IF p_end_time IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",END_TIME=",p_end_time);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF='",p_ref,"'");
	END IF;
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_ID=",p_res_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
