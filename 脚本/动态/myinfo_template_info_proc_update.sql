DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `myinfo_template_info_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `myinfo_template_info_proc_update`(
				          p_template_name VARCHAR(1000),
				          p_template_id INT,
				          p_template_searator VARCHAR(1000),
				          p_enable INT,
				          p_template_content VARCHAR(1000),
				          p_template_url VARCHAR(1000),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE myinfo_template_info set m_time=NOW()';
	
	
	IF p_template_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TEMPLATE_NAME='",p_template_name,"'");
	END IF;
	
	
	IF p_template_searator IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TEMPLATE_SEARATOR='",p_template_searator,"'");
	END IF;
	
	IF p_enable IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",ENABLE=",p_enable);
	END IF;
	
	IF p_template_content IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TEMPLATE_CONTENT='",p_template_content,"'");
	END IF;
	
	IF p_template_url IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TEMPLATE_URL='",p_template_url,"'");
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
		
	IF p_template_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND TEMPLATE_ID=",p_template_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
