DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_paper_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_paper_proc_add`(
				            p_paper_id BIGINT,
				            p_course_id BIGINT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_j_course_paper (";
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_paper_id,",");
	END IF;
	
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
