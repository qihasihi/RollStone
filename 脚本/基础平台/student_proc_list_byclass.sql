DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `student_proc_list_byclass`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `student_proc_list_byclass`(p_classid int,
						   p_year varchar(100),
						   p_pattern varchar(100)
						   )
BEGIN
	DECLARE tmp_sql VARCHAR(6000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' ju.user_id as userref,s.stu_no,s.stu_name';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '1=1';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' class_info c left join j_class_user ju on c.class_id = ju.class_id left join student_info s on ju.user_id = s.user_id'; 
	
	if p_classid is not null and
	   p_year is not null and
	   p_pattern is not null  then
	   
		set tmp_search_condition = concat(tmp_search_condition," and c.pattern='",p_pattern,"' and c.year='",p_year,"' and ju.class_id=",p_classid," and ju.relation_type='Ñ§Éú'");
		
		set tmp_sql = CONCAT(tmp_sql,"select ",tmp_search_column," from ",tmp_tbl_name," where ",tmp_search_condition);
		
		SET @tmp_sql=tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		
	end if;
END $$

DELIMITER ;
