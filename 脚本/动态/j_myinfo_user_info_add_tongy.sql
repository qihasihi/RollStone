DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_myinfo_user_info_add_tongy`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_myinfo_user_info_add_tongy`(p_template_id INt
							,p_user_ref VARCHAR(1000),
							p_msg_id INT,
							p_msg_name VARCHAR(150),
							p_operate_userref VARCHAR(1000),
							p_my_data VARCHAR(5000),
							OUT affect_row INT
							)
BEGIN	
    	
	SET @affect_row=0;
        CALL j_myinfo_user_info_proc_add(p_msg_name,p_operate_userref,p_msg_id,p_user_ref,p_template_id,p_my_data,@affect_row); 
        SET affect_row=@affect_row;
END $$

DELIMITER ;
