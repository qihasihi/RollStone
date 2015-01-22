DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_virtual_class_student_by_gradeclass`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_virtual_class_student_by_gradeclass`(
				          p_grade VARCHAR(50),
				          p_classid INT,
				          p_stuname VARCHAR(100),
				          p_year VARCHAR(50),
				          p_virclassid INT,
				          p_dc_school_id int,
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET sumCount=0;
	
	if p_year is not null AND p_dc_school_id>0 then
		
		SET tmp_sql=CONCAT("SELECT u.USER_ID,replace(s.STU_NAME,' ','') STU_NAME FROM student_info s,user_info u ,(
			SELECT DISTINCT cu.user_id FROM j_class_user cu,class_info c
			WHERE  c.class_id=cu.class_id  AND cu.RELATION_TYPE='Ñ§Éú' AND c.year='",p_year,"'");
			
		if p_grade IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND c.CLASS_GRADE='",p_grade,"'");
		end if;
		
		IF p_classid IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," AND c.CLASS_ID=",p_classid);
		END IF;
		
		set tmp_sql=CONCAT(tmp_sql,")aa WHERE s.USER_ID=u.REF AND s.`USER_ID`=aa.USER_ID ");
		
		IF p_stuname IS NOT NULL THEN
			set tmp_sql=CONCAT(tmp_sql," AND s.STU_NAME like '%",p_stuname,"%'");
		END IF;
		
		SET tmp_sql=CONCAT(tmp_sql," and u.STATE_ID=0   and u.dc_school_id=",p_dc_school_id);
		
		SET tmp_sql=CONCAT(tmp_sql,"
			AND !EXISTS(SELECT tvcs.user_id FROM tp_j_virtual_class_student tvcs WHERE 
			tvcs.VIRTUAL_CLASS_ID=",p_virclassid," AND u.USER_ID=tvcs.USER_ID  )");
		
		SET @sql1 =tmp_sql;   
		PREPARE s1 FROM  @sql1;   
		EXECUTE s1;   
		DEALLOCATE PREPARE s1;  
		SET sumCount=1;
	end if;
	
END $$

DELIMITER ;
