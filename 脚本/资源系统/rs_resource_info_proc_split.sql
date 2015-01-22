DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `rs_resource_info_proc_split`(
				          p_res_id VARCHAR(1000),
				          p_res_name VARCHAR(1000),
				          p_res_keyword VARCHAR(1000),
				          p_res_introduce VARCHAR(1000),
				          p_user_id INT,
				          p_user_type INT,
				          p_grade INT,
				          p_subject INT,
				          p_file_type INT,
				          p_res_type INT,
				          p_file_name VARCHAR(1000),
				          p_file_size INT,
				          p_user_name VARCHAR(1000),
				          p_school_name VARCHAR(1000),
				          
				          p_use_object VARCHAR(1000),
				          p_res_status INT,
				          p_res_degree INT,
				          
				          p_share_status INT,
				          p_reportnum INT,
				          p_praisenum INT,
				          p_downloadnum INT,
				          p_commentnum INT,
				          p_recomendnum INT,
				          p_clicks INT,
				          p_storenum INT,
				          p_res_score FLOAT UNSIGNED,
				          
				          p_net_share_status INT,
				          p_net_reportnum INT,
				          p_net_praisenum INT,
				          p_net_downloadnum INT,
				          p_net_commentnum INT,
					  p_net_recomendnum INT,
					  p_net_clicks INT,
				          p_net_storenum INT,
				          p_ctime VARCHAR(100),
				          p_convert_status INT,
				          p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " r.*";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'rs_resource_info r '; 
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_ID='",p_res_id,"'");
	END IF;
	
	IF p_convert_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.convert_status=",p_convert_status);
	END IF;
	
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_NAME LIKE '",p_res_name,"%'");
	END IF;
	
	IF p_res_keyword IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_KEYWORD='",p_res_keyword,"'");
	END IF;
	
	IF p_res_introduce IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_INTRODUCE='",p_res_introduce,"'");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USER_ID=",p_user_id);
	END IF;
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USER_ID=",p_user_type);
	END IF;
	
	IF p_grade IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.GRADE=",p_grade);
	END IF;
	
	IF p_subject IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SUBJECT=",p_subject);
	END IF;
	
	IF p_res_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_TYPE=",p_res_type);
	END IF;
	
	IF p_file_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.FILE_TYPE=",p_file_type);
	END IF;
	
	IF p_file_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.FILE_NAME='",p_file_name,"'");
	END IF;
	
	IF p_file_size IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.FILE_SIZE=",p_file_size);
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USER_NAME LIKE '",p_user_name,"%'");
	END IF;
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	IF p_use_object IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USE_OBJECT='",p_use_object,"'");
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
            IF p_res_degree=-3 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_DEGREE<>3");
            ELSE
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_DEGREE=",p_res_degree);
		END IF;
	END IF;
	
	IF p_share_status IS NOT NULL THEN
		IF p_share_status=-3 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and ((r.RES_DEGREE=3 AND r.SHARE_STATUS=1) OR (r.RES_DEGREE <>3))");
		  ELSE
		  SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SHARE_STATUS=",p_share_status);
		END IF;
		
	END IF;
	
	IF p_reportnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.REPORTNUM=",p_reportnum);
	END IF;
	
	IF p_praisenum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.PRAISENUM=",p_praisenum);
	END IF;
	
	IF p_downloadnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.DOWNLOADNUM=",p_downloadnum);
	END IF;
	
	IF p_commentnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.COMMENTNUM=",p_commentnum);
	END IF;
	
	IF p_recomendnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RECOMENDNUM=",p_recomendnum);
	END IF;
	
	IF p_clicks IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.CLICKS=",p_clicks);
	END IF;
	
	IF p_storenum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.STORENUM=",p_storenum);
	END IF;
	
	IF p_res_score IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_SCORE=",p_res_score);
	END IF;
	
	IF p_net_share_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_SHARE_STATUS=",p_net_share_status);
	END IF;
	
	IF p_net_reportnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_REPORTNUM=",p_net_reportnum);
	END IF;
	
	IF p_net_praisenum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_PRAISENUM=",p_net_praisenum);
	END IF;
	
	IF p_net_downloadnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_DOWNLOADNUM=",p_net_downloadnum);
	END IF;
	
	IF p_net_commentnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_COMMENTNUM=",p_net_commentnum);
	END IF;
	
	IF p_net_recomendnum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_RECOMENDNUM=",p_net_recomendnum);
	END IF;
	
	IF p_net_clicks IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_CLICKS=",p_net_clicks);
	END IF;
	
	IF p_net_storenum IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.NET_STORENUM=",p_net_storenum);
	END IF;
	
	IF p_ctime IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.C_TIME>str_to_date('",p_ctime,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT aa.*,IFNULL(t.teacher_name,IFNULL(s.stu_name,u.user_name)) realname,
		(select count(*) FROM rs_resource_info where user_id=aa.user_id) resnum,
		IFNULL(sub.SUBJECT_NAME,'--') SUBJECT_NAME,
		IFNULL(g.GRADE_NAME,'--') GRADE_NAME,
		IFNULL(d1.DICTIONARY_NAME,'--') RES_TYPE_NAME,
		IFNULL(d2.DICTIONARY_NAME,'--') FILE_TYPE_NAME FROM (",tmp_sql,") aa 
		LEFT JOIN user_info u ON u.user_id=aa.user_id
		LEFT JOIN teacher_info t ON t.user_id=u.ref
		LEFT JOIN student_info s ON s.user_id=u.ref
		
		LEFT JOIN subject_info sub ON sub.SUBJECT_ID=aa.SUBJECT
		LEFT JOIN grade_info g ON g.GRADE_ID=aa.GRADE
		LEFT JOIN dictionary_info d1 ON d1.DICTIONARY_VALUE=aa.RES_TYPE AND d1.DICTIONARY_TYPE='RES_TYPE'  
		LEFT JOIN dictionary_info d2 ON d2.DICTIONARY_VALUE=aa.FILE_TYPE AND d2.DICTIONARY_TYPE='RES_FILE_TYPE'");
	
	SET @sql1=tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
    END$$

DELIMITER ;