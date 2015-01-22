DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `student_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `student_proc_search_split`(
				          p_ref VARCHAR(50),
						   p_stu_id INT,
						   p_stu_no VARCHAR(30),
						   p_stu_name VARCHAR(200),
						   p_stu_sex VARCHAR(10),
						   p_user_ref VARCHAR(100),
						   p_user_id INT,
						   p_user_name VARCHAR(50),
						   p_dc_school_id INT,
						   p_current_page INT,						   
						   p_page_size	INT,
						   p_sort_column VARCHAR(50),
						   OUT totalNum INT
						   )
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT ' s.ref,s.stu_id,s.stu_no,s.stu_name,s.stu_sex,s.stu_address,s.link_man,s.link_man_phone,s.user_id user_ref,s.c_time,s.m_time,u.user_name,u.user_id ';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' student_info s inner join user_info u on u.ref=s.user_id';
	DECLARE	t_insql VARCHAR(1000) DEFAULT '';
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and s.ref=",p_ref);
	END IF;
	
	IF p_stu_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and s.stu_id=",p_stu_id);
	END IF;
	
	IF p_stu_no IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and s.stu_no='",p_stu_no,"'");
	END IF;
	
	IF p_stu_sex IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and s.stu_sex='",p_stu_sex,"'");
	END IF;
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and u.ref='",p_user_ref,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and u.user_id=",p_user_id);
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and u.user_name like '%'",p_user_name,"'%'");
	END IF;
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and u.dc_school_id=",p_dc_school_id);
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
	
	SET tmp_sql=CONCAT(" select count(stu_id) into @totalcount from",tmp_tblname," where ",tmp_condition);
	
	SET @t_sql1 =tmp_sql;
	PREPARE s2 FROM @t_sql;
	EXECUTE	s2;
	SET totalNum=@totalcount;
END $$

DELIMITER ;
