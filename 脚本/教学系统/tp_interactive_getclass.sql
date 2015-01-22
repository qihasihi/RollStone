DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_interactive_getclass`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_interactive_getclass`(
						p_course_id BIGINT,
						p_user_id BIGINT,		            
						p_role_type BIGINT    
							
							)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	
	
	IF p_role_type IS NOT NULL OR p_role_type=2 THEN   
		SET tmp_sql=CONCAT("	
		SELECT DISTINCT t.* FROM (
			SELECT  tcc.class_id,CONCAT(c.CLASS_GRADE,c.CLASS_NAME) classes,'1' classtype FROM tp_j_course_class tcc 
			INNER JOIN j_class_user cu ON cu.class_id=tcc.class_id AND tcc.class_type=1
			INNER JOIN class_info c ON c.CLASS_ID=tcc.CLASS_ID
			INNER JOIN user_info u ON u.REF=cu.USER_ID
			WHERE u.USER_ID=",p_user_id," AND course_id=",p_course_id,"
			UNION ALL
		 SELECT tcc.class_id ,tvc.VIRTUAL_CLASS_NAME classes,'2' classtype FROM tp_j_course_class tcc 
			INNER JOIN tp_virtual_class_info tvc ON tvc.VIRTUAL_CLASS_ID=tcc.CLASS_ID AND tcc.class_type=2
			INNER JOIN tp_j_virtual_class_student tvcs ON tvcs.VIRTUAL_CLASS_ID=tvc.VIRTUAL_CLASS_ID
			WHERE tvcs.USER_ID=",p_user_id," AND course_id=",p_course_id,"
		) t");
		ELSE    
		SET tmp_sql=CONCAT("
		SELECT DISTINCT t.class_id, t.class_type,
			(case t.class_type when 1 THEN (SELECT CONCAT(class_grade,class_name) cn FROM class_info where class_id=t.class_id limit 0,1)
					   WHEN 2 THEN  (SELECT VIRTUAL_CLASS_NAME FROM tp_virtual_class_info WHERE VIRTUAL_CLASS_ID=t.class_id LIMIT 0,1) END) classes		
			FROM (
				SELECT class_id,class_type
				FROM tp_j_course_class WHERE course_id=",p_course_id," AND user_id=",p_user_id,"
				)t LEFT JOIN class_info cla ON cla.class_id=t.class_id AND t.class_type=1
				   LEFT JOIN tp_virtual_class_info vc ON vc.VIRTUAL_CLASS_ID=t.class_id AND t.class_type=2
			");
	END IF;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
END $$

DELIMITER ;
