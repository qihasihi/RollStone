DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `appraise_logs_proc_usernum`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `appraise_logs_proc_usernum`(
				            p_user_id INT,
				            p_target_user_ref VARCHAR(50),
				            p_year_id INT,
							OUT affect_row int)
BEGIN
	set affect_row=0;
	
	select count(*) into affect_row from pj_appraise_logs_info pal 
	where pal.USER_ID=p_user_id
	and pal.TARGET_USER_REF=p_target_user_ref
	AND pal.YEAR_ID=p_year_id;
	
END $$

DELIMITER ;
