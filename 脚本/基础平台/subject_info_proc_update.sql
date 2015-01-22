DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `subject_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `subject_info_proc_update`(
					p_subject_id INT,
				          p_subject_name VARCHAR(1000),
				          p_lzx_subjectid int,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE subject_info set m_time=NOW()';
	
	
	IF p_subject_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SUBJECT_NAME='",p_subject_name,"'");
	END IF;
	
	IF p_lzx_subjectid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",lzx_subject_id=",p_lzx_subjectid);
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE SUBJECT_ID=",p_subject_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
