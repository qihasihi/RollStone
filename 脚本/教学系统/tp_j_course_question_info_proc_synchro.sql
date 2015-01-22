DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_question_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_question_info_proc_synchro`(						
						p_question_id bigint,
						p_course_id bigint,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	set affect_row=1;
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_j_course_question_info where question_id=",p_question_id," AND course_id=",p_course_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_sumCount <1 THEN		
		CALL tp_j_course_question_info_proc_add(
						null,
						p_question_id ,
						p_course_id ,
						affect_row);		          
	END IF;	
	
END $$

DELIMITER ;
