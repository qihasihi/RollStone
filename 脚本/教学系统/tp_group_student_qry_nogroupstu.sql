DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_student_qry_nogroupstu`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_student_qry_nogroupstu`(				         
				          p_class_id INT,
				          p_class_type INT,
				          p_user_id VARCHAR(50),
				          p_subject_id INT,
				          p_term_id VARCHAR(50))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' SELECT DISTINCT u.USER_ID,cu.ref,u.password,u.ett_user_id,stu.STU_NAME,stu.STU_NO,ELT(INTERVAL(CONV(HEX(LEFT(CONVERT(stu_name USING gbk),1)),16,10),
		0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,0xBFA6,0xC0AC,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,0xC8F6,0xCBFA,0xCDDA,0xCEF4,0xD1B9,0xD4D1),
		''A'',''B'',''C'',''D'',''E'',''F'',''G'',''H'',''J'',''K'',''L'',''M'',''N'',''O'',''P'',''Q'',''R'',''S'',''T'',''W'',''X'',''Y'',''Z'') AS PY ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' u.state_id=0 and u.REF=stu.USER_ID AND stu.USER_ID=cu.USER_ID AND cu.RELATION_TYPE=''学生'' AND u.USER_ID NOT IN 
	(SELECT gs.user_id FROM tp_j_group_student gs,tp_group_info gi WHERE gs.GROUP_ID=gi.GROUP_ID';
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT '  student_info stu,j_class_user cu,user_info u ';
	IF p_class_type=1 THEN
		SET tmp_tbl_name=CONCAT(' student_info stu,j_class_user cu,user_info u ');
		SET tmp_search_condition=CONCAT(' u.REF=stu.USER_ID AND stu.USER_ID=cu.USER_ID AND cu.RELATION_TYPE=''学生'' AND u.state_id=0 AND u.USER_ID NOT IN 
			(SELECT gs.user_id FROM tp_j_group_student gs,tp_group_info gi WHERE gs.GROUP_ID=gi.GROUP_ID');
	END IF;
	IF p_class_type=2 THEN
		SET tmp_tbl_name=CONCAT(' student_info stu,tp_j_virtual_class_student cu,user_info u ');
		SET tmp_search_condition=CONCAT(' u.REF=stu.USER_ID AND u.USER_ID=cu.USER_ID AND u.state_id=0 AND u.USER_ID NOT IN 
			(SELECT gs.user_id FROM tp_j_group_student gs,tp_group_info gi WHERE gs.GROUP_ID=gi.GROUP_ID');
	END IF;
	SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.CLASS_ID=",p_class_id);
	SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.CLASS_TYPE=",p_class_type);
	SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.TERM_ID='",p_term_id,"'");
	IF p_class_type=1 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.subject_id=",p_subject_id,")AND cu.CLASS_ID=",p_class_id);
	END IF;
	IF p_class_type=2 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.subject_id=",p_subject_id,")AND cu.VIRTUAL_CLASS_ID=",p_class_id);
	END IF;
	SET tmp_sql=CONCAT("SELECT * FROM( ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET tmp_sql=CONCAT(tmp_sql," )aa ORDER BY   stu_no,CONVERT(aa.stu_name USING gbk)   COLLATE   gbk_chinese_ci ASC  ");
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
END $$

DELIMITER ;
