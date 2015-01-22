DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_theme_reply_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_theme_reply_info_proc_split`(
				          p_theme_id BIGINT,
				          p_user_id BIGINT,
				          p_reply_id BIGINT,
				          p_topic_id BIGINT ,
				          p_toreply_id BIGINT,
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(1000),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' u.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_theme_reply_info u'; 
	
	IF p_theme_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.THEME_ID=",p_theme_id);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_ID=",p_user_id);
	END IF;	
	IF p_topic_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.TOPIC_ID=",p_topic_id);
	END IF;
	IF p_toreply_id IS NOT NULL THEN
		IF p_toreply_id=-1 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.to_replyid IS NULL");
		ELSE
			SET tmp_search_condition=CONCAT(tmp_search_condition," and u.to_replyid=",p_toreply_id);
		END IF;
	END IF;
	
	
	IF p_reply_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.REPLY_ID=",p_reply_id);
	END IF;
	
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_sort_column IS NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  c_time asc");
	END IF;	
	
	SET tmp_sql=CONCAT('SELECT * FROM (SELECT t.*,(@rownum:=@rownum+1) rownum FROM (',tmp_sql,') t,(SELECT @rownum:=0) b) t');
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	
	SET tmp_sql=CONCAT('
		SELECT t.*
		,IFNULL(ct.teacher_name,IFNULL(cs.STU_NAME,cu.USER_NAME)) crealname
		,cu.HEAD_image cheadimage
		FROM (',tmp_sql,') t 
		LEFT JOIN user_info cu ON cu.user_id=t.user_id
		LEFT JOIN teacher_info ct ON ct.user_id=cu.ref
		LEFT JOIN student_info cs ON cs.user_id=cu.ref
	
	');
	
	
	
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