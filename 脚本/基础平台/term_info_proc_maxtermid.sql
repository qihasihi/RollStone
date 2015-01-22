DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `term_info_proc_maxtermid`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `term_info_proc_maxtermid`(					 
						in_isflag INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT 'SELECT * FROM term_info WHERE NOW() BETWEEN semester_begin_date AND semester_end_date';
	
	SET tmp_sql="SELECT u.*,  IFNULL(
    (SELECT 
      CASE
        term_name 
        WHEN '第一学期' 
        THEN get_split_string (YEAR, '~', 1) 
        ELSE get_split_string (YEAR, '~', 2) 
      END 
    FROM
      term_info a 
    WHERE NOW() NOT BETWEEN a.SEMESTER_BEGIN_DATE 
      AND a.SEMESTER_END_DATE 
      AND a.ref = u.ref),
    DATE_FORMAT(NOW(), '%Y')
  ) auto_year FROM term_info u WHERE NOW() BETWEEN semester_begin_date AND semester_end_date";	
	
	IF in_isflag IS NOT NULL AND in_isflag=1 THEN
	    SET tmp_sql="SELECT u.*,  IFNULL(
    (SELECT 
      CASE
        term_name 
        WHEN '第一学期' 
        THEN get_split_string (YEAR, '~', 1) 
        ELSE get_split_string (YEAR, '~', 2) 
      END 
    FROM
      term_info a 
    WHERE NOW() NOT BETWEEN a.SEMESTER_BEGIN_DATE 
      AND a.SEMESTER_END_DATE 
      AND a.ref = u.ref),
    DATE_FORMAT(NOW(), '%Y')
  ) auto_year FROM term_info u WHERE semester_begin_date=(SELECT MAX(semester_begin_date) FROM term_info) ORDER BY semester_begin_date";	
	END IF;		
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
    END$$

DELIMITER ;