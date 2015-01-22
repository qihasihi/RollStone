DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `question_info_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `question_info_proc_delete`(
				            p_question_id bigint,
				            p_paper_type_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from question_info where 1=1";
	
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and QUESTION_ID=",p_question_id);
	END IF;
	
	
	IF p_paper_type_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and PAPER_TYPE_ID=",p_paper_type_id);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
