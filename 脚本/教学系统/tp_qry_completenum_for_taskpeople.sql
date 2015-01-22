DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_completenum_for_taskpeople`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_completenum_for_taskpeople`(p_groupid BIGINT,p_taskid BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_groupid IS NOT NULL 
	   AND p_taskid IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql,"SELECT COUNT(*) num FROM (SELECT * FROM tp_task_performance per ");
	        SET tmp_sql = CONCAT(tmp_sql," WHERE per.task_id=",p_taskid);
	        SET tmp_sql = CONCAT(tmp_sql," AND user_id IN(SELECT u.ref FROM  ");
	        SET tmp_sql = CONCAT(tmp_sql," user_info u LEFT JOIN tp_j_group_student g ON u.USER_ID=g.USER_ID ");
	        SET tmp_sql = CONCAT(tmp_sql," WHERE g.GROUP_ID=",p_groupid);
	        /*SET tmp_sql = CONCAT(tmp_sql," AND g.C_TIME<(SELECT allot.e_time ");
	        SET tmp_sql = CONCAT(tmp_sql," FROM tp_task_allot_info allot ");
	        SET tmp_sql = CONCAT(tmp_sql," WHERE allot.user_type=2 AND allot.user_type_id= ",p_groupid," AND allot.task_id=",p_taskid,"))) a");*/
	        SET tmp_sql = CONCAT(tmp_sql,"))a");
	        
	        SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;
	END IF;
END $$

DELIMITER ;
