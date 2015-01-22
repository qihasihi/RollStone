DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_user_proc_theme_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_user_proc_theme_split`(
				           p_class_id INT,
				          p_class_name VARCHAR(1000),
				          p_class_grade VARCHAR(1000),
				          p_relationtype VARCHAR(1000),
				          p_year VARCHAR(1000),
				          p_pattern VARCHAR(1000)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT 'SELECT DISTINCT u.REF userid,u.USER_NAME,IFNULL(t.TEACHER_NAME,IFNULL(s.STU_NAME,u.USER_NAME)) realName		
		 FROM j_class_user cu 
		 INNER JOIN class_info c ON c.class_id=cu.class_id
		INNER JOIN user_info u ON u.ref=cu.USER_ID 
		LEFT JOIN teacher_info t ON t.USER_ID=cu.USER_ID
		LEFT JOIN student_info s ON s.USER_ID=cu.USER_ID WHERE 1=1 ';
	IF p_class_id IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND cu.class_id=",p_class_id);
	END IF;
	IF p_class_name IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND cu.class_name='",p_class_name,"'");
	END IF;
	IF p_class_grade IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND cu.class_grade='",p_class_grade,"'");
	END IF;
	IF p_relationtype IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND cu.relation_type='",p_relationtype,"'");
	END IF;
	IF p_year IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND c.year='",p_year,"'");
	END IF;
	IF p_pattern IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," AND c.pattern='",p_pattern,"'");
	END IF;	
	set tmp_sql=CONCAT(tmp_sql,' ORDER BY u.USER_ID ASC');
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1; 
END $$

DELIMITER ;
