DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_column_right_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `user_column_right_list`(
					p_column_id VARCHAR(100),
				        p_userref VARCHAR(100))
BEGIN
	declare tmp_sql VARCHAR(5000) default '';
 
 
		
		SET tmp_sql=CONCAT("SELECT * FROM page_right_info 
				WHERE column_id='",p_column_id,"' AND 
				page_right_id NOT IN(
					SELECT page_right_id FROM j_columnright_pageright_info cright WHERE column_id='",p_column_id,"'
					AND column_right_id IN (
						SELECT DISTINCT columnright_id FROM j_user_columnright_info ucr
						WHERE column_id='",p_column_id,"' AND user_id='",p_userref,"'
						UNION ALL
						SELECT DISTINCT columnright_id FROM columnright_info WHERE columnright_id IN(
						SELECT column_right_id FROM j_role_columnright_info 
						WHERE role_id IN (SELECT role_id from j_role_user where user_id='",p_userref,"')
						) AND column_id='",p_column_id,"'
					)
				)");			 
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
