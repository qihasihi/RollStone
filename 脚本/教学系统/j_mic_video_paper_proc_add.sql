DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_mic_video_paper_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `j_mic_video_paper_proc_add`(
				            p_paper_id BIGINT,
				            p_ref INT,
				            p_mic_video_id BIGINT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO j_mic_video_paper (";
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAPER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_paper_id,",");
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ref,",");
	END IF;
	
	
	IF p_mic_video_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MIC_VIDEO_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_mic_video_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;