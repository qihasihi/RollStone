DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `teach_version_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teach_version_info_proc_synchro`(
				            p_version_name VARCHAR(1000),
				            p_version_id INT,
				            p_remark VARCHAR(1000),
				            p_c_user_id VARCHAR(1000),
				            p_abbreviation VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM teach_version_info where version_id=",p_version_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_sumCount >0 THEN
		CALL teach_version_info_proc_update(
				          p_version_name ,
				          p_version_id ,
				          p_remark ,
				          p_c_user_id ,
				          p_abbreviation,
				           affect_row 
				          );
	  ELSE
		CALL teach_version_info_proc_add(
				            p_version_name ,
				            p_version_id ,
				            p_remark ,
				            p_c_user_id ,
				            p_abbreviation,
					     affect_row 
							);
	END IF;
	
    END$$

DELIMITER ;