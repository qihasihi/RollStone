DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_subgrade_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_course_subgrade_proc_split`(
							p_term_id VARCHAR(50),
							p_userid INT,
							p_current_page INT,
							p_page_size	INT,
							p_sort_column VARCHAR(50),
							OUT totalNum INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT '  DISTINCT cc.grade_id,g.grade_value,cc.subject_id,s.`SUBJECT_NAME` ';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 and tc.`COURSE_ID`=cc.course_id AND g.`GRADE_ID`=cc.`grade_id` AND s.`SUBJECT_ID`=cc.`subject_id` and cu.class_id=cc.class_id ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT '  tp_course_info tc,tp_j_course_class cc,j_class_user cu,grade_info g,subject_info s ';
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_condition = CONCAT(tmp_condition," and cc.term_id='",p_term_id,"'");
	END IF;
	
	IF p_userid IS NOT NULL THEN
		SET tmp_condition = CONCAT(tmp_condition," and cc.user_id=",p_userid);
	END IF;
	
	
	SET tmp_sql = CONCAT("select ",tmp_search_col," from ",tmp_tblname," where ",tmp_condition);
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	
	SET tmp_sql=CONCAT(" select count(*) into @totalcount from",tmp_tblname," where ",tmp_condition);
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET totalNum = @totalcount;
    END$$

DELIMITER ;