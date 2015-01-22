DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_proc_adminsearch_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_proc_adminsearch_split`(p_userid VARCHAR(60),p_atname VARCHAR(100),p_state INT,
							p_current_page INT,
							p_page_size	INT,
							p_sort_column VARCHAR(50),
							OUT totalNum INT)
BEGIN
	DECLARE tmp_sql VARCHAR(3000) DEFAULT '';
	DECLARE tmp_search_col VARCHAR(1000) DEFAULT ' ac.ref,ac.AT_NAME,ac.BEGIN_TIME,ac.END_TIME,ac.c_user_id,ifnull(t.teacher_name,s.stu_name) as username,ac.STATE,ac.is_sign,(SELECT COUNT(*) FROM at_j_activityuser_info au WHERE au.ATTITUDE=0 AND au.ACTIVITY_ID=ac.REF) AS num1,(SELECT COUNT(*) FROM at_j_activityuser_info au  WHERE au.ACTIVITY_ID=ac.REF and ac.is_sign=1) AS num2 ';
	DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 ';
	DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' at_activity_info ac left join teacher_info t on ac.c_user_id = t.user_id left join student_info s on ac.c_user_id=s.user_id';
	IF p_atname IS NOT NULL THEN 
		SET tmp_condition = CONCAT(tmp_condition," and ac.at_name like '%",p_atname,"%'");
	END IF;
	IF p_state IS NOT NULL THEN 
		SET tmp_condition = CONCAT(tmp_condition," and ac.state = ",p_state);
	END IF;
	SET tmp_sql = CONCAT("select ",tmp_search_col,",(SELECT c.ATTITUDE FROM at_j_activityuser_info c WHERE c.activity_id = ac.ref AND c.user_id='",p_userid,"' ) as attitude,(SELECT COUNT(*) FROM at_j_activityuser_info au  WHERE  au.ACTIVITY_ID=ac.REF and ac.is_sign=0 and au.user_id='",p_userid,"') AS isIn"," from ",tmp_tblname," where ",tmp_condition);
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	
	SET tmp_sql=CONCAT(" select count(ac.ref) into @totalcount from",tmp_tblname," where ",tmp_condition);
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET totalNum = @totalcount;
END $$

DELIMITER ;
