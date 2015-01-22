DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi115_proc_classinfo_for_course`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `imapi115_proc_classinfo_for_course`(p_user_id VARCHAR(60),p_class_id INT)
BEGIN
	DECLARE tmp_sql VARCHAR(5000) DEFAULT '';
	DECLARE tmp_condition_sql VARCHAR(2000) DEFAULT '';
	DECLARE tmp_search_sql VARCHAR(2000) DEFAULT '';
	DECLARE tmp_tbl_sql VARCHAR(1000) DEFAULT '';
	
	SET tmp_search_sql = ' ju.`SUBJECT_ID` subjectid,ci.`CLASS_ID` classid,CONCAT(ci.class_grade,ci.class_name) classname,g.`GRADE_ID` gradeid';
	SET tmp_tbl_sql = ' j_class_user ju,class_info ci,grade_info g';
	SET tmp_condition_sql = ' ju.`CLASS_ID`=ci.`CLASS_ID` AND ci.`CLASS_GRADE`=g.`GRADE_VALUE`';
	
	IF p_user_id IS NOT NULL AND p_class_id IS NOT NULL THEN
		SET tmp_condition_sql = CONCAT(tmp_condition_sql," AND ju.`USER_ID`='",p_user_id,"' AND ju.`SUBJECT_ID` is not null AND ju.subject_id in(select subject_id from j_class_user j where j.user_id='",p_user_id,"' and j.class_id=",p_class_id,")");
		SET tmp_condition_sql = CONCAT(tmp_condition_sql," AND ci.`CLASS_GRADE`=(SELECT class_grade FROM  class_info WHERE class_id=",p_class_id,")");
	END IF;
	
	SET tmp_sql = CONCAT(tmp_sql,"select ",tmp_search_sql," from ",tmp_tbl_sql," where " ,tmp_condition_sql);
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
    END$$

DELIMITER ;