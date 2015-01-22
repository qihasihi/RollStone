DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_theme_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_topic_theme_info_proc_split`(
				          p_view_count INT,
						  p_c_user_id BIGINT,
						  p_comment_user_id INT,
						  p_cloud_status INT,
						  p_is_essence INT,
						  p_theme_title VARCHAR(1000),
						  p_theme_content VARCHAR(1000),
						  p_comment_m_time DATETIME,
						  p_comment_title VARCHAR(1000),						  
						    p_theme_id BIGINT,
						    p_course_id BIGINT,
						    p_is_top INT,				            				          
						    p_topic_id BIGINT, 
						    p_comment_content VARCHAR(1000),
						    p_login_user_ref VARCHAR(100),
							p_selectType INT,  /*1:不查 text  2:查text  -3:不查text，并且quote_id IS NULL  -4:不查text 并且quote_id IS NULL*/
							p_quote_id BIGINT,
							p_status BIGINT,
							p_cls_id BIGINT,
							p_cls_type BIGINT,
							p_role_type INT,/*角色*/
							 p_imattach VARCHAR(5000),
							    p_attachtype INT,
							    source_id INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(21845) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(20000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(20000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_topic_theme_info u'; 
	DECLARE tmp_sort_name VARCHAR(50) DEFAULT ' is_top DESC,c_time desc';
	DECLARE tmp_theme_sql VARCHAR(10000) DEFAULT '';
		
	IF p_selectType IS  NULL OR p_selectType=1  THEN
		SET tmp_search_column=" theme_id,course_id,topic_id,theme_title,comment_user_id,comment_title,comment_m_time,cloud_status,is_top,is_essence,view_count,c_user_id,c_time,m_time,praise_count,pinglunshu,lastfabiao,quote_id ";
	END IF;
	IF p_theme_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.THEME_ID=",p_theme_id);
	END IF;
	IF p_imattach IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.im_attach='",p_imattach,"'");
	END IF;
	IF p_attachtype IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.im_attach_type=",p_attachtype);
	END IF;
	IF source_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.source_id=",source_id);
	END IF;
		
	IF p_cls_id IS NOT NULL AND p_cls_type IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.c_user_id IN(");
			
		IF p_cls_type=1 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," (SELECT distinct user_id FROM user_info where ref In (SELECT user_id FROM j_class_user WHERE class_id=",p_cls_id,"))");  
		ELSEIF p_cls_type=2 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," (SELECT distinct user_id FROM tp_j_virtual_class_student WHERE VIRTUAL_CLASS_ID=",p_cls_id,")");  
		END IF;	
		
		SET tmp_search_condition=CONCAT(tmp_search_condition,")");
	END IF;
	/*IF p_role_type IS NOT NULL AND p_role_type=2 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND c_user_id IN (				
				SELECT DISTINCT user_id FROM (SELECT user_id FROM user_info WHERE ref IN (
				SELECT user_id FROM j_class_user WHERE class_id IN (
					SELECT DISTINCT class_id FROM j_class_user WHERE user_id='",p_login_user_ref,"'
					)
				)
				UNION
				SELECT distinct user_id FROM tp_j_virtual_class_student WHERE VIRTUAL_CLASS_ID IN(
					SELECT VIRTUAL_CLASS_ID FROM tp_j_virtual_class_student WHERE user_id=(SELECT user_id FROM user_info WHERE ref='",p_login_user_ref,"')
				)
				)t
			)");
	END IF;*/
	IF p_quote_id IS NOT NULL THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUOTE_ID=",p_quote_id);
	END IF;
	IF p_status IS NOT NULL THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.STATUS=",p_status);
	END IF;
	IF p_quote_id IS NULL THEN
	   IF p_selectType IS NOT NULL AND p_selectType=-3 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and (u.QUOTE_ID IS NULL OR u.QUOTE_ID=0) ");
	   END IF;
	   IF p_selectType IS NOT NULL AND p_selectType=-4 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUOTE_ID IS NOT NULL AND u.QUOTE_ID <>0 ");
	   END IF;
	END IF;
	
	IF p_comment_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COMMENT_USER_ID=",p_comment_user_id);
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID='",p_c_user_id,"'");
	END IF;
	
	IF p_theme_content IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.THEME_CONTENT='",p_theme_content,"'");
	END IF;
	
	IF p_is_top IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.IS_TOP=",p_is_top);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_view_count IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.VIEW_COUNT=",p_view_count);
	END IF;
	
	IF p_topic_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TOPIC_ID=",p_topic_id);
	END IF;
	
	IF p_comment_m_time IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COMMENT_M_TIME=",p_comment_m_time);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		IF p_cloud_status=-3 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS<>3");
		   ELSE
		       SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS=",p_cloud_status);
		END IF;
	END IF;
	
	
	IF p_is_essence IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.IS_ESSENCE=",p_is_essence);
	END IF;
	
	IF p_comment_content IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COMMENT_CONTENT='",p_comment_content,"'");
	END IF;
	
	
	IF p_theme_title IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.THEME_TITLE='",p_theme_title,"'");
	END IF;
	
	IF p_comment_title IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COMMENT_TITLE='",p_comment_title,"'");
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN 
		SET tmp_sort_name=p_sort_column;
	END IF;
	
	IF LENGTH(tmp_sort_name)>0 THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",tmp_sort_name);		
	END IF;
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
		    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
		END IF;
	IF p_selectType IS NOT NULL AND p_selectType<>3 THEN
		SET tmp_theme_sql=CONCAT("SELECT t.*,
			t.pinglunshu restorecount
			,cu.HEAD_image cheadimage,cu.user_name cusername
			,IF(ct.TEACHER_NAME IS NULL,'学生','教师')  croleType ");
	IF p_theme_id IS NOT NULL THEN
	       SET tmp_theme_sql=CONCAT(tmp_theme_sql,"
			,(SELECT COUNT(t1.theme_id) FROM tp_topic_theme_info t1 where  t1.c_user_id=t.c_user_id AND t1.course_id IN (
				SELECT COURSE_ID FROM tp_j_course_class cc
				,(SELECT term_id,grade_id,subject_id FROM tp_j_course_class cc WHERE course_id= (SELECT course_id FROM tp_topic_theme_info WHERE theme_id=",p_theme_id,")) t
				WHERE cc.term_id=t.term_id AND cc.grade_id=t.grade_id AND cc.subject_id=t.subject_id	
			)) cfatieshu
			,(SELECT COUNT(rt1.reply_id) FROM tp_theme_reply_info rt1 WHERE rt1.user_id=t.c_user_id AND theme_id IN (
				SELECT theme_id FROM tp_topic_theme_info WHERE course_id IN (
				SELECT COURSE_ID FROM tp_j_course_class cc
				,(SELECT term_id,grade_id,subject_id FROM tp_j_course_class cc WHERE course_id=(SELECT course_id FROM tp_topic_theme_info WHERE theme_id=",p_theme_id,")) t
				WHERE cc.term_id=t.term_id AND cc.grade_id=t.grade_id AND cc.subject_id=t.subject_id	
				)
			)) cpinglunshu"
			);
			
			
	END IF;
	SET tmp_sql=CONCAT(tmp_theme_sql,",IFNULL(ct.teacher_name,IFNULL(cs.STU_NAME,cu.USER_NAME)) crealname
			,(SELECT COUNT(opl.ref) FROM operate_log_info opl WHERE opl.operate_table='tp_topic_theme_info' AND opl.operate_rowsid=t.theme_id 
			AND opl.operate_user='",IFNULL(p_login_user_ref,0),"' AND operate_type='VIEW') isread
			,(SELECT COUNT(opl.ref) FROM operate_log_info opl WHERE opl.operate_table='tp_topic_theme_info' AND opl.operate_rowsid=t.theme_id 
			AND opl.operate_user='",IFNULL(p_login_user_ref,0),"' AND operate_type='PARISE') ispraise,
			c.school_name,
			(SELECT COUNT(theme_id) FROM tp_topic_theme_info where (quote_id IS NOT NULL AND quote_id<>0) AND theme_id=t.theme_id AND status=1) isquote			
			 ,(
				SELECT CONCAT(clas.class_grade,'|',clas.class_name,'|',group_name) FROM 
				j_class_user cu INNER JOIN user_info u1 ON u1.ref=cu.user_id 
				 INNER JOIN class_info clas ON cu.class_id=clas.class_id  AND clas.pattern='行政班'
				 LEFT JOIN 
				(				
				        SELECT group_name,g.subject_id,g.`CLASS_ID`,gs.`USER_ID` FROM tp_j_group_student gs,tp_group_info g
					WHERE gs.GROUP_ID=g.GROUP_ID 
				) s
				 ON  s.class_id=clas.`CLASS_ID` AND s.user_id=u1.user_id				 
				WHERE  clas.year=(SELECT YEAR FROM term_info WHERE NOW() BETWEEN semester_begin_date AND semester_end_date)
					AND u1.user_id=t.c_user_id LIMIT 0,1
			) classGroup
			 FROM 
			(",tmp_sql,") t
			INNER JOIN tp_course_info c ON c.course_id=t.course_id
			LEFT JOIN user_info cu ON cu.user_id=t.c_user_id
			LEFT JOIN teacher_info ct ON ct.user_id=cu.ref
			LEFT JOIN student_info cs ON cs.user_id=cu.ref
			
		");
	   
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
	
    END$$

DELIMITER ;