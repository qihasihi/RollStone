DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_dept_user_all_proc_search`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_dept_user_all_proc_search`(p_deptid varchar(100),
						     out totalnum int
							)
BEGIN
	
END $$

DELIMITER ;
