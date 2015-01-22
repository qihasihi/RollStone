DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_qry_stu_in_course`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_qry_stu_in_course`(
				          p_user_id VARCHAR(50),
				          p_course_id VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	
	
	SET tmp_sql="SELECT COUNT(*) INTO @tmp_sumCount FROM tp_teacher_course_info tc INNER JOIN tp_course_class cc on tc.course_id =cc.course_id
      LEFT JOIN j_class_user cu ON cc.class_id = cu.class_id 
      LEFT JOIN student_info s ON s.user_id =cu.user_id ";
        
        if p_user_id is not null then
		set tmp_sql=concat(tmp_sql," and cu.user_id='",p_user_id,"'");
        end if;
        
        IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and tc.course_id='",p_course_id,"'");
        END IF;
      
   
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
