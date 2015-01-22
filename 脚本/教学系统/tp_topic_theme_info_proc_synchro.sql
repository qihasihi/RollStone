DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_theme_info_proc_synchro`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_topic_theme_info_proc_synchro`(
						  p_view_count INT,
						  p_c_user_id INT,
						  p_comment_user_id INT,
						  p_cloud_status INT,
						  p_is_essence INT,
						  p_theme_title VARCHAR(1000),
						  p_comment_m_time DATETIME,
						  p_comment_title VARCHAR(1000),						  
						    p_theme_id BIGINT,
						    p_course_id BIGINT,
						    p_is_top INT,				            				          
						    p_topic_id BIGINT, 			            
				            	 p_imattach VARCHAR(5000),
							    p_attachtype INT,
							    source_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_topic_theme_info where theme_id=",p_theme_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF @tmp_sumCount>0 THEN
		CALL tp_topic_theme_info_proc_update(
				           p_view_count ,
						  p_c_user_id ,
						  p_comment_user_id ,
						  p_cloud_status ,
						  p_is_essence ,
						  p_theme_title ,
						  p_comment_m_time ,
						  p_comment_title ,						  
						    p_theme_id ,
						    p_course_id ,
						    p_is_top ,				            				          
						    p_topic_id ,
						    NULL,
						    NULL,NULL, 
						    p_imattach,
						    p_attachtype,
						    source_id,
				           affect_row 
				          );
	ELSE
		CALL tp_topic_theme_info_proc_add(
						  p_view_count ,
						  p_c_user_id ,
						  p_comment_user_id ,
						  p_cloud_status ,
						  p_is_essence ,
						  p_theme_title,
						  p_comment_m_time ,
						  p_comment_title ,						  
						    p_theme_id ,
						    p_course_id ,
						    p_is_top ,				            				          
						    p_topic_id , 
						    NULL,
						    NULL,	
						      p_imattach,
						    p_attachtype,
						    source_id,	
						    0,		            
						affect_row 
							);
	END IF;
	
    END$$

DELIMITER ;