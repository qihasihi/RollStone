DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_right_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_right_info_proc_update`(
				         p_ref VARCHAR(1000),
					 p_c_user_id VARCHAR(100),
					  p_right_type INT,
					    p_right_roletype INT,
					     p_right_subject VARCHAR(1000),
				            p_right_user_ref VARCHAR(1000),
				            res_id VARCHAR(100),
				                subjectid VARCHAR(100),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE rs_resource_right_info set m_time=NOW()';
	
	
	
	IF p_right_user_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RIGHT_USER_REF='",p_right_user_ref,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID='",p_c_user_id,"'");
	END IF;
	IF subjectid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",subjectid='",subjectid,"'");
	END IF;
	
	IF p_right_subject IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RIGHT_SUBJECT='",p_right_subject,"'");
	END IF;
	
	IF res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",res_id='",res_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF='",p_ref,"'");
	END IF;
	
	IF p_right_roletype IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RIGHT_ROLETYPE=",p_right_roletype);
	END IF;
	
	IF p_right_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RIGHT_TYPE=",p_right_type);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
