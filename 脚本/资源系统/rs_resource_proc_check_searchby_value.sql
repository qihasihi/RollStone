DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_proc_check_searchby_value`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_proc_check_searchby_value`(
				         p_res_id VARCHAR(1000),
				          p_res_name VARCHAR(1000),
				          p_user_id INT,
				          p_user_type INT,
				          p_grades VARCHAR(100),
				          p_subjects VARCHAR(100),
				          p_file_types VARCHAR(100),
				          p_res_types VARCHAR(100),
				          p_user_name VARCHAR(1000),
				          p_school_name VARCHAR(1000),				          
				          p_res_status INT,
				          p_res_degree INT,
				           p_share_status INT,         
					   p_ctime VARCHAR(100),
				          p_course_id BIGINT,
				          p_isunion INT,
				          p_currentloginSub INT,
				          p_currentloginGrd INT,
				          p_sharestatusvalues VARCHAR(100),
				            p_type VARCHAR(1000),
				             p_reverse BOOLEAN,
				          p_dc_school_id BIGINT,
				          p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column VARCHAR(150),
				          OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE type_num INT DEFAULT 0;
	DECLARE type_i INT DEFAULT 0;
	DECLARE value_temp VARCHAR(2000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(5500) DEFAULT " DISTINCT
			r.res_id,r.res_name,r.res_introduce,r.user_id,r.user_type,r.res_status,r.res_score,r.clicks,r.commentnum,r.DOWNLOADNUM,r.REPORTNUM,r.user_name,r.school_name,r.res_degree,r.share_status
		,r.file_suffixname,r.file_size,r.use_object,r.net_share_status,r.res_type,r.file_type,r.c_time,
		r.STORENUM,r.RECOMENDNUM,r.PRAISENUM
		,r.grade,r.SUBJECT,r.dc_school_id
	 ";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(10000) DEFAULT 'rs_resource_info r '; 
	DECLARE course_res_sql VARCHAR(10000) DEFAULT ' '; 
	DECLARE total_sql VARCHAR(10000) DEFAULT ' '; 
  
	
	IF p_sharestatusvalues IS NULL  OR (LOCATE(1,p_sharestatusvalues)>0 AND  LOCATE(2,p_sharestatusvalues)>0) THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition,' AND ((r.RES_DEGREE=3 AND r.SHARE_STATUS=1) OR (r.RES_DEGREE <>3)) ');
	END IF;
	IF p_sharestatusvalues IS NOT NULL AND (LOCATE(1,p_sharestatusvalues)<1 OR  LOCATE(2,p_sharestatusvalues)<1) THEN
		IF LOCATE(1,p_sharestatusvalues)>0 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition,' AND r.RES_DEGREE=3 AND r.SHARE_STATUS=1');
		END IF;
		IF LOCATE(2,p_sharestatusvalues)>0 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition,' AND r.RES_DEGREE<>3 ');
		END IF;
	END IF;
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_ID='",p_res_id,"'");
	END IF;
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_NAME LIKE '%",p_res_name,"%'");
	END IF;
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.dc_school_id=",p_dc_school_id," AND r.dc_school_id IS NOT NULL ");
	END IF;
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USER_ID=",p_user_id);
	END IF;
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USER_ID=",p_user_type);
	END IF;
	
	IF p_grades IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.GRADE IN (",p_grades,")");
	END IF;
	
	IF p_subjects IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SUBJECT IN (",p_subjects,")");
	END IF;
	
	IF p_res_types IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_TYPE IN (",p_res_types,")");
	END IF;	
	
	IF p_file_types IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.FILE_TYPE IN (",p_file_types,")");
	END IF;
	
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USER_NAME LIKE '%",p_user_name,"%'");
	END IF;
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SCHOOL_NAME='",p_school_name,"'");
	END IF;	
	
	
	
	IF p_res_status IS NOT NULL THEN
		IF p_res_status=-3 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_STATUS<>3");
	        ELSEIF p_res_status=-100 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and (r.RES_STATUS=1 OR r.res_status=3)");
		ELSE
			SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_STATUS=",p_res_status);
		END IF;
	END IF;
	IF p_res_degree IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_DEGREE=",p_res_degree);
	END IF;
	
	IF p_share_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SHARE_STATUS=",p_share_status);
	END IF;	
	IF p_type IS NOT NULL THEN
		SET type_num = get_split_string_total(p_type,'|');	
		IF type_num>1 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," AND LOWER(r.FILE_SUFFIXNAME) ");
		END IF;
		
		IF p_reverse THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition,"NOT IN(");
		ELSE
			SET tmp_search_condition=CONCAT(tmp_search_condition,"IN(");
		END IF;		
		WHILE type_i<type_num DO
			SET value_temp=get_split_string(p_type,'|',type_i+1);
			IF type_i>0 THEN
				SET tmp_search_condition=CONCAT(tmp_search_condition,", ");
			END IF;
			SET tmp_search_condition=CONCAT(tmp_search_condition,"'",value_temp,"'");
			SET type_i=type_i+1;
		END WHILE;		
		SET tmp_search_condition=CONCAT(tmp_search_condition,")");		
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," INNER JOIN tp_j_course_resource_info tt ON tt.res_id =r.res_id ");
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tt.course_id=",p_course_id);
	END IF;
	
	IF p_ctime IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.C_TIME>str_to_date('",p_ctime,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	IF p_isunion IS NULL OR (p_sharestatusvalues IS NOT NULL AND LOCATE(1,p_sharestatusvalues)>0) THEN
		SET tmp_sql=CONCAT(" SELECT r.RES_ID FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	END IF;
       	IF p_isunion IS NOT NULL OR (p_sharestatusvalues IS NOT NULL AND LOCATE(2,p_sharestatusvalues)>0) THEN 
		 IF p_sharestatusvalues IS NOT NULL AND LOCATE(1,p_sharestatusvalues)>0 THEN
		     SET tmp_sql=CONCAT(tmp_sql,' union ');
		 END IF;
		SET tmp_sql=CONCAT(tmp_sql,' 	
		       SELECT DISTINCT r.res_id FROM (SELECT r.res_id
				  FROM tp_j_course_resource_info cr 
				  INNER JOIN rs_resource_info r ON cr.res_id=r.RES_ID
				  INNER JOIN tp_course_info tc ON tc.course_id=cr.course_id
				  INNER JOIN  tp_j_course_teaching_material tcm ON tc.course_id=tcm.course_id
				  INNER JOIN teaching_material_info tm ON tm.material_id=tcm.teaching_material_id 
				  where 1=1 and r.res_status=1  
				  ');
		IF p_dc_school_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and r.dc_school_id=",p_dc_school_id," AND r.dc_school_id IS NOT NULL ");
		END IF;
			IF p_sharestatusvalues IS NULL OR (LOCATE(1,p_sharestatusvalues)>0 AND  LOCATE(2,p_sharestatusvalues)>0) THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND ((r.RES_DEGREE=3 AND r.SHARE_STATUS=1) OR (r.RES_DEGREE <>3)) ');
			END IF;
			IF p_sharestatusvalues IS NOT NULL AND (LOCATE(1,p_sharestatusvalues)<1 OR  LOCATE(2,p_sharestatusvalues)<1) THEN
				IF LOCATE(1,p_sharestatusvalues)>0 THEN
					SET tmp_sql=CONCAT(tmp_sql,' AND r.RES_DEGREE=3 AND r.SHARE_STATUS=1');
				END IF;
				IF LOCATE(2,p_sharestatusvalues)>0 THEN
					SET tmp_sql=CONCAT(tmp_sql,' AND r.RES_DEGREE<>3 ');
				END IF;
			END IF;
		IF p_user_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and r.USER_NAME LIKE '%",p_user_name,"%'");
		END IF;
		
		IF p_res_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and r.RES_NAME LIKE '%",p_res_name,"%'");
		END IF;
		IF p_ctime IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and r.C_TIME>str_to_date('",p_ctime,"','%Y-%m-%d %H:%i:%s')");
		END IF;
		
		IF p_grades IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and tm.GRADE_ID IN (",p_grades,")");
		END IF;
	
		IF p_subjects IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and tm.SUBJECT_ID IN (",p_subjects,")");
		END IF;
		
		IF p_res_types IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and r.RES_TYPE IN (",p_res_types,")");
		END IF;	
		
		IF p_file_types IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and r.FILE_TYPE IN (",p_file_types,")");
		END IF;
		IF p_type IS NOT NULL THEN
			SET type_num = get_split_string_total(p_type,'|');	
			IF type_num>1 THEN
				SET tmp_sql=CONCAT(tmp_sql," AND LOWER(r.FILE_SUFFIXNAME) ");
			END IF;
			
			IF p_reverse THEN 
				SET tmp_sql=CONCAT(tmp_sql,"NOT IN(");
			ELSE
				SET tmp_sql=CONCAT(tmp_sql,"IN(");
			END IF;		
			SET type_i=0;
			WHILE type_i<type_num DO
				SET value_temp=get_split_string(p_type,'|',type_i+1);
				IF type_i>0 THEN
					SET tmp_sql=CONCAT(tmp_sql,", ");
				END IF;
				SET tmp_sql=CONCAT(tmp_sql,"'",value_temp,"'");
				SET type_i=type_i+1;
			END WHILE;		
			SET tmp_sql=CONCAT(tmp_sql,")");		
		END IF;
		
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY ");
			IF p_sort_column IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql," ",p_sort_column);
			ELSE 
				IF p_currentloginSub IS NOT NULL THEN
					SET tmp_sql=CONCAT(tmp_sql," ABS(",p_currentloginSub,"-IFNULL(tm.subject_id,0)) ASC,");		
				END IF;
				IF p_currentloginGrd IS NOT NULL THEN
					SET tmp_sql=CONCAT(tmp_sql," ABS(",p_currentloginGrd,"-IFNULL(tm.grade_id,0)) ASC,");		
				END IF;
				SET tmp_sql=CONCAT(tmp_sql," r.C_TIME DESC");
			END IF;	  
		SET tmp_sql=CONCAT(tmp_sql,") r");
	END IF;
	
	
	SET tmp_sql=CONCAT(" SELECT t.RES_ID FROM (",tmp_sql);
	
	
	
	
		
	SET tmp_sql=CONCAT(tmp_sql,")t ");
	SET total_sql=tmp_sql;
		
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	SET tmp_sql=CONCAT("  SELECT ",tmp_search_column," FROM (",tmp_sql,") bb
				  INNER JOIN rs_resource_info r ON bb.res_id=r.RES_ID
				 ");
				 
	IF p_isunion IS NOT NULL THEN
	 SET tmp_sql=CONCAT( tmp_sql,"
			LEFT JOIN tp_j_course_resource_info cr ON r.res_id=cr.res_id
				  LEFT JOIN tp_course_info tc ON tc.course_id=cr.course_id
				  LEFT JOIN  tp_j_course_teaching_material tcm ON tc.course_id=tcm.course_id
				  LEFT JOIN teaching_material_info tm ON tm.material_id=tcm.teaching_material_id
	 ");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT aa.*,
		IFNULL(sub.SUBJECT_NAME,'--') SUBJECT_NAME,
		IFNULL(g.GRADE_NAME,'--') GRADE_NAME,
		IFNULL(d1.DICTIONARY_NAME,'--') RES_TYPE_NAME,
		IFNULL(d2.DICTIONARY_NAME,'--') FILE_TYPE_NAME
		FROM (",tmp_sql,") aa 	
		LEFT JOIN subject_info sub ON sub.SUBJECT_ID=aa.SUBJECT
		LEFT JOIN grade_info g ON g.GRADE_ID=aa.GRADE
		LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=aa.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'
		LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=aa.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE'");
		
		
	
	
	
	
	
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",total_sql,") t");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
