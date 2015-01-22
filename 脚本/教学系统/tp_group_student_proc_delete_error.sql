DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_student_proc_delete_error`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_student_proc_delete_error`(
				            p_user_id VARCHAR(1000),
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	IF p_user_id IS NOT NULL THEN
		DELETE FROM tp_group_student WHERE ref IN 
		(SELECT a.ref FROM (
			SELECT gs.ref FROM tp_group_student gs,tp_group_info g 
			WHERE gs.group_id=g.group_id 
			AND gs.user_id=p_user_id
			AND g.CLASS_ID NOT IN (
				SELECT cu.class_id FROM j_class_user cu WHERE cu.USER_ID=p_user_id
				)
			)a
		);
	END IF;
	SET affect_row = 1;
	
END $$

DELIMITER ;
