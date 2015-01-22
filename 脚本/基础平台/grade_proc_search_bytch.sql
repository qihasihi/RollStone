DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `grade_proc_search_bytch`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `grade_proc_search_bytch`(p_userid INT,
				          p_year VARCHAR(50),
					OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	IF p_userid IS NOT NULL and p_year is not null THEN
		SET tmp_sql=CONCAT("SELECT * FROM grade_info WHERE GRADE_VALUE IN (
			SELECT DISTINCT CLASS_GRADE FROM class_info c,j_class_user cu,user_info u 
			WHERE c.CLASS_ID=cu.CLASS_ID AND cu.USER_ID=u.REF 
			AND u.USER_ID=",p_userid," AND c.YEAR='",p_year,"')");	
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
