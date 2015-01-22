DELIMITER $$
CREATE DEFINER=`mytest`@`%` PROCEDURE `class_info_proc_list_elite_class`(
		gradeId int,
		termId int
	)
BEGIN
	declare tmp_sql varchar(500) default "select * from class_info c 
		inner join grade_info d on c.class_grade = d.grade_value where 1=1";

	if gradeId is not null then
		set tmp_sql = CONCAT(tmp_sql," and grade_id=",gradeId);
	end if;
	if termId is not null and termId<>0 then
		set tmp_sql = CONCAT(tmp_sql," and term_id =",termId);
	end if;

	SET @tmp_sql=tmp_sql;
	prepare stmt from @tmp_sql;
	execute stmt;
	deallocate prepare stmt;
END$$
DELIMITER ;
