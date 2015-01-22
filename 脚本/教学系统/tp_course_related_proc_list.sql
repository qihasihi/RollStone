DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_related_proc_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_related_proc_list`(p_material_id bigint,p_user_id int,p_course_name varchar(200))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	SET tmp_sql=CONCAT(tmp_sql,"SELECT tc.COURSE_ID,(case when tc.course_level=1 and tc.course_id<0 then (concat(tc.course_name,'     （我的）')) 
								when tc.course_level=1 and tc.course_id>0 then (concat(tc.course_name,'     （标准）')) 
								when tc.course_level=2 then (concat(tc.course_name,'     （共享）')) 
								when tc.course_level=3 and tc.SHARE_TYPE=3 then (concat(tc.course_name,'     （我的）')) 
								when tc.course_level=3 and (tc.SHARE_TYPE=1 or tc.SHARE_TYPE=2) then (concat(tc.course_name,'     （共享）'))
								end) course_name ");
	set tmp_sql=concat(tmp_sql," FROM tp_course_info tc LEFT JOIN tp_j_course_teaching_material tj ON tc.COURSE_ID=tj.course_id");
	set tmp_sql=concat(tmp_sql," WHERE ((((tc.COURSE_LEVEL=1 and (tc.cuser_id=",p_user_id," or tc.course_id>0))  OR tc.COURSE_LEVEL=2) AND tc.COURSE_STATUS=1) OR (tc.course_level=3 AND tc.CUSER_ID=",p_user_id," AND tc.LOCAL_STATUS=1) or (tc.course_level=3 and (tc.share_type=1 or tc.share_type=2) AND tc.COURSE_STATUS=1))");
	set tmp_sql=concat(tmp_sql," AND tj.teaching_material_id=",p_material_id);
	if p_course_name is not null then 
		set tmp_sql=concat(tmp_sql," AND tc.COURSE_NAME LIKE '%",p_course_name,"%'");
	end if;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;
END $$

DELIMITER ;
