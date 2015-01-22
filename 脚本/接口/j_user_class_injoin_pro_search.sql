DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_user_class_injoin_pro_search`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `j_user_class_injoin_pro_search`(
				            p_user_id INT,
				            p_class_id INT,
				            p_dc_school_id INT
				          )
BEGIN
	SELECT * FROM j_user_class_injoin uc WHERE user_id=p_user_id AND class_id=p_class_id AND dc_school_id=p_dc_school_id
	ORDER BY uc.c_time DESC;	
 END$$

DELIMITER ;