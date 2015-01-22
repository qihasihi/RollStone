DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `notice_proc_add`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `notice_proc_add`(p_ref VARCHAR(60),
					 p_noticetitle VARCHAR(500),
					 p_noticecontent BLOB,
					 p_noticetype VARCHAR(500),					 
					 p_noticerole VARCHAR(3000),
					 p_noticegrade VARCHAR(3000),
					 p_istop INT,
					 p_clickcount INT,
					 p_begintime VARCHAR(50),
					 p_endtime VARCHAR(50),
					 p_cuserid VARCHAR(60),
					 p_titlelink VARCHAR(500),
					 p_istime INT,
					 p_dc_school_id INT,
					 OUT affect_row INT
					)
BEGIN
	
        DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
        DECLARE tmp_title VARCHAR(100) DEFAULT '';	
		DECLARE tmp_templateid INT;	
		 
	 DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"ref,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_ref,"',");
	END IF; 
	IF p_noticetitle IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"notice_title,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_noticetitle,"',");
	END IF; 
	IF p_noticecontent IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"notice_content,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_noticecontent,"',");
	END IF; 
	IF p_noticetype IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"notice_type,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_noticetype,"',");
	END IF; 
	IF p_noticerole IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"notice_role,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_noticerole,"',");
	END IF; 
	IF p_noticegrade IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"notice_grade,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_noticegrade,"',");
	END IF; 
	IF p_istop IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"is_top,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_istop,"',");
	END IF;
	IF p_clickcount IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"click_count,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_clickcount,"',");
	END IF;
	IF p_begintime IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"begin_time,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"STR_TO_DATE('",p_begintime,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	IF p_endtime IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"end_time,");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"STR_TO_DATE('",p_endtime,"','%Y-%m-%d %H:%i:%s'),");
	END IF;
	IF p_cuserid IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,"c_user_id");
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_cuserid,"'");
	END IF;
	IF p_titlelink IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,",title_link");
		SET tmp_value_sql = CONCAT(tmp_value_sql,",'",p_titlelink,"'");
	END IF;
	IF p_istime IS NOT NULL THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,",is_time");
		SET tmp_value_sql = CONCAT(tmp_value_sql,",",p_istime);
	END IF;
	
	IF p_dc_school_id IS NOT NULL AND p_dc_school_id>0 THEN
		SET tmp_column_sql = CONCAT(tmp_column_sql,",dc_school_id");
		SET tmp_value_sql = CONCAT(tmp_value_sql,",",p_dc_school_id);
	END IF;
	
	SET tmp_sql = CONCAT("insert into notice_info(c_time,",tmp_column_sql,") values(NOW(),",tmp_value_sql,")");
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
		
	SET affect_row = 1;	
			
		
		
			  SET tmp_title='公告';
			  SET tmp_templateid=10;		 
		
					
		
		IF p_noticerole IS NOT NULL THEN	
		BEGIN		
		DECLARE tmp_user_id VARCHAR(100);	
			DECLARE tmp_cursor CURSOR FOR 
			 SELECT DISTINCT user_id FROM j_role_user ru WHERE role_id IN (
				 SELECT DISTINCT role_id FROM identity_info WHERE 
				 FIND_IN_SET(identity_name,
					(SELECT REPLACE(notice_role,'|',',') FROM notice_info WHERE 1=1 AND ref=p_ref)
					)>0
				 ) AND EXISTS (
					SELECT 1 FROM user_info WHERE ref=ru.user_id AND dc_school_id=p_dc_school_id				
				 );
			
			
			OPEN tmp_cursor;
			tmp_noticloop:LOOP FETCH tmp_cursor INTO tmp_user_id;					
			       CALL j_myinfo_user_info_add_tongy(tmp_templateid,tmp_user_id,14,tmp_title,p_cuserid,CONCAT(p_noticetitle,'#ETIANTIAN_SPLIT#',p_ref),@officeRows);	
			       SET affect_row=@officeRows;      
			  END LOOP tmp_noticloop;
			 CLOSE tmp_cursor;
			 END;		 
		END IF;
		
		IF p_noticerole IS NULL THEN
		BEGIN
		DECLARE tmp_user_id VARCHAR(100);	
			DECLARE tmp_cursor CURSOR FOR SELECT DISTINCT ru.user_id FROM j_role_user ru,user_info u WHERE ru.user_id=u.ref AND dc_school_id=p_dc_school_id;
			OPEN tmp_cursor;
			tmp_noticloop:LOOP FETCH tmp_cursor INTO tmp_user_id;					
			       CALL j_myinfo_user_info_add_tongy(tmp_templateid,tmp_user_id,1,tmp_title,p_cuserid,CONCAT(p_noticetitle,'#ETIANTIAN_SPLIT#',p_ref),@officeRows);	
			       SET affect_row=@officeRows;      
			  END LOOP tmp_noticloop;
			 CLOSE tmp_cursor;
			 END;	
		END IF;
	
	
    END$$

DELIMITER ;