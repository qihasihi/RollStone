DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rightuser_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rightuser_proc_search_split`(
				          p_user_id int,
					             p_user_name varchar(50),
					             p_right_id int,
					             p_right_type int,
					             p_current_page INT,
						     p_page_size INT,
						     p_sort_column VARCHAR(50),
						     OUT totalNum INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT ' jr.*,u.user_name,p.page_name,p.page_value,ifnull(t.teacher_name,ifnull(s.stu_name,u.user_name)) realname ';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' j_user_page_right jr inner join user_info u on jr.user_id=u.user_id inner join page_right_info p on jr.page_right_id=p.page_right_id
						    left join teacher_info t on u.ref=t.user_id left join student_info s on s.user_id=u.ref ';
	DECLARE	t_insql VARCHAR(1000) DEFAULT '';
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and jr.user_id=",p_user_id);
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and u.user_name like '%'",p_user_name,"'%'");
	END IF;
	
	IF p_right_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and p.page_right_id=",p_right_id);
	END IF;
	
	IF p_right_type IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and p.page_right_type=",p_right_type);
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
	
	
	SET tmp_sql=CONCAT(" select count(jr.ref) into @totalcount from",tmp_tblname," where ",tmp_condition);
	
	SET @t_sql1 =tmp_sql;
	PREPARE s2 FROM @t_sql1;
	EXECUTE	s2;
	SET totalNum=@totalcount;
END $$

DELIMITER ;
