DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `comment_info_proc_support_or_oppose`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `comment_info_proc_support_or_oppose`(
				          
				          p_comment_id varchar(50),
				          p_type INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	if p_type=1 then
		update comment_info set SUPPORT=SUPPORT+1 where COMMENT_ID=p_comment_id;
	else
		UPDATE comment_info SET OPPOSE=OPPOSE+1 WHERE COMMENT_ID=p_comment_id;
	end if;
	
	SET affect_row = 1;
END $$

DELIMITER ;
