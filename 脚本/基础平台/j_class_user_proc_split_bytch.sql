DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_class_user_proc_split_bytch`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_class_user_proc_split_bytch`(p_user_id VARCHAR(50),
				          p_year VARCHAR(50),
				          OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET sumCount=0;
	if p_user_id is not null and p_year is not null then
		SET tmp_sql=CONCAT("SELECT c.* FROM (
			SELECT cu.CLASS_ID FROM j_class_user cu
			WHERE cu.relation_type = '»ŒøŒ¿œ ¶'
			AND cu.USER_ID='",p_user_id,"' 
			GROUP BY cu.CLASS_ID)aa
			INNER JOIN class_info c ON aa.CLASS_ID=c.CLASS_ID 
			WHERE c.YEAR='",p_year,"'");	
	end if;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	SET sumCount=1;
END $$

DELIMITER ;
