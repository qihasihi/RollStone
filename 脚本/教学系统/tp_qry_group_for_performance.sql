DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_group_for_performance`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_group_for_performance`(p_groupid bigint)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_groupid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql,"SELECT * ");
	        SET tmp_sql = CONCAT(tmp_sql," FROM tp_task_allot_info allot ");
	        SET tmp_sql = CONCAT(tmp_sql," WHERE allot.e_time<NOW()  ");
	        SET tmp_sql = CONCAT(tmp_sql," AND allot.user_type=2 ");
	        SET tmp_sql = CONCAT(tmp_sql," AND allot.user_type_id=",p_groupid);
	        
	        SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;
	END IF;
END $$

DELIMITER ;
