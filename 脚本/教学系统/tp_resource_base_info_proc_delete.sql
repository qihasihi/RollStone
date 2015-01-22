DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_base_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_base_info_proc_delete`(
			                    p_ref varchar(50),
				            p_user_id varchar(50),
				            p_course_id varchar(50),
				            p_resource_id varchar(50),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_resource_base_info where 1=1";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and COURSE_ID='",p_course_id,"'");
	END IF;
	
	IF p_resource_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RESOURCE_ID='",p_resource_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
