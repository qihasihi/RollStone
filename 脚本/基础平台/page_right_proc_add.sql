DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `page_right_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `page_right_proc_add`(
				            p_ref VARCHAR(1000),
				            p_page_value VARCHAR(1000),
				            p_page_name VARCHAR(1000),
				            p_parent_id INT,
				            p_page_right_type INT,
				            p_remark VARCHAR(4000),
				            p_columnid	VARCHAR(100),
					    OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO page_right_info (";
	
	IF p_page_right_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAGE_RIGHT_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_page_right_type,",");
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"',");
	else
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REF,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"UUID(),");
	END IF;
	
	
	IF p_page_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAGE_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_page_name,"',");
	END IF;
	
	IF p_page_value IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAGE_VALUE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_page_value,"',");
	END IF;
	
	IF p_parent_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PAGERIGHT_PARENT_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_parent_id,",");
	END IF;
	IF p_remark IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"REMARK,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_remark,"',");
	END IF;
	IF p_columnid IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"column_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_columnid,"',");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"C_TIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
