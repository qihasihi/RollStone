DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_question_option_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_question_option_proc_synchro`(
				            p_question_id bigint,
				            p_score INT,
				            p_content VARCHAR(4000),
				            p_is_right INT,
				            p_option_type VARCHAR(1000),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	
	
	CALL j_question_option_proc_add(p_question_id,
				            p_score,
				            p_content,
				            p_is_right,
				            p_option_type,
					 affect_row );
	
END $$

DELIMITER ;
