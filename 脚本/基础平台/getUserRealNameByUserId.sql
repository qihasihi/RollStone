DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `getUserRealNameByUserId`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `getUserRealNameByUserId`(p_user_id BIGINT)
BEGIN
	declare tmp_sql varchar(10000);
	set tmp_sql = CONCAT(' SELECT IFNULL(t.TEACHER_NAME,IFNULL(s.STU_NAME,u.USER_NAME)) realName FROM 
				user_info u LEFT JOIN teacher_info t ON u.ref=t.USER_ID
				LEFT JOIN student_info s ON s.USER_ID=u.ref
				WHERE 1=1 AND u.user_id=',p_user_id);
	set @tmp_sql = tmp_sql;
	prepare stmt from @tmp_sql;	
	execute stmt;
	
END $$

DELIMITER ;
