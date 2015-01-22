DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `notice_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `notice_proc_update`(p_ref VARCHAR(60),
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
					 p_istime int,
					 OUT affect_row INT)
BEGIN
	
        DECLARE tmp_column_sql VARCHAR(2000) DEFAULT '';
        DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
		 
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row = 0;
	
	IF p_ref IS NOT NULL THEN
		IF p_noticetitle IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,"notice_title='",p_noticetitle,"'");
		END IF; 
		IF p_noticecontent IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",notice_content='",p_noticecontent,"'");
		END IF; 
		IF p_noticetype IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",notice_type='",p_noticetype,"'");
		END IF; 
		IF p_noticerole IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",notice_role='",p_noticerole,"'");
		END IF; 
		IF p_noticegrade IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",notice_grade='",p_noticegrade,"'");
		END IF; 
		IF p_istop IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",is_top='",p_istop,"'");
		END IF;
		IF p_clickcount IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",click_count='",p_clickcount,"'");
		END IF;
		IF p_begintime IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",begin_time=STR_TO_DATE('",p_begintime,"','%Y-%m-%d %H:%i:%s')");
		END IF;
		IF p_endtime IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",end_time=STR_TO_DATE('",p_endtime,"','%Y-%m-%d %H:%i:%s')");
		END IF;
		
		IF p_titlelink IS NOT NULL THEN
			set tmp_column_sql = CONCAT(tmp_column_sql,",title_link='",p_titlelink,"'");
		END IF;
		IF p_istime IS NOT NULL THEN
			SET tmp_column_sql = CONCAT(tmp_column_sql,",is_time=",p_istime);
		END IF;
		
		SET tmp_sql = CONCAT("update  notice_info set m_time=NOW(),",tmp_column_sql," where ref='",p_ref,"'");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
			
		SET affect_row = 1;
	else
		set affect_row = 0;
	END IF; 
	
	
	
END $$

DELIMITER ;
