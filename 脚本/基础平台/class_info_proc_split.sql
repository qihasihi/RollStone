DELIMITER $$
CREATE DEFINER=`mytest`@`%` PROCEDURE `class_info_proc_split`(
				           p_class_id INT,
				           p_lzx_class_id INT,
				           p_dc_school_id INT,
				          p_class_name VARCHAR(1000),
				          p_class_grade VARCHAR(1000),
				          p_type VARCHAR(1000),
				          p_year VARCHAR(1000),
				          p_pattern VARCHAR(1000),				  
				          p_user_ref VARCHAR(100),
				          p_islike INT,
				          p_subject_id	INT,
				          p_dctype INT,
				          p_isflag  INT,  
				          p_subject_str VARCHAR(1000),
				          p_invite_code VARCHAR(1000),
				          p_im_validate_code VARCHAR(1000),
						  p_term_id int,
						  p_activity_type int,
							p_current_page INT,
							p_page_size INT,
							p_sort_column VARCHAR(100),
							
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(1000) DEFAULT 'class_info u'; 
	
	IF p_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TYPE='",p_type,"'");
	END IF;
	IF p_im_validate_code IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.im_valdate_code='",replace(p_im_validate_code,'\\','\\\\'),"'");
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLASS_ID=",p_class_id);
	END IF;
	
	IF  p_dc_school_id  IS NOT NULL AND  p_dc_school_id>0 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.dc_school_id=",p_dc_school_id);
	END IF;
	IF p_lzx_class_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.lzx_classid=",p_lzx_class_id);
	END IF;
	IF p_dctype IS NOT NULL THEN
		IF p_dctype=9 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.dc_type>1");
		ELSE 
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.dc_type=",p_dctype);
		END IF;
	END IF;
	
	
	IF p_subject_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.subject_id=",p_subject_id);
	END IF;
	IF p_year IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.YEAR='",p_year,"'");
	END IF;
	
	IF p_pattern IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.PATTERN='",p_pattern,"'");
	END IF;
	
	
	IF p_class_grade IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLASS_GRADE='",p_class_grade,"'");
	END IF;
	
	IF p_isflag IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.isflag=",p_isflag);
	END IF;	
	
	IF p_class_name IS NOT NULL AND p_islike <1 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLASS_NAME like'%",p_class_name,"%'");
	ELSE 
		IF p_class_name IS NOT NULL THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLASS_NAME ='",p_class_name,"'");
		END IF;
	END IF;
	IF p_user_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND u.CLASS_ID IN (");
		SET tmp_search_condition=CONCAT(tmp_search_condition,"select DISTINCT t.class_id from j_class_user t where user_id='",p_user_ref,"') ");		
	END IF;
	
	IF p_subject_str IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND ((pattern='分层班' AND subject_id in (",p_subject_str,")) OR pattern='行政班') ");
	END IF;
	
	IF p_invite_code IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.invite_code='",p_invite_code,"'");
	END IF;

IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.term_id='",p_term_id,"'");
	END IF;

IF p_activity_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.activity_type='",p_activity_type,"'");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT u.*,g.grade_id,
       IFNULL((SELECT COUNT(*) FROM j_class_user cu WHERE cu.class_id=u.CLASS_ID AND cu.relation_type='学生'),0)NUM ,
       (SELECT subject_name FROM subject_info WHERE subject_id=u.subject_id) subjectname,
       (SELECT class_year_name FROM class_year_info where class_year_value=u.year) classyearname
       FROM (SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition,") u inner join grade_info g on u.class_grade=g.grade_value ");	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @sql2=tmp_sql;
	PREPARE stmt FROM @sql2  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$
DELIMITER ;
