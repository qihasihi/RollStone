DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `student_proc_search_bystuno`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `student_proc_search_bystuno`(p_stuno varchar(100))
BEGIN
	DECLARE tmp_sql VARCHAR(6000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' s.stu_id,s.stu_name,s.ref,s.user_id as userref,s.stu_no';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '1=1';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' student_info s';
	
	if p_stuno is not null then
		set tmp_search_condition = concat(tmp_search_condition," and s.stu_no='",p_stuno,"'");
		SET tmp_sql = CONCAT(tmp_sql,"select ",tmp_search_column," from ",tmp_tbl_name," where ",tmp_search_condition);
		
		SET @tmp_sql=tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
	end if;
END $$

DELIMITER ;
