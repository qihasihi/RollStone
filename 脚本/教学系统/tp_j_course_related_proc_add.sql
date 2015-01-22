DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_related_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_related_proc_add`(p_courseid bigint,
							p_related_courseid bigint,
							out affect_row int)
BEGIN
	declare tmp_column_sql varchar(2000) default '';
	declare tmp_value_sql varchar(2000) default '';
	declare tmp_sql varchar(5000) default '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	if p_courseid is not null then 
		set tmp_column_sql=concat(tmp_column_sql,"course_id");
		set tmp_value_sql = concat(tmp_value_sql,p_courseid);
	end if;
	
	if p_related_courseid is not null then 
		set tmp_column_sql = concat(tmp_column_sql,",related_course_id");
		set tmp_value_sql = concat(tmp_value_sql,",",p_related_courseid);
	end if;
	
	SET tmp_sql=CONCAT("insert into tp_j_course_related_info(",tmp_column_sql,")VALUES(",tmp_value_sql,")");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
END $$

DELIMITER ;
