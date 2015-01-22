DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `page_right_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `page_right_proc_update`(
					p_ref VARCHAR(100),
		                           p_page_right_id INT,
		                           p_page_value VARCHAR(1000),
		                           p_page_name VARCHAR(1000),
		                           p_parent_id INT,
				           p_page_right_type INT,
				             p_remark VARCHAR(4000),
				             p_columnid	VARCHAR(100),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE page_right_info set m_time=NOW()';
	
	IF p_page_right_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAGE_RIGHT_TYPE=",p_page_right_type);
	END IF;
	
	IF p_page_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAGE_NAME='",p_page_name,"'");
	END IF;
	
	IF p_page_value IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAGE_VALUE='",p_page_value,"'");
	END IF;
	
	IF p_parent_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PAGERIGHT_PARENT_ID=",p_parent_id);
	END IF;
	IF p_remark IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",remark='",p_remark,"'");
	END IF;
	
	IF p_columnid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",column_id='",p_columnid,"'");
	END IF;
	
	IF p_page_right_id IS NOT NULL THEN
		SET tmp_sql =CONCAT(tmp_sql, ",PAGE_RIGHT_ID=",p_page_right_id);  
	END IF;
	set tmp_sql=CONCAT(tmp_sql," WHERE 1=1 ");
	if p_ref IS NOT NULL THEN
		set tmp_sql=CONCAT(tmp_sql," AND ref='",p_ref,"'");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
