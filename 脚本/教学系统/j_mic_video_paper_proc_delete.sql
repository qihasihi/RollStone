DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_mic_video_paper_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_mic_video_paper_proc_delete`(
				            p_paper_id BIGINT,
				            p_ref INT,
				            p_mic_video_id BIGINT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from j_mic_video_paper where 1=1";
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PAPER_ID=",p_paper_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	
	IF p_mic_video_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and MIC_VIDEO_ID=",p_mic_video_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
