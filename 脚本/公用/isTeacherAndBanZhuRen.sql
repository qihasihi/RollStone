DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `isTeacherAndBanZhuRen`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `isTeacherAndBanZhuRen`(
  p_user_id VARCHAR (60),
  p_class_id INT,
  p_term_id VARCHAR(50),
  p_grade_value VARCHAR(50),
  OUT num INT
)
BEGIN
  DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
  DECLARE tmp_table_name VARCHAR(2000) DEFAULT '';
  DECLARE tmp_condition VARCHAR(2000) DEFAULT '';
  DECLARE user_type INT DEFAULT 0;
  
  
  
  SET tmp_table_name=" j_class_user cu,class_info c " ;
  SET tmp_condition = "  cu.class_id=c.class_id ";
  
	 IF p_class_id IS NOT NULL THEN 
		SET tmp_condition=CONCAT(tmp_condition," and cu.class_id =", p_class_id );
	 ELSEIF p_term_id IS NOT NULL THEN  
		SET tmp_condition=CONCAT(tmp_condition," and c.year=(SELECT year FROM term_info  WHERE ref='",p_term_id,"' and NOW() BETWEEN semester_begin_date AND semester_end_date ) " );
	 ELSE 
		SET tmp_condition=CONCAT(tmp_condition," and c.year=(SELECT year FROM term_info  WHERE NOW() BETWEEN semester_begin_date AND semester_end_date ) " );
	 END IF;   
	 
	IF p_grade_value IS NOT NULL THEN 
		SET tmp_condition=CONCAT(tmp_condition," and c.class_grade ='", p_grade_value,"'" );
	END IF;  
	 
	IF p_user_id IS NOT NULL THEN 
		SET tmp_condition=CONCAT(tmp_condition," and cu.user_id ='", p_user_id,"'" );
	END IF; 
 
	
	SET tmp_sql=CONCAT("select COUNT(DISTINCT  cu.relation_type ) into @teanum from ",tmp_table_name," where ",tmp_condition,"  AND cu.relation_type ='任课老师' ");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
		
	
	SET tmp_sql=CONCAT("select if(COUNT(DISTINCT  cu.relation_type )>0,2,0) into @bzrnum from ",tmp_table_name," where ",tmp_condition,"  AND cu.relation_type ='班主任' ");
	
	SET @sql2 =tmp_sql;   
	PREPARE s2 FROM  @sql2;   
	EXECUTE s2;   
	DEALLOCATE PREPARE s2;  
	
	
	
	
	
  SET num = @teanum+@bzrnum ;
END $$

DELIMITER ;
