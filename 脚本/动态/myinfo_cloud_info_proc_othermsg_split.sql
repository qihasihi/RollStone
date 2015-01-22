DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `myinfo_cloud_info_proc_othermsg_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `myinfo_cloud_info_proc_othermsg_split`(				      
					    p_user_id BIGINT,		
					    p_target_id BIGINT,
					    p_typeid BIGINT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(200000) DEFAULT '';
	DECLARE tmp_condition VARCHAR(100000) DEFAULT ' 1=1 ';
	DECLARE tmp_order VARCHAR(500) DEFAULT ' mc.REF DESC ';
	IF p_sort_column IS NOT NULL AND LENGTH(p_sort_column)>0 THEN
		SET tmp_order=p_sort_column;
	END IF;
	
	SET tmp_sql=CONCAT("		
			SELECT DISTINCT mc.*,IFNULL(tea.TEACHER_NAME,IFNULL(stu.STU_NAME,u.USER_NAME)) realName ,
			IF(mc.type=1,(SELECT res_name FROM rs_resource_info WHERE res_id=mc.target_id)
					,(SELECT c.course_name FROM tp_course_info c WHERE c.course_id=mc.target_id)
			) targetName
			FROM myinfo_cloud_info mc
				INNER JOIN user_info u ON mc.user_id=u.USER_ID
				LEFT JOIN teacher_info tea ON tea.USER_ID=u.ref
				LEFT JOIN student_info stu ON stu.USER_ID=u.ref
			 WHERE mc.user_id IN (
				SELECT user_id FROM user_info WHERE ref IN (
					SELECT DISTINCT user_id FROM j_user_subject WHERE subject_id IN (
						SELECT subject_id FROM j_user_subject WHERE user_id=(SELECT ref FROM user_info WHERE user_id=",p_user_id,")
					)
				)
			) AND mc.user_id <>",p_user_id," AND mc.data LIKE '%·ÖÏí%' AND u.dc_school_id=(SELECT dc_school_id FROM user_info where user_id=",p_user_id,")");
	IF p_target_id IS NOT NULL THEN
	 SET tmp_sql=CONCAT(tmp_sql," AND mc.target_id=",p_target_id);
	END IF;	
	IF p_typeid IS NOT NULL THEN
	 SET tmp_sql=CONCAT(tmp_sql," AND mc.TYPE=",p_typeid);
	END IF;	
	SET tmp_sql=CONCAT(tmp_sql," ORDER By ",tmp_order);	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(DISTINCT mc.REF) INTO @tmp_sumCount
			FROM myinfo_cloud_info mc
				INNER JOIN user_info u ON mc.user_id=u.USER_ID
				LEFT JOIN teacher_info tea ON tea.USER_ID=u.ref
				LEFT JOIN student_info stu ON stu.USER_ID=u.ref
			 WHERE mc.user_id IN (
				SELECT user_id FROM user_info WHERE ref IN (
					SELECT DISTINCT user_id FROM j_user_subject WHERE subject_id IN (
						SELECT subject_id FROM j_user_subject WHERE user_id=(SELECT ref FROM user_info WHERE user_id=",p_user_id,")
					)
				)
			) AND mc.user_id <>",p_user_id,"  AND mc.data LIKE '%·ÖÏí%' AND u.dc_school_id=(SELECT dc_school_id FROM user_info where user_id=",p_user_id,")");
	IF p_target_id IS NOT NULL THEN
	 SET tmp_sql=CONCAT(tmp_sql," AND mc.target_id=",p_target_id);
	END IF;	
	IF p_typeid IS NOT NULL THEN
	 SET tmp_sql=CONCAT(tmp_sql," AND mc.TYPE=",p_typeid);
	END IF;	
			
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
