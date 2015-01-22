DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_theme_reply_info_bythemeidstr_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_theme_reply_info_bythemeidstr_split`(
					  p_theme_idstr VARCHAR(10000),			        
					  p_search_type INT,  -- 1:查评论  2：查回复
					  p_current_page INT(10),
					  p_page_size INT(10),
					  p_sort_column VARCHAR(1000),
					  OUT outNUm VARCHAR(1000)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE totaltheme_num INT DEFAULT 0;
	DECLARE tmp_themeid VARCHAR(100);
	DECLARE tmp_idx INT DEFAULT 1;
	IF p_theme_idstr IS NOT NULL AND LENGTH(p_theme_idstr)>0 THEN
	   SET totaltheme_num=get_split_string_total(p_theme_idstr,',');	
		IF totaltheme_num >0 THEN
		   WHILE tmp_idx<=totaltheme_num DO
			SET tmp_themeid=get_split_string(p_theme_idstr,',',tmp_idx);
			IF LENGTH(tmp_sql)>0 THEN 
			 SET tmp_sql=CONCAT(tmp_sql,' UNION  ALL ');
			END IF;
			SET tmp_sql=CONCAT(tmp_sql,'(SELECT * FROM tp_theme_reply_info WHERE  theme_id=',tmp_themeid);	
			IF p_search_type IS NULL OR p_search_type=1 THEN    -- 查主帖
				SET tmp_sql=CONCAT(tmp_sql,' AND to_replyid IS NULL ');
			   ELSE	   					     -- 查回复
			        SET tmp_sql=CONCAT(tmp_sql,' AND to_replyid IS NOT NULL ');
			END IF;
			IF p_sort_column IS NOT NULL AND LENGTH(p_sort_column)>0 THEN
				SET tmp_sql=CONCAT(tmp_sql,' ORDER BY ',p_sort_column);
			  ELSE
				SET tmp_sql=CONCAT(tmp_sql,' ORDER BY c_time');
			END IF;
			IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
			   SET tmp_sql=CONCAT(tmp_sql,' LIMIT ',((p_current_page-1)*p_page_size),',',(p_current_page*p_page_size));
			END IF;
			SET tmp_sql=CONCAT(tmp_sql,')');
			
			SET tmp_idx=tmp_idx+1;
		   END WHILE;
		END IF;
	
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
	IF p_search_type IS NOT NULL AND p_search_type<>1 THEN	-- 查回复时，必须按时间严格排序
		SET tmp_sql=CONCAT(tmp_sql,' ORDER BY c_time');
	END IF;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;
	-- 数量，分组得到
	SET tmp_idx=1;
	IF p_theme_idstr IS NOT NULL AND LENGTH(p_theme_idstr)>0 THEN
	   SET totaltheme_num=get_split_string_total(p_theme_idstr,',');	
		IF totaltheme_num >0 THEN
		   WHILE tmp_idx<=totaltheme_num DO
			SET tmp_themeid=get_split_string(p_theme_idstr,',',tmp_idx);	
			IF p_search_type IS NULL OR p_search_type=1 THEN		
				SELECT COUNT(*) INTO @trCount FROM tp_theme_reply_info WHERE  theme_id=tmp_themeid AND to_replyid IS NULL;
				SET outNUm=CONCAT(IF(outNUm IS NULL,'',CONCAT(outNUm,',')),tmp_themeid,'|',@trCount);			
			  ELSE
				SELECT COUNT(*) INTO @trCount FROM tp_theme_reply_info WHERE  theme_id=tmp_themeid AND to_replyid IS NOT NULL;
				SET outNUm=CONCAT(IF(outNUm IS NULL,'',CONCAT(outNUm,',')),tmp_themeid,'|',@trCount);			
			END IF;
			
			SET tmp_idx=tmp_idx+1;
		   END WHILE;
		END IF;
	
	END IF; 
    END$$

DELIMITER ;