DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_resource_base_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_resource_base_info_proc_split`(
					   p_ref VARCHAR(50),
				          p_user_id VARCHAR(50),
				          p_res_state INT,
				          p_user_type INT,
				          p_course_id VARCHAR(50),
				          p_res_name VARCHAR(1000),
				          p_res_keyword VARCHAR(1000),
				          p_resource_id VARCHAR(50),
				          p_real_name VARCHAR(100),
				          p_isunion INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE t_course_str VARCHAR(20000) DEFAULT '';
	
	
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' (select count(res_id)
          from tp_resource_view v
         where v.res_id = u.resource_id
           and v.course_id = u.course_id) viewcount,
      round(ifnull((select avg(score)
          from tp_resource_rank r
         where r.res_id = u.resource_id
           and r.course_id = u.course_id
         group by r.res_id),0))rankscore,u.*,tc.course_name,f.file_name,f.path 	filepath,f.file_size,ifnull(tt.teacher_name,s.stu_name)realname';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_resource_base_info u inner join tp_teacher_course_info tc on u.course_id=tc.course_id
	 inner join rs_resource_info r on r.res_id=u.resource_id inner join rs_resource_file_info f on u.resource_id=f.res_id inner join user_info uu on u.user_id =uu.ref
	 left join student_info s on u.user_id=s.user_id left join teacher_info tt on tt.user_id=u.user_id ';
	  
	BEGIN
		DECLARE tmp_cobj_id VARCHAR(50) DEFAULT '';
		DECLARE cursor_done INT DEFAULT 0;
		DECLARE CUR_TP_COURSE_ID CURSOR FOR SELECT t.COURSE_ID FROM tp_teacher_course_info t,teacher_info tea WHERE tea.user_id=t.user_id AND tea.user_id=p_user_id;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done=1;
		
		 IF p_isunion IS NOT NULL AND p_user_id IS NOT NULL THEN
			OPEN CUR_TP_COURSE_ID;
			cobj :LOOP
				FETCH CUR_TP_COURSE_ID INTO tmp_cobj_id;
				IF cursor_done=1 THEN
					LEAVE cobj;
				END IF;
				SET t_course_str=CONCAT(t_course_str,"'",tmp_cobj_id,"',");
			END LOOP cobj;
		END IF;
		SET t_course_str=SUBSTRING_INDEX(t_course_str,",",-1);
        END;
     
	
	IF p_user_id IS NOT NULL AND  p_isunion IS NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_res_state IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RES_STATE=",p_res_state);
	END IF;
	
	
	IF p_user_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_TYPE=",p_user_type);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REF='",p_ref,"'");
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID='",p_course_id,"'");
	END IF;
	
	IF p_res_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RES_NAME like '%",p_res_name,"%'");
	END IF;
	
	IF p_res_keyword IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RES_KEYWORD='",p_res_keyword,"'");
	END IF;
	
	
	IF p_resource_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.RESOURCE_ID='",p_resource_id,"'");
	END IF;
	
	IF p_real_name IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and (uu.user_name like '%",p_real_name,"%' or tt.teacher_name like '%",p_real_name,"%' or s.stu_name like '%",p_real_name,"%')");
	END IF;
	
        IF p_isunion IS NOT NULL AND LENGTH(t_course_str)>0 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND tc.course_id in (",t_course_str,")");
        END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
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
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
