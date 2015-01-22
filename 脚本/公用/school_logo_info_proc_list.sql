DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_logo_info_proc_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_logo_info_proc_list`(p_school_id int)
BEGIN
	declare tmp_sql varchar(2000) default '';
	if p_school_id is not null then
		set tmp_sql = concat(tmp_sql,"select * from school_logo_info where school_id=",p_school_id);
		SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;   
		DEALLOCATE PREPARE s1;  
	end if;
END $$

DELIMITER ;
