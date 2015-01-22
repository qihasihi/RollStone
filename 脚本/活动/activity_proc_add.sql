DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_proc_add`(p_ref VARCHAR(60),
                                           p_atname VARCHAR(60),
					   p_begintime VARCHAR(50),
					   p_endtime VARCHAR(50),
					   p_userid VARCHAR(60),					 
					   p_content VARCHAR(3000),
					   p_estimationnum INT,
					   p_audiovisual VARCHAR(3000),
					   p_issign INT,
					   OUT affect_row INT)
BEGIN
	
        DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
        
        DECLARE tmp_crealname VARCHAR(1000);
		 
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row=1;
	IF p_ref IS NOT NULL AND
	   p_atname IS NOT NULL AND
	   p_begintime IS NOT NULL AND
	   p_endtime IS NOT NULL AND
	   p_content IS NOT NULL AND
	   p_estimationnum IS NOT NULL AND
	   p_audiovisual IS NOT NULL and
	   p_issign is not null THEN
	   
		
		
			
		SET tmp_column_sql = 'ref,at_name,begin_time,end_time,c_time,c_user_id,content,estimationnum,audiovisual,is_sign';
		
		SET tmp_value_sql = CONCAT(tmp_value_sql,"'",p_ref,"','",p_atname,"',STR_TO_DATE('",p_begintime,"','%Y-%m-%d %H:%i:%s'),STR_TO_DATE('",p_endtime,"','%Y-%m-%d %H:%i:%s'),NOW(),'",p_userid,"','",p_content,"',",p_estimationnum,",'",p_audiovisual,"',",p_issign);
		
		SET tmp_sql = CONCAT("INSERT INTO at_activity_info (",tmp_column_sql,") VALUES (",tmp_value_sql,")");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
			
	    SET affect_row = 1;
	ELSE
		
	    SET affect_row = 0;
	END IF;	
	IF affect_row>0 THEN
		
		SET tmp_sql=CONCAT("SELECT IFNULL(IFNULL(tea.teacher_name,stu.stu_name),u.USER_NAME) INTO @tmp_crealname FROM user_info u LEFT JOIN teacher_info tea ON tea.user_id=u.ref LEFT JOIN student_info stu ON stu.user_id=u.ref WHERE u.REF= '",p_userid,"'");		
		SET @tmp_sql1=tmp_sql;
		PREPARE stmt1 FROM @tmp_sql1  ;
		EXECUTE stmt1;
		DEALLOCATE PREPARE stmt1;
		SET tmp_crealname=@tmp_crealname;	
		
			BEGIN		
				DECLARE tmp_user_id VARCHAR(100);	
				
				
				DECLARE tmp_cursor CURSOR FOR SELECT upr.USER_ID FROM j_user_page_right upr,page_right_info pr
					WHERE upr.PAGE_RIGHT_ID=pr.PAGE_RIGHT_ID AND pr.PAGE_VALUE='activity?m=adminlist';
				OPEN tmp_cursor;
				tmp_noticloop:LOOP FETCH tmp_cursor INTO tmp_user_id;					
				       CALL j_myinfo_user_info_add_tongy(2,tmp_user_id,3,'…Û∫À',p_userid,CONCAT(tmp_crealname,'|',p_atname),@officeRows);	
				       SET affect_row=@officeRows;      
				  END LOOP tmp_noticloop;
				 CLOSE tmp_cursor;
			 END;	
	END IF;
END $$

DELIMITER ;
