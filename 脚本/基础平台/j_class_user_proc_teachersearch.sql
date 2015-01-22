DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_class_user_proc_teachersearch`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_class_user_proc_teachersearch`(
						p_relation_type VARCHAR(20),
						p_userref VARCHAR(100),
						p_year VARCHAR(30),
						p_gradeid INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '	
	SELECT DISTINCT g.grade_id,g.GRADE_NAME,g.GRADE_VALUE,class_grade,cu.subject_id,sb.SUBJECT_NAME FROM 
		j_class_user cu INNER JOIN class_info cla ON cla.CLASS_ID=cu.CLASS_ID
		INNER JOIN grade_info g ON g.GRADE_VALUE=class_grade
		LEFT JOIN subject_info sb ON sb.SUBJECT_ID=cu.SUBJECT_ID
				WHERE 1=1 ';
	IF p_userref IS NOT NULL THEN 
		SET tmp_sql=CONCAT(tmp_sql,' AND cu.USER_ID="',p_userref,'"');
	END IF;
	IF p_relation_type IS NOT NULL THEN 
		SET tmp_sql=CONCAT(tmp_sql,' AND relation_type="',p_relation_type,'"');
	END IF;
	IF p_gradeid IS NOT NULL THEN 
		SET tmp_sql=CONCAT(tmp_sql,' AND g.grade_id=',p_gradeid);
	END IF;
		
	SET tmp_sql=CONCAT(tmp_sql,' ORDER BY cla.CLASS_GRADE DESC');
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
END $$

DELIMITER ;
