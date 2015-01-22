DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_resource_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_resource_info_proc_update`(
				          
				          p_ref bigint,
				          p_res_id bigINT,
				          p_course_id bigINT,
				          p_res_type int,
				          p_localstatus int,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_j_course_resource_info set operate_time=NOW() ';
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_ID=",p_res_id);
	END IF;
	
	IF p_res_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RESOURCE_TYPE=",p_res_type);
	END IF;
	
	IF p_localstatus IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",LOCAL_STATUS=",p_localstatus);
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
