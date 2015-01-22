DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_topic_info_proc_synchro`(
					    p_order_idx INT,
				            p_topic_keyword VARCHAR(1000),
				            p_status INT,
				              p_topic_title VARCHAR(1000),
				              p_c_user_id INT,
				              p_topic_content VARCHAR(1000),
				               p_course_id BIGINT,
				            p_cloud_status INT,
				            p_topic_id BIGINT,
				            OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(20000);
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_topic_info where topic_id=",p_topic_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF @tmp_sumCount>0 THEN
		CALL tp_topic_info_proc_update(
				          p_order_idx,
				            p_topic_keyword,
				            p_status ,
				              p_topic_title,
				              p_c_user_id,
				              p_topic_content,
				               p_course_id,
				            p_cloud_status,
				            p_topic_id,
				          affect_row 
				          );
	    ELSE
		CALL tp_topic_info_proc_add(
					    p_order_idx,
				            p_topic_keyword,
				            p_status,
				              p_topic_title,
				              p_c_user_id,
				              p_topic_content,
				               p_course_id,
				            p_cloud_status,
				            p_topic_id,
				             affect_row);
	END IF;
	
END $$

DELIMITER ;
