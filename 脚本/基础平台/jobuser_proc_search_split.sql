DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `jobuser_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `jobuser_proc_search_split`(
				          p_job_id int,
						   p_user_id varchar(50),
						   p_job_name varchar(100),
						   p_current_page INT,
						   p_page_size	INT,
						   p_sort_column VARCHAR(50),
						   OUT totalNum INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT ' uj.*,j.job_name ';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' j_user_job uj';
	DECLARE	t_insql VARCHAR(1000) DEFAULT '';
	set tmp_tblname="(SELECT uj.* FROM j_user_job uj WHERE 1=1";
	IF p_job_id IS NOT NULL THEN
		SET tmp_tblname=CONCAT(tmp_tblname," and uj.job_id=",p_job_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_tblname=CONCAT(tmp_tblname," and uj.user_id='",p_user_id,"'");
	END IF;
	
	set tmp_tblname=CONCAT(tmp_tblname,") uj,job_info j WHERE uj.job_id=j.job_id");
	
	IF p_job_name IS NOT NULL THEN
		SET tmp_tblname=CONCAT(tmp_tblname," and j.job_name='",p_job_name,"'");
	END IF;
	
	
	SET tmp_sql=CONCAT("select ",tmp_search_col," from ",tmp_tblname);
	
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
	
	
	SET tmp_sql=CONCAT(" select count(ref) into @totalcount from",tmp_tblname);
	
	SET @t_sql1 =tmp_sql;
	PREPARE s2 FROM @t_sql1;
	EXECUTE	s2;
	SET totalNum=@totalcount;
END $$

DELIMITER ;
