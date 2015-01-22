DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_base_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_base_info_proc_update`(
				            p_user_id VARCHAR(50),
				            p_res_state INT,
				            p_user_type INT,
				            p_ref VARCHAR(50),
				            p_course_id VARCHAR(50),
				            p_res_name VARCHAR(1000),
				            p_res_remark VARCHAR(1000),
				            p_res_keyword VARCHAR(1000),
				            p_res_introduce VARCHAR(1000),
				            p_resource_id VARCHAR(50),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_resource_base_info set m_time=NOW()';
	
	
	
	IF p_res_state IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_STATE=",p_res_state);
	END IF;
	
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_TYPE=",p_user_type);
	END IF;
	
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID='",p_course_id,"'");
	END IF;
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_NAME='",p_res_name,"'");
	END IF;
	
	IF p_res_remark IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_REMARK='",p_res_remark,"'");
	END IF;
	
	IF p_res_keyword IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_KEYWORD='",p_res_keyword,"'");
	END IF;
	
	
	IF p_res_introduce IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RES_INTRODUCE='",p_res_introduce,"'");
	END IF;
	
	IF p_resource_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RESOURCE_ID='",p_resource_id,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
