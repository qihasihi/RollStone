DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `db_object_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `db_object_info_proc_update`(
				          p_db_object_id INT,
				          p_sort_id INT,
				          p_c_user_id VARCHAR(1000),
				          p_desc VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE db_object_info set c_time=c_time';
	
	IF p_db_object_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DB_OBJECT_ID=",p_db_object_id);
	END IF;
	
	IF p_sort_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SORT_ID=",p_sort_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	
	IF p_desc IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",DESC='",p_desc,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
