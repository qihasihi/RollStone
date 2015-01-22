DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_allot_yx_by_tkid_stuid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_allot_yx_by_tkid_stuid`(
				            p_task_id LONG,
				            p_user_id LONG
						)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) tkCount FROM (
		SELECT 
		user_type_id,user_type,(SELECT COUNT(*) FROM tp_j_group_student gs WHERE gs.group_id=tpa.`user_type_id` AND gs.user_id=",p_user_id,") ishas,tpa.b_time,tpa.e_time
		 FROM tp_task_allot_info tpa WHERE task_id=",p_task_id," AND user_type=2
		 UNION
		SELECT 
		user_type_id,user_type,(SELECT COUNT(*) FROM j_class_user cu WHERE cu.class_id=tpa.`user_type_id` AND user_id=(SELECT ref FROM user_info u WHERE u.user_id=",p_user_id,")) ishas
		,tpa.b_time,tpa.e_time
		 FROM tp_task_allot_info tpa WHERE task_id=",p_task_id," AND user_type=0 
		 ) t WHERE NOW() BETWEEN b_time AND e_time AND ishas>0");
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	
END $$

DELIMITER ;
