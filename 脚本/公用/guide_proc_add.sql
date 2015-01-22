DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `guide_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `guide_proc_add`(
					p_ref BIGINT,
					p_op_table VARCHAR(100),
					p_op_user BIGINT,
					p_op_type INT,     
					 out affect_row int
					)
BEGIN
	
        DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
        DECLARE tmp_title VARCHAR(100) DEFAULT '';	
		DECLARE tmp_templateid INT;	
		 
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	
	SET affect_row:=0;
	if p_ref is not null then
		set tmp_column_sql = concat(tmp_column_sql,"ref,");
		set tmp_value_sql = concat(tmp_value_sql,"'",p_ref,"',");
	end if; 
	IF p_op_table IS NOT NULL THEN
		set tmp_column_sql = CONCAT(tmp_column_sql,"op_table,");
		set tmp_value_sql = CONCAT(tmp_value_sql,"'",p_op_table,"',");
	END IF; 
	IF p_op_user IS NOT NULL THEN
		set tmp_column_sql = CONCAT(tmp_column_sql,"op_user,");
		set tmp_value_sql = CONCAT(tmp_value_sql,p_op_user,",");
	END IF; 
	IF p_op_type IS NOT NULL THEN
		set tmp_column_sql = CONCAT(tmp_column_sql,"op_type,");
		set tmp_value_sql = CONCAT(tmp_value_sql,p_op_type,",");
	END IF;
	
	set tmp_sql = concat("insert into guide_info(",tmp_column_sql,"ctime) values(",tmp_value_sql,"NOW())");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
		
	SET affect_row = 1;			
	
END $$

DELIMITER ;
