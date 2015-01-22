DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `subject_user_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `subject_user_proc_delete`(
				            p_ref int,
					          p_user_id varchar(50),
						  p_subject_id int,
						  out affect_row int)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	IF p_ref IS NULL AND p_user_id IS NULL AND p_subject_id IS NULL THEN
		SET affect_row = 0;
	ELSE
		SET tmp_sql="delete from j_user_subject where 1=1";
		
		IF p_ref IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and ref=",p_ref);
		END IF;
		
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and user_id='",p_user_id,"'");
		END IF;
		
		IF p_subject_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and subject_id=",p_subject_id);
		END IF;
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		
		SET affect_row = 1;
	END IF;
END $$

DELIMITER ;
