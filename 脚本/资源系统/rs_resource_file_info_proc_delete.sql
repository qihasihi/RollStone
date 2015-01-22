DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_file_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_file_info_proc_delete`(
				            p_file_size VARCHAR(1000),
				            p_ref VARCHAR(1000),
				            p_file_name VARCHAR(1000),
				            p_school_id INT,
				            p_res_id VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from rs_resource_file_info where 1=1";
	
	IF p_file_size IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and FILE_SIZE='",p_file_size,"'");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	IF p_file_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and FILE_NAME='",p_file_name,"'");
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and SCHOOL_ID=",p_school_id);
	END IF;
	
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and RES_ID='",p_res_id,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
