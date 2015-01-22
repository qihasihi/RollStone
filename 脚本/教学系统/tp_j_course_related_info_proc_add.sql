DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_related_info_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_related_info_proc_add`(p_course_id bigint,
							   p_related_course_id bigint,
							   out affect_row int
							)
BEGIN
	
        DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
        
        
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	
	if p_course_id is not null 
	   and p_related_course_id is not null then
		SET tmp_column_sql=CONCAT(tmp_column_sql,"course_id,related_course_id");
		SET tmp_column_sql=CONCAT(tmp_value_sql,p_course_id,",",p_related_course_id);
	end if;
	
	SET tmp_sql = CONCAT("insert into tp_j_course_related_info(",tmp_column_sql,") values(",tmp_value_sql,")");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
		
	SET affect_row = 1;
END $$

DELIMITER ;
