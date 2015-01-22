DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_condition_proc_search_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `user_condition_proc_search_split`(p_year varchar(20),
								is_sel_for_stu int, 
								is_sel_for_jz int, 
								p_roleid_str varchar(500),
								p_subjectid_str varchar(500),
								p_clsid_str varchar(500),
								p_deptid_str varchar(500),
								p_jobid_str varchar(500),
								p_grade varchar(20),
								p_user_name varchar(20),
								p_identity_name varchar(20),
								p_current_page INT,
								p_page_size	INT,
								p_sort_column	VARCHAR(50),
								p_dc_school_id    int,
								OUT totalNum INT)
BEGIN
	DECLARE execute_sql VARCHAR(5000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(5000) DEFAULT '';
	declare t_insql varchar(200) default '';
	DECLARE tmp_sql_one VARCHAR(1000) DEFAULT '';
	declare tmp_count_sql varchar(5000) default '';
	
	
	set tmp_sql='SELECT t.* FROM (
			SELECT DISTINCT u.ref,u.user_id,u.user_name,u.state_id,u.c_time,u.m_time,
			       IFNULL(stu.stu_no,''-1'')stu_no,
			       IFNULL(IFNULL(tea.teacher_name,IFNULL(stu.stu_name,parent.parent_name)),u.user_name)realname,
			       IFNULL(tea.teacher_sex,IFNULL(stu.stu_sex,parent.parent_sex))sex,
			        tea.dept_name,
				stu.class_name,
				(SELECT GROUP_CONCAT(r.role_name) FROM role_info r,j_role_user ru WHERE r.ROLE_ID=ru.ROLE_ID AND ru.USER_ID=u.REF ) role_name,
			       ui.identity_name
			  FROM user_info u LEFT JOIN ( 
						SELECT DISTINCT
                     t.*,(SELECT GROUP_CONCAT(dept_name) FROM dept_info d,j_user_dept_info ud WHERE d.dept_id=ud.dept_id AND ud.USER_ID=t.USER_ID)dept_name
                   FROM  teacher_info t
                     INNER JOIN user_info u
                       ON t.user_id = u.ref
                     INNER JOIN j_user_identity_info ui
                       ON t.user_id = ui.user_id                       
                     INNER JOIN j_role_user ru
                       ON ru.user_id = t.user_id
                     LEFT JOIN j_user_subject s
                       ON t.USER_ID = s.user_id
                     LEFT JOIN j_user_dept_info ud
                       ON ud.user_id = t.user_id
                     LEFT JOIN j_user_job j
                       ON j.user_id = t.user_id
                   WHERE 1 = 1';
					 
		if p_subjectid_str is not null then
			SET t_insql=get_split_insql(p_subjectid_str,",");
			set tmp_sql=concat(tmp_sql," and s.subject_id ",t_insql);
		end if;	
		
		IF p_dc_school_id>0  THEN
			
			SET tmp_sql=CONCAT(tmp_sql," and u.dc_school_id= ",p_dc_school_id);
		END IF;	
		
		if p_roleid_str is not null then
			SET t_insql=get_split_insql(p_roleid_str,",");
			SET tmp_sql=CONCAT(tmp_sql," and (ru.role_id ",t_insql," or ud.role_id ",t_insql,")");
		end if;
		
		IF p_deptid_str IS NOT NULL THEN
			SET t_insql=get_split_insql(p_deptid_str,",");
			SET tmp_sql=CONCAT(tmp_sql," and ud.dept_id ",t_insql);
		END IF;
		
		IF p_jobid_str IS NOT NULL THEN
			SET t_insql=get_split_insql(p_jobid_str,",");
			SET tmp_sql=CONCAT(tmp_sql," and j.job_id ",t_insql);
		END IF;
		
		IF p_identity_name IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql," and ui.identity_name='",p_identity_name,"'");
		END IF;
		
		
		
		if p_user_name is not null then
			SET tmp_sql=CONCAT(tmp_sql," and (u.user_name like '%",p_user_name,"%' or t.teacher_name like '%",p_user_name,"%')");
		end if;
		
		set tmp_sql=concat(tmp_sql,' ) tea ON tea.user_id=u.ref LEFT JOIN (');
		
		
		SET tmp_sql=CONCAT(tmp_sql,' SELECT DISTINCT
                     stu.*,(SELECT GROUP_CONCAT(CONCAT(CLASS_GRADE,CLASS_NAME)) FROM class_info c,j_class_user cu WHERE c.class_id=cu.class_id AND cu.USER_ID=stu.user_id and year=(
			SELECT class_year_value FROM class_year_info WHERE b_time <NOW() AND e_time >NOW() LIMIT 1
                     )) class_name
                   FROM student_info stu
                     INNER JOIN user_info u
                       ON stu.user_id = u.ref
                     INNER JOIN j_user_identity_info ui
                       ON stu.user_id = ui.user_id
                     INNER JOIN j_class_user cu
                       ON cu.user_id = stu.user_id
                     INNER JOIN class_info c
                       ON c.class_id = cu.class_id
                     INNER JOIN j_role_user ru
                       ON ru.user_id = stu.user_id
                     LEFT JOIN j_user_dept_info ud
                       ON ud.user_id = stu.user_id
                     LEFT JOIN j_user_job j
                       ON j.user_id = stu.user_id
                   WHERE 1 = 1 ');
	if p_year is not null then
		set tmp_sql =concat(tmp_sql," and c.year='",p_year,"'");
	end if;
		
	IF p_dc_school_id>0  THEN
			
		SET tmp_sql=CONCAT(tmp_sql," and u.dc_school_id= ",p_dc_school_id);
	END IF;	
	
	
	IF p_roleid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_roleid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and (ru.role_id ",t_insql," or ud.role_id ",t_insql,")");
	END if;
	
	IF p_deptid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_deptid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and ud.dept_id ",t_insql);
	END IF;
		
	IF p_jobid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_jobid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and j.job_id ",t_insql);
	END IF;
	
	IF p_identity_name IS NOT NULL THEN
		SET tmp_sql =CONCAT(tmp_sql," and ui.identity_name='",p_identity_name,"'");
	END IF;
		
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and (u.user_name like '%",p_user_name,"%' or stu.stu_name like '%",p_user_name,"%')");
	END IF;
	
	IF p_clsid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_clsid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and c.class_id ",t_insql);
	END if;
	
	if p_grade is not null then
		set tmp_sql=concat(tmp_sql," and c.class_grade='",p_grade,"'");
	end if;
	
	SET tmp_sql=CONCAT(tmp_sql,' ) stu ON stu.user_id=u.ref LEFT JOIN (');
	
	
	SET tmp_sql =CONCAT(tmp_sql," SELECT p.* 
					FROM user_info u INNER JOIN j_user_identity_info ui on u.ref=ui.user_id
					INNER JOIN parent_info p ON p.user_id=u.ref 
					INNER JOIN j_role_user ru ON ru.user_id=u.ref 
					LEFT JOIN j_user_dept_info ud on ud.user_id=u.ref
					LEFT JOIN j_user_job j on  j.user_id=u.ref where 1=1 ");
	
	IF p_roleid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_roleid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and (ru.role_id ",t_insql," or ud.role_id ",t_insql,")");
	END if;
	
	IF p_deptid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_deptid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and ud.dept_id ",t_insql);
	END IF;
		
	IF p_jobid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_jobid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and j.job_id ",t_insql);
	END IF;
	
	IF p_identity_name IS NOT NULL THEN
		SET tmp_sql =CONCAT(tmp_sql," and ui.identity_name='",p_identity_name,"'");
	END IF;
	
	IF p_user_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and (u.user_name like '%",p_user_name,"%' or p.parent_name like '%",p_user_name,"%')");
	END IF;
	
	set tmp_sql=concat(tmp_sql," ) parent on parent.user_id=u.ref INNER JOIN j_user_identity_info ui on u.ref=ui.user_id ");
	SET tmp_sql=CONCAT(tmp_sql," LEFT JOIN j_role_user ru ON ru.user_id=u.ref 
				     LEFT JOIN j_class_user cu ON cu.user_id=u.ref 
				     LEFT JOIN class_info c ON c.class_id=cu.class_id 
				     LEFT JOIN j_user_dept_info ud on ud.user_id=u.ref
				     LEFT JOIN j_user_job j on  j.user_id=u.ref
				     LEFT JOIN j_user_subject s on s.user_id=u.ref
				     LEFT JOIN role_info r ON ru.ROLE_ID=r.ROLE_ID where 1=1 ");
	
	IF p_year IS NOT NULL THEN
		SET tmp_sql =CONCAT(tmp_sql," and c.year='",p_year,"'");
	END IF;
	
	
	IF p_dc_school_id>0  THEN
		
		SET tmp_sql=CONCAT(tmp_sql," and u.dc_school_id= ",p_dc_school_id);
	END IF;	
	
	IF p_subjectid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_subjectid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and s.subject_id ",t_insql);
	END IF;	
		
	IF p_roleid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_roleid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and (ru.role_id ",t_insql," or ud.role_id ",t_insql,")");
	END IF;
		
	IF p_deptid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_deptid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and ud.dept_id ",t_insql);
	END IF;
		
	IF p_jobid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_jobid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and j.job_id ",t_insql);
	END IF;
	
	IF p_identity_name IS NOT NULL THEN
		SET tmp_sql =CONCAT(tmp_sql," and ui.identity_name='",p_identity_name,"'");
	END IF;
		
		
	IF p_user_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and (u.user_name like '%",p_user_name,"%' or tea.teacher_name like '%",p_user_name,"%' or stu.stu_name like '%",p_user_name,"%' or parent.parent_name like '%",p_user_name,"%')");
	END IF;
	
	IF p_clsid_str IS NOT NULL THEN
		SET t_insql=get_split_insql(p_clsid_str,",");
		SET tmp_sql=CONCAT(tmp_sql," and c.class_id ",t_insql);
	END IF;
	
	IF p_grade IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and c.class_grade='",p_grade,"'");
	END IF;	
		
	
	SET tmp_sql=CONCAT(tmp_sql," GROUP BY u.ref,u.user_id,u.user_name,u.state_id,u.c_time,u.m_time )t ");
	
	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	
        set tmp_count_sql=tmp_sql;	
	
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	
	SET @t_sql =tmp_sql;
	PREPARE s1 FROM @t_sql;
	EXECUTE	s1;
	DEALLOCATE PREPARE s1;
	
	
	SET execute_sql=CONCAT(execute_sql," select count(distinct user_id) into @totalcount from (",tmp_count_sql,")t");
	
	SET @t_sql1 =execute_sql;
	PREPARE s2 FROM @t_sql1;
	EXECUTE	s2;
	DEALLOCATE PREPARE s2;
	SET totalNum=@totalcount; 
END $$

DELIMITER ;
