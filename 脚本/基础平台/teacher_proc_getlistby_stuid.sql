DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teacher_proc_getlistby_stuid`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teacher_proc_getlistby_stuid`(
				          p_stu_id VARCHAR(1000),
				          p_year VARCHAR(1000),
				          OUT sumCount int)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' teacher_info t,user_info u,j_class_user cut,subject_info s';
	set tmp_sql=concat("select DISTINCT t.*,GROUP_CONCAT(s.SUBJECT_NAME SEPARATOR '/') SUBJECTS from ",tmp_tbl_name,"  where u.REF=t.USER_ID and s.SUBJECT_ID=cut.SUBJECT_ID and cut.USER_ID=t.USER_ID 
	AND u.STATE_ID=0 AND cut.CLASS_ID in(SELECT c.CLASS_ID FROM class_info c,j_class_user cus WHERE c.CLASS_ID=cus.CLASS_ID AND 
	cus.USER_ID='",p_stu_id,"' AND c.YEAR='",p_year,"') and cut.RELATION_TYPE='»ŒøŒ¿œ ¶' GROUP BY t.TEACHER_ID"); 
	
	SET sumCount=0;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	SET sumCount=1;
	
END $$

DELIMITER ;
