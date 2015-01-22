DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_class_user_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_class_user_proc_update`(
				          p_ref varchar(50),
				          p_user_id varchar(50),
				          p_class_id INT,
				          p_relation_type VARCHAR(1000),
				          p_sport_sex INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE j_class_user set m_time=NOW()';
	
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_ID=",p_class_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
	END IF;
	
	
	IF p_relation_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RELATION_TYPE='",p_relation_type,"'");
	END IF;
	
	IF p_sport_sex IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SPORT_SEX=",p_sport_sex);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
        IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF='",p_ref,"'");
	END IF;
	
	iF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
