DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_column_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `user_column_list`(
				        p_userref VARCHAR(100))
BEGIN
	declare tmp_sql VARCHAR(5000) default '';
 
		
		SET tmp_sql=CONCAT("SELECT DISTINCT * FROM (
			SELECT col.* FROM column_info col,(
			 SELECT DISTINCT columnright_id,column_id
			  FROM j_user_columnright_info
			 WHERE USER_ID='",p_userref,"'
			 ) tmp WHERE col.column_id=tmp.column_id
		 ) tmp WHERE EXISTS(
		 SELECT DISTINCT uc.columnright_id FROM 
				j_user_columnright_info uc,
				j_columnright_pageright_info crpr,
				page_right_info pr
			WHERE uc.columnright_id=crpr.column_right_id 
				AND uc.column_id=crpr.column_id 				
				AND tmp.path=pr.PAGE_VALUE	
				AND user_id='",p_userref,"'
		)
		UNION ALL
		SELECT DISTINCT col.* FROM column_info col,(
			SELECT column_id FROM columnright_info WHERE columnright_id IN(
				SELECT DISTINCT column_right_id FROM j_role_columnright_info WHERE ROLE_ID IN (SELECT role_id FROM j_role_user WHERE USER_ID='",p_userref,"')				
			)
		) t WHERE t.column_id=col.column_id		
		");
 
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
