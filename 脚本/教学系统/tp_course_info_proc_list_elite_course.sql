DELIMITER $$
CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_course_info_proc_list_elite_course`(
		gradeId int,
		termId int,
		subjectId int
	)
BEGIN
	declare tmp_sql varchar(900) default "select
		c.course_id,c.teacher_name,c.course_name,j.begin_time,j.end_time, j.subject_id from 
		tp_course_info c inner join tp_j_course_class j on c.course_id = j.course_id 
		inner join class_info ci on j.class_id = ci.class_id
		inner join grade_info gi on gi.GRADE_VALUE = ci.CLASS_GRADE where 1=1 ";
	if gradeId is not null and termId is not null and subjectId is not null then
		if gradeId is not null then
			set tmp_sql = CONCAT(tmp_sql," and j.grade_id=",gradeId);
		end if;
		if termId is not null and termId<>'' then
			set tmp_sql = CONCAT(tmp_sql," and ci.term_id=",termId);
		end if;
		if subjectId is not null and subjectId<>-1 then
			set tmp_sql = CONCAT(tmp_sql," and j.subject_id=",subjectId);
		end if;
		set tmp_sql = CONCAT(tmp_sql," order by j.begin_time");
		SET @tmp_sql=tmp_sql;
		prepare stmt from @tmp_sql;
		execute stmt;
		deallocate prepare stmt;
	end if;
END$$
DELIMITER ;
