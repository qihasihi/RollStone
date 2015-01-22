DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_year_proc_current_year`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_year_proc_current_year`(flag varchar(50))
BEGIN
	DECLARE tmp_sql VARCHAR(100) DEFAULT '';
	if flag is not null then
		SET tmp_sql="select * from class_year_info where e_time>NOW()";
	else 
		SET tmp_sql="select * from class_year_info where b_time<NOW() and e_time>NOW()";
	end if;
	
	SET @tmp_sql =tmp_sql;   
	PREPARE s1 FROM  @tmp_sql;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
