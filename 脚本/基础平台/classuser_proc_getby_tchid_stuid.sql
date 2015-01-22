DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `classuser_proc_getby_tchid_stuid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `classuser_proc_getby_tchid_stuid`(
					       p_tch_ref varchar(50),
					       p_stu_ref VARCHAR(50),
					       p_year VARCHAR(50),
					       p_isflag INT,
					       out sumCount int)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT "SELECT cut.*,c.CLASS_NAME,c.CLASS_GRADE FROM j_class_user cut,j_class_user cus,class_info c WHERE 
	cut.CLASS_ID=c.CLASS_ID AND cut.CLASS_ID=cus.CLASS_ID AND cut.RELATION_TYPE='任课老师' AND cus.RELATION_TYPE='学生'";
	
	set tmp_sql=concat(tmp_sql,"AND cut.USER_ID='",p_tch_ref,"'");
	sET tmp_sql=CONCAT(tmp_sql,"AND cus.USER_ID='",p_stu_ref,"'");
	SET tmp_sql=CONCAT(tmp_sql,"AND c.YEAR='",p_year,"'");
	IF p_isflag IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,"AND c.isflag=",p_isflag);
	END IF;
	SET sumCount=0;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	SET sumCount=1;
	
END $$

DELIMITER ;
