DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teacher_proc_search_by_tnorun`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teacher_proc_search_by_tnorun`(p_teacher_id	int,
					            p_teacher_name	varchar(250),
					            p_teacher_sex	varchar(5),
					            p_user_id		int,
					            p_user_ref		varchar(50),
					            p_user_name		varchar(50),
					            p_real_name		varchar(50),
					            p_current_page 	INT,
						    p_page_size		INT,
						    p_sort_column 	VARCHAR(50),
						    p_dc_school_id      int,
						    OUT totalNum 	INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT ' t.*,u.user_name,u.user_id tuser_id,
	(SELECT (GROUP_CONCAT(si.SUBJECT_NAME)) FROM j_user_subject us 
	LEFT JOIN subject_info si ON us.SUBJECT_ID=si.SUBJECT_ID WHERE us.USER_ID=t.USER_ID) SUBJECTS';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' teacher_info t 
	inner join user_info u on u.ref=t.user_id   ';
	DECLARE	t_insql VARCHAR(1000) DEFAULT '';
	
        set tmp_tblname=concat(tmp_tblname," and u.dc_school_id=",p_dc_school_id); 	
        
	
	IF p_teacher_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and t.teacher_id=",p_teacher_id);
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and (t.teacher_name like '%",p_teacher_name,"%' or u.user_name like '%",p_teacher_name,"%')");
	END IF;
	
	IF p_teacher_sex IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and t.teacher_sex='",p_teacher_sex,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and u.user_id=",p_user_id);
	END IF;
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and u.ref='",p_user_ref,"'");
	END IF;
	
	IF p_real_name IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and (u.user_name like '%",p_real_name,"%' or t.teacher_name like '%",p_real_name,"%' )");
	END IF;
	
	
	
	SET tmp_sql=CONCAT("select ",tmp_search_col," from ",tmp_tblname," where ",tmp_condition);
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	
	SET @t_sql =tmp_sql;
	PREPARE s1 FROM @t_sql;
	EXECUTE	s1;
	DEALLOCATE PREPARE s1;
	
	
	SET tmp_sql=CONCAT(" select count(teacher_id) into @totalcount from",tmp_tblname," where ",tmp_condition);
	
	SET @t_sql1 =tmp_sql;
	PREPARE s2 FROM @t_sql1;
	EXECUTE	s2;
	SET totalNum=@totalcount;
END $$

DELIMITER ;
