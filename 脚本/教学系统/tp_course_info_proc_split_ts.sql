DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_split_ts`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_split_ts`(
				          p_course_id BIGINT,
				          p_cuser_id int,
				          p_course_name VARCHAR(100),
				          p_share_type INT,
				          p_course_level INT,
				          p_cloud_status INT,
				          p_course_status INT,
				          p_local_status INT,
				          p_teacher_name VARCHAR(100),
				          p_school_name VARCHAR(100),
				          p_term_id varchar(50),
				          p_subject_id int,
				          p_grade_id INT,
				          p_material_values varchar(50),
				          p_user_id INT,
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_ts_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.COURSE_ID ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_course_info u'; 
	
	if p_subject_id is not null
		and p_grade_id is not null
		AND p_user_id IS NOT NULL
		AND p_term_id IS NOT NULL  then
	
		SET tmp_ts_sql=CONCAT("INNER JOIN j_trusteeship_class tsc ON
			cc.USER_ID = tsc.RECEIVE_TEACHER_ID
			AND cc.CLASS_ID = tsc.TRUST_CLASS_ID 
			AND cc.CLASS_TYPE = tsc.TRUST_CLASS_TYPE
			AND tsc.TRUST_TEACHER_ID = ",p_user_id,"  
			AND tsc.is_accept = 1 ");
			
		SET tmp_search_condition=CONCAT(" exists 
			(SELECT 1 FROM tp_j_course_class cc
			",tmp_ts_sql,"
			WHERE u.course_id=cc.course_id and cc.SUBJECT_ID=",p_subject_id,"
			AND cc.GRADE_ID=",p_grade_id,"
			AND cc.TERM_ID='",p_term_id,"'
			GROUP BY cc.COURSE_ID
			)
		");
		
	end if;
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	IF p_course_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_NAME='",p_course_name,"'");
	END IF;
	
	IF p_share_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.SHARE_TYPE=",p_share_type);
	END IF;
	
	IF p_teacher_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TEACHER_NAME='",p_teacher_name,"'");
	END IF;
	
	IF p_course_level IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_LEVEL=",p_course_level);
	END IF;
	
	IF p_cuser_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CUSER_ID=",p_cuser_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_material_values IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and jtm.TEACHING_MATERIAL_ID IN(",p_material_values,")");
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_course_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_STATUS=",p_course_status);
	END IF;
	
	IF p_local_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.LOCAL_STATUS=",p_local_status);
	else
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.LOCAL_STATUS=1");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("
	SELECT tc.*,cc.*,t.TEACHER_NAME RECEIVE_TEACHER_NAME FROM (
		SELECT bb.COURSE_ID,bb.SUBJECT_ID,bb.GRADE_ID,bb.USER_ID,GROUP_CONCAT(classname) classes,GROUP_CONCAT(classid) classesid,GROUP_CONCAT(classtype) classtype,GROUP_CONCAT(classtime) classtimes
			FROM(SELECT aa.COURSE_ID,cc.SUBJECT_ID,cc.GRADE_ID,cc.USER_ID,
			    IFNULL(CONCAT(c.class_grade, c.class_name),tvc.VIRTUAL_CLASS_NAME) classname,
			    IFNULL(c.class_id,tvc.VIRTUAL_CLASS_ID) classid,
			    cc.CLASS_TYPE classtype,
			    DATE_FORMAT(
				cc.begin_time,
				'%Y-%m-%d %H:%i:%s'
			      ) classtime FROM(",tmp_sql,")aa
			LEFT JOIN tp_j_course_class cc ON cc.COURSE_ID = aa.COURSE_ID 
			",tmp_ts_sql,"
			LEFT JOIN class_info c ON c.CLASS_ID = cc.CLASS_ID AND cc.CLASS_TYPE=1
			LEFT JOIN tp_virtual_class_info tvc ON cc.CLASS_TYPE = 2 AND tvc.VIRTUAL_CLASS_ID = cc.CLASS_ID)bb 
		GROUP BY bb.COURSE_ID)cc
	INNER JOIN user_info u ON u.USER_ID=cc.USER_ID
	INNER JOIN teacher_info t ON t.USER_ID=u.REF
	INNER JOIN tp_course_info tc ON tc.COURSE_ID=cc.COURSE_ID");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
