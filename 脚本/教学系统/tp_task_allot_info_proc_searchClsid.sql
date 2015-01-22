DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_allot_info_proc_searchClsid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_allot_info_proc_searchClsid`(
						p_user_id BIGINT,
						p_task_id BIGINT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	set tmp_sql=CONCAT('
	SELECT * FROM (
		SELECT 
		(
		CASE allot.user_type WHEN 0 THEN allot.`user_type_id` 
				WHEN 2 THEN (
					SELECT DISTINCT class_id FROM `tp_j_group_student` gs,tp_group_info g 
					WHERE gs.group_id=g.group_id AND gs.user_id=',p_user_id,' AND allot.`user_type_id`=g.group_id) 
			      
				END
		) class_id FROM tp_task_allot_info allot
		WHERE allot.task_id=',p_task_id,'
		AND (EXISTS(
					SELECT DISTINCT class_id FROM `tp_j_group_student` gs,tp_group_info g 
							WHERE gs.group_id=g.group_id AND gs.user_id=',p_user_id,' AND g.class_id=allot.user_type_id
			)OR
			NOT EXISTS
			(SELECT  DISTINCT class_id FROM `tp_j_group_student` gs,tp_group_info g 
							WHERE gs.group_id=g.group_id AND g.`CLASS_ID`=allot.user_type)
			
				)
	) t WHERE t.class_id IS NOT NULL 
			LIMIT 0,1	
		');
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
END $$

DELIMITER ;
