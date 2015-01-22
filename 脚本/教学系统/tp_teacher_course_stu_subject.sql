DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_teacher_course_stu_subject`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_teacher_course_stu_subject`(
				          p_term_id VARCHAR(50),
				          p_user_id VARCHAR(50),
				          p_available INT,
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT '';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_teacher_course_info tc 
	                                               LEFT JOIN tp_course_class cc ON cc.course_id = tc.course_id
						       LEFT JOIN j_class_user cu ON cu.class_id = cc.class_id
						       INNER JOIN subject_info s ON s.SUBJECT_ID=tc.SUBJECT_ID'; 
	
	SET tmp_search_column = CONCAT("distinct tc.subject_id,s.SUBJECT_NAME");
	SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE<>2");
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.TERM_ID='",p_term_id,"'");
	END IF;
	
	IF p_available IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tc.AVAILABLE=",p_available);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cu.user_id='",p_user_id,"' and cu.RELATION_TYPE='Ñ§Éú'");
	END IF;
	SET tmp_search_condition=CONCAT(tmp_search_condition," and cc.CLASSTIME<NOW() ORDER BY tc.SUBJECT_ID");
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);		
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
