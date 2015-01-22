DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `term_proc_search_available_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `term_proc_search_available_list`()
BEGIN
	DECLARE tmp_sql VARCHAR(100) DEFAULT '';
	SET tmp_sql="select * from term_info where semester_end_date>NOW()";
	SET @tmp_sql =tmp_sql;   
	PREPARE s1 FROM  @tmp_sql;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
