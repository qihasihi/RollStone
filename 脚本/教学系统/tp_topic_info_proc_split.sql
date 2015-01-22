DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_topic_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_topic_info_proc_split`(
				          p_order_idx INT,
				            p_topic_keyword VARCHAR(1000),
				            p_status INT,
				              p_topic_title VARCHAR(1000),
				              p_c_user_id INT,
				              p_topic_content VARCHAR(1000),
				               p_course_id BIGINT,
				            p_cloud_status INT,
				            p_topic_id BIGINT,
				            p_login_user_id INT,
				            p_selectType INT,   
				            p_quote_id BIGINT,
				            p_taskflag INT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column INT(1),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " u.*";
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_topic_info u'; 
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_column=CONCAT(tmp_search_column,",(select count(*) from tp_topic_info tt where tt.STATUS<>3 and u.topic_id=tt.topic_id and tt.topic_id  IN (SELECT t.TASK_VALUE_ID FROM tp_task_info t,tp_task_allot_info ta WHERE t.task_id =ta.task_id and t.course_id=",p_course_id,"))flag ");  
	END IF;
	
	
	
	
	IF p_selectType IS NULL OR p_selectType<>2 THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND NOT EXISTS
									(SELECT REF FROM tp_operate_info WHERE COURSE_ID=u.course_id AND target_id=u.topic_id and operate_type=1 ");	
	
	       IF p_login_user_id IS NOT NULL THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," AND c_user_id=",p_login_user_id);
		END IF;								
		SET tmp_search_condition=CONCAT(tmp_search_condition,")");
	END IF;
	
	IF p_topic_keyword IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TOPIC_KEYWORD='",p_topic_keyword,"'");
	END IF;
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.ORDER_IDX=",p_order_idx);
	END IF;
	IF p_quote_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.QUOTE_ID=",p_quote_id);
	END IF;
	
	IF p_topic_title IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TOPIC_TITLE LIKE '%",p_topic_title,"%'");
	END IF;	
	
	IF p_topic_content IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TOPIC_CONTENT='",p_topic_content,"'");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.C_USER_ID=",p_c_user_id);
	END IF;
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.COURSE_ID=",p_course_id);
	END IF;
	
	IF p_cloud_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.CLOUD_STATUS=",p_cloud_status);
	END IF;
	
	IF p_status IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.STATUS=",p_status);
		IF p_status=3 THEN 
		   SET tmp_search_condition=CONCAT(tmp_search_condition," AND m_time BETWEEN DATE_SUB(NOW(),INTERVAL 1 MONTH) AND NOW() ");
		END IF;
	END IF;
	
	IF p_topic_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TOPIC_ID=",p_topic_id);
	END IF;
	
	IF p_taskflag IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and not exists (SELECT 1 FROM tp_task_allot_info ta,tp_task_info t WHERE ta.task_id=t.task_id AND t.course_id=u.course_id AND t.task_value_id=u.topic_id)");
	END IF;
	
	
	IF p_status IS NULL THEN
		IF p_selectType IS NOT NULL THEN
			IF p_selectType=1 THEN
				SET tmp_search_condition=CONCAT(tmp_search_condition,"  and u.STATUS<>3");
			END IF;
			
			
		END IF;
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	    ELSE
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  c_time desc");
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	SET tmp_sql=CONCAT("SELECT u.*,(SELECT teacher_name FROM teacher_info t,user_info u1 WHERE u1.ref=t.user_id AND u1.USER_ID=u.c_user_id) crealname,
			(SELECT COUNT(task_id) FROM tp_task_info where task_type=2 AND TASK_VALUE_ID=u.TOPIC_ID) isPublishTask,
	
			(SELECT COUNT(theme_id) FROM tp_topic_theme_info WHERE topic_id=u.topic_id AND quote_id IS NULL) themecount,
			(SELECT COUNT(reply_id) FROM tp_theme_reply_info where topic_id=u.topic_id AND to_replyid IS NULL) restorecount,
			(
			
			  SELECT CONCAT(tt.theme_title,' ',IFNULL(t.TEACHER_NAME,IFNULL(s.STU_NAME,u1.USER_NAME)),' ',DATE_FORMAT(tt.c_time,'%Y-%m-%d %H:%I:%S')) 
			  FROM tp_topic_theme_info tt 
			  LEFT JOIN user_info u1 ON u1.USER_ID=tt.c_user_id
			  LEFT JOIN teacher_info t ON t.user_id=u1.ref
			  LEFT JOIN student_info s ON s.user_id=u1.ref
			  WHERE tt.TOPIC_ID=u.TOPIC_ID ORDER BY tt.c_time DESC LIMIT 0,1
			
			 )lastFb			
			  FROM (	
	 ",tmp_sql," ) u");
	
	
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