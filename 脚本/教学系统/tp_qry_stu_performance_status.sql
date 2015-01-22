DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_performance_status`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_performance_status`(
					  p_task_id	VARCHAR(50),
					  p_user_id	VARCHAR(50))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql='SELECT tp.USER_ID,tp.TASK_ID,tp.TASK_TYPE FROM (
                   SELECT DISTINCT tp.USER_ID,tp.TASK_ID,tp.TASK_TYPE,CRETERIA_TYPE 
                     FROM tp_task_performance tp) tp WHERE 1=1 ';
        SET tmp_sql=concat(tmp_sql," AND tp.USER_ID='",p_user_id,"'");
        SET tmp_sql=CONCAT(tmp_sql," AND tp.TASK_ID='",p_task_id,"'");
         
        SET tmp_sql=CONCAT(tmp_sql,"   GROUP BY tp.USER_ID,tp.TASK_ID,tp.TASK_TYPE
           HAVING COUNT(*)=(SELECT COUNT(*)  FROM tp_task_complete_criteria 
                             WHERE TASK_ID=tp.TASK_ID)");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
