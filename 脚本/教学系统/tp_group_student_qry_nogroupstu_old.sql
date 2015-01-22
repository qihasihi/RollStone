DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_student_qry_nogroupstu_old`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_student_qry_nogroupstu_old`(p_class_id INT,
				          p_user_id VARCHAR(50))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' SELECT DISTINCT u.USER_ID,stu.STU_NAME,ELT(INTERVAL(CONV(HEX(LEFT(CONVERT(stu_name USING gbk),1)),16,10),
0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,0xBFA6,0xC0AC,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,0xC8F6,0xCBFA,0xCDDA,0xCEF4,0xD1B9,0xD4D1),
''A'',''B'',''C'',''D'',''E'',''F'',''G'',''H'',''J'',''K'',''L'',''M'',''N'',''O'',''P'',''Q'',''R'',''S'',''T'',''W'',''X'',''Y'',''Z'') AS PY ';  
	
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' u.REF=stu.USER_ID AND stu.USER_ID=cu.USER_ID AND cu.RELATION_TYPE=''Ñ§Éú'' AND u.USER_ID NOT IN 
	(SELECT gs.user_id FROM tp_j_group_student gs,tp_group_info gi WHERE gs.GROUP_ID=gi.GROUP_ID';  
					
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT '  student_info stu,j_class_user cu,user_info u '; 
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.CLASS_ID=",p_class_id);
		
	SET tmp_search_condition=CONCAT(tmp_search_condition," and gi.C_USER_ID=",p_user_id,")AND cu.CLASS_ID=",p_class_id);
	SET tmp_sql=CONCAT("SELECT * FROM( ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET tmp_sql=CONCAT(tmp_sql," )aa ORDER BY   CONVERT(aa.stu_name USING gbk)   COLLATE   gbk_chinese_ci ASC  ");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
