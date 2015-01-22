DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_suggest_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_suggest_info_proc_add`(
					    p_task_id bigint,
				            p_user_id varchar(50),
				            p_is_anonymous INT,
				            p_suggest_content VARCHAR(1000),
				            p_course_id bigint,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_task_suggest_info (";
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_user_id,"',");
	END IF;
	
	IF p_is_anonymous IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IS_ANONYMOUS,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_is_anonymous,",");
	END IF;
	
	IF p_task_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TASK_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_task_id,",");
	END IF;
	
	
	IF p_suggest_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SUGGEST_CONTENT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_suggest_content,"',");
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"COURSE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
