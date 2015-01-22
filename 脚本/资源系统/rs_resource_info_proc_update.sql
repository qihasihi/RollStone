DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_update`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `rs_resource_info_proc_update`(
				          p_res_id VARCHAR(1000),
				          p_res_name VARCHAR(10000),
				          p_res_keyword VARCHAR(10000),
				          p_res_introduce VARCHAR(400000),
				          
				          p_user_id INT,
				          p_user_type INT,
				          p_user_name VARCHAR(1000),
				          p_school_name VARCHAR(1000),
				          
				          p_subject INT,
				          p_grade INT,
				          p_res_type INT,
				          p_file_type INT,
				         
				          p_file_size INT,
				          
				          p_res_status INT,
				          p_res_degree INT,
				          p_share_status INT,
				          p_use_object VARCHAR(1000),
				          
				          p_res_score FLOAT UNSIGNED,
				          p_reportnum INT,
				          p_storenum INT,
				          p_praisenum INT,
				          p_commentnum INT,
				          p_downloadnum INT,
				          p_clicks INT,
				          p_recomendnum INT,
				          p_filesuffixname VARCHAR(100),
				          p_convert_status INT,
				          p_difftype INT,
				          p_ismicopiece INT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(10000000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE rs_resource_info set m_time=NOW()';
	IF p_res_id IS NOT NULL THEN
		
		IF p_res_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RES_NAME='",p_res_name,"'");
		END IF;
		
		IF p_res_keyword IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RES_KEYWORD='",p_res_keyword,"'");
		END IF;
		IF p_difftype IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",DIFF_TYPE=",p_difftype);
		END IF;
		
		IF p_res_introduce IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RES_INTRODUCE='",p_res_introduce,"'");
		END IF;
		
		IF p_user_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",USER_ID=",p_user_id);
		END IF;
		
		IF p_convert_status IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",convert_status=",p_convert_status);
		END IF;
		
		
		IF p_user_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",USER_TYPE=",p_user_type);
		END IF;
		
		IF p_user_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",USER_NAME='",p_user_name,"'");
		END IF;
		
		IF p_school_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SCHOOL_NAME='",p_school_name,"'");
		END IF;
		
		IF p_subject IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SUBJECT=",p_subject);
		END IF;
		
		IF p_grade IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",GRADE=",p_grade);
		END IF;
			
		IF p_res_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RES_TYPE=",p_res_type);
		END IF;
		
		IF p_file_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",FILE_TYPE=",p_file_type);
		END IF;
		-- 是否碎片化
		IF p_ismicopiece IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",is_mic_copiece=",p_ismicopiece);
		END IF;	
		
		
		IF p_file_size IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",FILE_SIZE=",p_file_size);
		END IF;
		
		IF p_res_status IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RES_STATUS=",p_res_status);
		END IF;
		
		IF p_res_degree IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RES_DEGREE=",p_res_degree);
		END IF;
		
		IF p_share_status IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",SHARE_STATUS=",p_share_status);
		END IF;
		
		IF p_use_object IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",USE_OBJECT='",p_use_object,"'");
		END IF;
		
		IF p_res_score IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RES_SCORE=",p_res_score);
		END IF;
		
		IF p_reportnum IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",REPORTNUM=",p_reportnum);
		END IF;
		
		IF p_storenum IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",STORENUM=",p_storenum);
		END IF;
		
		IF p_praisenum IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",PRAISENUM=",p_praisenum);
		END IF;
		
		IF p_commentnum IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",COMMENTNUM=",p_commentnum);
		END IF;
		
		IF p_downloadnum IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",DOWNLOADNUM=",p_downloadnum);
		END IF;
		
		IF p_clicks IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",CLICKS=",p_clicks);
		END IF;
		
		IF p_recomendnum IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",RECOMENDNUM=",p_recomendnum);
		END IF;
		IF p_filesuffixname IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",FILE_SUFFIXNAME='",p_filesuffixname,"'");
		END IF;
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE RES_ID='",p_res_id,"'"); 
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
    END$$

DELIMITER ;