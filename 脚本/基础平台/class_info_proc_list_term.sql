DELIMITER $$
CREATE DEFINER=`mytest`@`%` PROCEDURE `class_info_proc_list_term`()
BEGIN
	declare tmp_sql varchar(500) default "select distinct term_id from class_info where term_id is not null and term_id<>0 order by term_id asc";
	set @tmp_sql =tmp_sql;
	prepare stmt from @tmp_sql;
	execute stmt;
	deallocate prepare stmt;
END$$
DELIMITER ;
