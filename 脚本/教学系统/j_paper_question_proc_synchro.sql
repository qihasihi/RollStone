DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_question_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_question_proc_synchro`(
				            p_paper_id BIGINT,
				            p_question_id BIGINT,
				            p_order_idx INT,
				            p_score float,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(20000);
	SET affect_row=1;
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM j_paper_question where paper_id=",p_paper_id," AND question_id=",p_question_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF @tmp_sumCount<1 THEN	
             
	     CALL j_paper_question_proc_add(
				            p_paper_id ,
				            p_question_id ,
				            p_order_idx ,
				            p_score ,
					    affect_row
							);
	END IF;
	
	
	
END $$

DELIMITER ;
