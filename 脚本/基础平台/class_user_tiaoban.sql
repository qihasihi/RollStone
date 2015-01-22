DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_user_tiaoban`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_user_tiaoban`(
					  p_pattern VARCHAR(50),
				          p_classyear varchar(50),
				          p_clasid VARCHAR(50)				         				          
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	set tmp_sql=CONCAT(tmp_sql,'
		SELECT stu.STU_NO,stu.STU_NAME,isgray,t.relation_type,t.class_id,t.subject_id,t.user_id,t.sport_sex FROM (
			SELECT DISTINCT 
			(SELECT COUNT(DISTINCT REF) FROM j_class_user cut,class_info cla1
			WHERE cla1.CLASS_ID=cut.CLASS_ID 
	');
	
	IF p_classyear IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND cla1.YEAR='",p_classyear,"'");
	END IF;
	
	IF p_pattern IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,' AND cla1.PATTERN="',p_pattern,'"');
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,'  AND cut.user_id=cu.USER_ID ');
	
	SET tmp_sql=CONCAT(tmp_sql,' AND (CASE WHEN cla.subject_id IS NULL THEN TRUE ELSE cla1.subject_id=cla.subject_id END)) isgray,');	
	
	
	SET tmp_sql=CONCAT(tmp_sql,' cu.* FROM j_class_user cu,class_info cla	WHERE cla.CLASS_ID=cu.CLASS_ID ');
	
	IF p_clasid IS NOT NULL THEN
		set tmp_sql=CONCAT(tmp_sql,' AND cu.CLASS_ID=',p_clasid);
	END IF;	
	
	SET tmp_sql=CONCAT(tmp_sql,') t ,student_info stu  WHERE stu.USER_ID=t.USER_ID ORDER BY isgray asc ');
	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
END $$

DELIMITER ;
