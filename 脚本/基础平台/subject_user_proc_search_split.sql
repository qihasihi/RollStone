DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `subject_user_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `subject_user_proc_search_split`(
							p_subject_id INT,
							   p_user_id varchar(50),
							   p_subject_name VARCHAR(100),
							   p_ismar INT,
							   p_current_page INT,
							   p_page_size	INT,
							   p_sort_column VARCHAR(50),
							   OUT totalNum INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT ' us.*,s.subject_name ';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' us.subject_id=s.subject_id ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' j_user_subject us,subject_info s';
	DECLARE	t_insql VARCHAR(1000) DEFAULT '';
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and us.subject_id=",p_subject_id);
	END IF;
	IF p_ismar IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and us.is_major=",p_ismar);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and us.user_id='",p_user_id,"'");
	END IF;
	
	IF p_subject_name IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition," and s.subject_name='",p_subject_name,"'");
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
	
	
	SET tmp_sql=CONCAT(" select count(ref) into @totalcount from",tmp_tblname," where ",tmp_condition);
	
	SET @t_sql1 =tmp_sql;
	PREPARE s2 FROM @t_sql1;
	EXECUTE	s2;
	SET totalNum=@totalcount;
END $$

DELIMITER ;
