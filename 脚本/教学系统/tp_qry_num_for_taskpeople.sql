DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_num_for_taskpeople`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_num_for_taskpeople`(p_groupid bigint,p_taskid bigint)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_groupid IS NOT NULL 
	   AND p_taskid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql,"SELECT COUNT(*) num FROM (");
	        SET tmp_sql = CONCAT(tmp_sql," SELECT * FROM tp_j_group_student g WHERE g.GROUP_ID=",p_groupid);
	        /*SET tmp_sql = CONCAT(tmp_sql," AND g.C_TIME<(SELECT allot.e_time FROM tp_task_allot_info allot ");
	        set tmp_sql = concat(tmp_sql," WHERE allot.user_type=2 AND allot.user_type_id= ",p_groupid," AND allot.task_id=",p_taskid," )) a");*/
	        SET tmp_sql = CONCAT(tmp_sql,")a");
	        
	        SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;
	END IF;
	
END $$

DELIMITER ;
