DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_proc_searchCheckBy_value`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_proc_searchCheckBy_value`(
				          p_res_id VARCHAR(50),
				          p_res_keyword VARCHAR(1000),
				          p_res_name VARCHAR(1000),
				          p_user_id INT,
				          p_user_ref VARCHAR(50),
				          p_res_state INT,
				          p_res_score INT,
				          p_school_id INT,
				          p_ctime VARCHAR(100),
				          p_values VARCHAR(4000),
				          p_current_login_ref VARCHAR(100),
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE ori_sql VARCHAR(10000) DEFAULT '';
	
	DECLARE tmp_search_column VARCHAR(1000) DEFAULT " a.*,IFNULL(IFNULL(si.STU_NAME,ti.TEACHER_NAME),u.USER_NAME) USERNAME,u.ref USERREF,
	getResourceExtendValueGroup(a.RES_ID) EXTENDVALUES";  
	
	DECLARE tmp_search_condition VARCHAR(8000) DEFAULT '';  
	DECLARE tmp_tbl_name VARCHAR(5000) DEFAULT 'rs_resource_info r'; 
	DECLARE tmp_join_tbl VARCHAR(1000) DEFAULT 'INNER JOIN user_info u 
	    ON u.USER_ID = a.USER_ID 
	  LEFT JOIN teacher_info ti 
	    ON ti.user_id = u.ref 
	  LEFT JOIN student_info si 
	    ON si.user_id = u.ref 
	  LEFT JOIN rs_resource_recommend rr 
	    ON rr.RES_ID = a.RES_ID'; 
	
	DECLARE numAND INT DEFAULT 0;
	DECLARE numOR INT DEFAULT 0;
	DECLARE idx_i INT DEFAULT 0;
	DECLARE idx_j INT DEFAULT 0;
	DECLARE n INT DEFAULT 0;
	DECLARE values_AND VARCHAR(2000) DEFAULT '';
	DECLARE value_temp VARCHAR(2000) DEFAULT '';
	DECLARE valueschild_condition VARCHAR(5000) DEFAULT '1=1';
	IF p_values IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," inner join(SELECT er0.RES_ID FROM ");
		SET numAND = get_split_string_total(p_values,'|');	
		WHILE idx_i<numAND DO
			
			IF LENGTH(TRIM(get_split_string(p_values,'|',idx_i+1)))>0 THEN
			
				SET idx_j=0;
				IF n>0 THEN
					SET tmp_search_condition=CONCAT(tmp_search_condition,",");
				END IF;
				
				SET tmp_search_condition=CONCAT(tmp_search_condition,"rs_extend_resource er",n);
				
				IF n>0 THEN
					SET valueschild_condition=CONCAT(valueschild_condition," and er",n-1,".RES_ID=er",n,".RES_ID");
				END IF;
				SET valueschild_condition=CONCAT(valueschild_condition," and er",n,".VALUE_ID IN(");
				SET values_AND=get_split_string(p_values,'|',idx_i+1);
				SET numOR = get_split_string_total(values_AND,',');	
				WHILE idx_j<numOR DO
					SET value_temp=get_split_string(values_AND,',',idx_j+1);
					IF idx_j>0 THEN
					SET valueschild_condition=CONCAT(valueschild_condition,",");
					END IF;
					SET valueschild_condition=CONCAT(valueschild_condition,getChildValueList(value_temp));
					SET idx_j=idx_j+1;
				END WHILE;
				SET valueschild_condition=CONCAT(valueschild_condition,")");
				SET n=n+1;
			END IF;
			SET idx_i=idx_i+1;
		END WHILE;
		
		SET tmp_search_condition=CONCAT(tmp_search_condition," WHERE ",valueschild_condition,") rl on rl.RES_ID=r.RES_ID");
	END IF;
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," WHERE 1=1");	
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_NAME like '%",p_res_name,"%'");
	END IF;
	
	IF p_res_keyword IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_KEYWORD='",p_res_keyword,"'");
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.SCHOOL_ID=",p_school_id);
	END IF;
	
	IF p_ctime IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.C_TIME>str_to_date('",p_ctime,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.USER_ID=",p_user_id);
	END IF;
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and r.RES_ID='",p_res_id,"'");
	END IF;	
	
	SET ori_sql=CONCAT(tmp_tbl_name,tmp_search_condition);
	
	SET tmp_sql=CONCAT("SELECT r.* FROM ",ori_sql);
	
	SET tmp_sql=CONCAT(tmp_sql," ORDER BY REPORTNUM DESC,C_TIME DESC");
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM (",tmp_sql,")a ",tmp_join_tbl);
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) into @tmp_sumCount from ",ori_sql);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
