DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_operate_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_operate_info_proc_delete`(
				            p_operate_type INT,
				            p_course_id BIGINT,				           
				            p_ref BIGINT,
				            p_data_type INT,
				            p_c_user_id INT,
				            target_id BIGINT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_operate_info where 1=1";
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID=",p_course_id);
	END IF;
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and OPERATE_TYPE=",p_operate_type);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_data_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and DATA_TYPE=",p_data_type);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and C_USER_ID=",p_c_user_id);
	END IF;
	
	
	IF target_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TARGET_ID=",target_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
