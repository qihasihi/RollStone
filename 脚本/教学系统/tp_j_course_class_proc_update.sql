DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_class_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_class_proc_update`(
				          p_course_id INT,
				          p_class_id INT,
				          p_ref INT,
				          p_begin_time DATETIME,
				          p_class_type INT,
				          p_user_id INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_j_course_class set m_time=NOW()';
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",COURSE_ID=",p_course_id);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_ID=",p_class_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF=",p_ref);
	END IF;
	
	IF p_begin_time IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",BEGIN_TIME=",p_begin_time);
	END IF;
	
	IF p_class_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_TYPE=",p_class_type);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
