DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `stu_paper_times_info_proc_search`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `stu_paper_times_info_proc_search`(
				            p_task_id BIGINT,
				            p_user_id BIGINT,
				            p_paper_id BIGINT,				           			          
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_condition VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	SET tmp_sql="SELECT * FROM stu_paper_times_info where 1=1";
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND task_id=",p_task_id);		
	END IF;	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND user_id=",p_user_id);		
	END IF;	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND paper_id=",p_paper_id);		
	END IF;	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
    END$$

DELIMITER ;