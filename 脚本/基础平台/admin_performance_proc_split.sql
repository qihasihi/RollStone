DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `admin_performance_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `admin_performance_proc_split`(p_begin_time VARCHAR(100),
							p_end_time VARCHAR(100),
							p_grade_id INT,
							p_subject_id INT,
							p_class_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
							OUT sumCount INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(4000) DEFAULT 'SELECT * FROM admin_performance_teacher ap WHERE 1=1';
	IF p_grade_id IS NOT NULL AND p_subject_id IS NOT NULL AND p_class_id IS NOT NULL THEN
		SET tmp_sql = CONCAT(tmp_sql," and ap.grade_id=",p_grade_id);
		SET tmp_sql = CONCAT(tmp_sql," and ap.subject_id=",p_subject_id);
		SET tmp_sql = CONCAT(tmp_sql," and ap.class_id=",p_class_id);
	END IF;
	IF p_begin_time IS NOT NULL AND p_end_time IS NOT NULL THEN 
		SET tmp_sql = CONCAT(tmp_sql," and ap.course_time > STR_TO_DATE('",p_begin_time,"','%Y-%m-%d %H:%i:%s') and ap.course_time<STR_TO_DATE('",p_end_time,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_sql,") a");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
    END$$

DELIMITER ;