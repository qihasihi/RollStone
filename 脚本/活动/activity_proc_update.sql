DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `activity_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `activity_proc_update`(p_ref VARCHAR(60),
                                           p_atname VARCHAR(60),
					   p_begintime VARCHAR(50),
					   p_endtime VARCHAR(50),					 
					   p_content VARCHAR(3000),
					   p_estimationnum INT,
					   p_audiovisual VARCHAR(3000),
					   p_issign INT,
					   p_state INT,
					   OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmpatname VARCHAR(60);
	DECLARE cuserid VARCHAR(60);
	DECLARE tmpissign BIGINT;
	DECLARE cusername VARCHAR(100);
	
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	
	DECLARE EXIT HANDLER FOR NOT FOUND SET affect_row=1;
	IF p_ref IS NULL THEN
		SET affect_row = 0;
	ELSE
		SET tmp_sql = 'update at_activity_info set m_time=NOW()';
		IF p_atname IS NOT NULL AND 
		   p_begintime IS NOT NULL AND
		   p_endtime IS NOT NULL AND
		   p_content IS NOT NULL AND
		   p_estimationnum IS NOT NULL AND
		   p_audiovisual IS NOT NULL AND 
		   p_issign IS NOT NULL THEN
		   
		       SET tmp_sql = CONCAT(tmp_sql,",at_name='",p_atname,"',begin_time = STR_TO_DATE('",p_begintime,"','%Y-%m-%d %H:%i:%s'),end_time = STR_TO_DATE('",p_endtime,"','%Y-%m-%d %H:%i:%s'),content='",p_content,"',estimationnum=",p_estimationnum,",audiovisual='",p_audiovisual,"',is_sign=",p_issign);
		       
		END IF;
		IF p_state IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",state=",p_state);
		END IF;
		SET tmp_sql = CONCAT(tmp_sql," where ref='",p_ref,"'");
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		SET affect_row = 1;
	END IF;
	IF affect_row >0 AND p_state<>2 THEN
		
	    SET tmp_sql=CONCAT("SELECT at_name,C_USER_ID,is_sign INTO @tmpatname,@cuserid,@issign FROM at_activity_info WHERE ref='",p_ref,"'");		
	    SET @tmp_sql1=tmp_sql;
	    PREPARE stmt1 FROM @tmp_sql1  ;
	    EXECUTE stmt1;
	    DEALLOCATE PREPARE stmt1;
	    SET cuserid=@cuserid;
	    SET tmpatname=@tmpatname;
	    SET tmpissign=@issign;
	    
	    SET tmp_sql=CONCAT("SELECT IFNULL(t.teacher_name,ifnull(s.stu_name,u.user_name)) INTO @realname FROM user_info u LEFT JOIN teacher_info t ON t.user_id=u.ref left join student_info s on s.user_id=u.ref WHERE u.ref='",cuserid,"'");		
	    SET @tmp_sql2=tmp_sql;
	    PREPARE stmt2 FROM @tmp_sql2  ;
	    EXECUTE stmt2;
	    DEALLOCATE PREPARE stmt2;	 
	    SET cusername=@realname;
	    
	    IF p_state=0 THEN
		
		CALL j_myinfo_user_info_add_tongy(3,cuserid,14,'通知',cuserid,CONCAT(tmpatname,'#ETIANTIAN_SPLIT#',p_ref,'|通过'),@officeRows);
		IF tmpissign=0 THEN 
			BEGIN
			DECLARE tmp_user_id VARCHAR(100) DEFAULT '';
			DECLARE tmp_cursor CURSOR FOR SELECT DISTINCT ref FROM user_info;
			OPEN tmp_cursor;
			tmp_taskloop:LOOP FETCH tmp_cursor INTO tmp_user_id;					
			       CALL j_myinfo_user_info_add_tongy(1,tmp_user_id,14,'通知',cuserid,CONCAT(cusername,'|',tmpatname,'#ETIANTIAN_SPLIT#',p_ref),@officeRows);					    
			  END LOOP tmp_taskloop;
			 CLOSE tmp_cursor;
			END;
		END IF;
		IF tmpissign=1 THEN 
			BEGIN
			DECLARE tmp_user_id VARCHAR(100) DEFAULT '';
			DECLARE tmp_cursor CURSOR FOR SELECT DISTINCT ref FROM at_j_activityuser_info au WHERE activity_id=p_ref;
			OPEN tmp_cursor;
			tmp_taskloop:LOOP FETCH tmp_cursor INTO tmp_user_id;					
			       CALL j_myinfo_user_info_add_tongy(1,tmp_user_id,14,'通知',cuserid,CONCAT(cusername,'|',tmpatname,'#ETIANTIAN_SPLIT#',p_ref),@officeRows);					 
			  END LOOP tmp_taskloop;
			 CLOSE tmp_cursor;
			END;
		END IF;		
	    END IF;
	    IF p_state=1 THEN
		 CALL j_myinfo_user_info_add_tongy(3,cuserid,14,'通知',cuserid,CONCAT(tmpatname,'#ETIANTIAN_SPLIT#',p_ref,'|未通过'),@officeRows);	
	    END IF;		
	END IF;
END $$

DELIMITER ;
