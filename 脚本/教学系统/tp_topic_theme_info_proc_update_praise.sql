DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_theme_info_proc_update_praise`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_topic_theme_info_proc_update_praise`(				       						  
						    p_theme_id BIGINT,						 
						    p_type BIGINT,							    
						OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_topic_theme_info set ';
	IF p_type=1 THEN
		SET tmp_sql=CONCAT(tmp_sql," praise_count=praise_count+1");
	END IF;
	IF p_type=2 THEN
		SET tmp_sql=CONCAT(tmp_sql," praise_count=praise_count-1");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	SET tmp_sql=CONCAT(tmp_sql," AND THEME_ID=",p_theme_id);
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
