DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_auto_level_up`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `class_auto_level_up`(p_year VARCHAR(100),p_dc_school_id BIGINT,out afficeRows INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';	
	
	DECLARE tmp_nextyear VARCHAR(100) DEFAULT '';
	DECLARE tmp_clsname VARCHAR(100) DEFAULT '';
	DECLARE tmp_clsgrade varchar(100) DEFAULT '';
	DECLARE tmp_type VARCHAR(100) DEFAULT '';
	declare tmp_classid INT;
	
	DECLARE tmp_clsid INT;
	
	DECLARE tmp_clscount INT;
	
	
	
	DECLARE cur_currentCls CURSOR FOR
	 SELECT class_name,class_grade,type,class_id FROM class_info WHERE class_grade<>'高三' AND class_grade<>'初三' AND class_grade <>'小学六年级' AND YEAR=p_year  AND pattern='行政班' AND isflag=1 AND dc_school_id=p_dc_school_id;
	
				
				   
	
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET afficeRows=-1;
	   
	
	
	 
	 SET tmp_sql=CONCAT("SELECT DISTINCT YEAR INTO @nextyear FROM term_info WHERE YEAR>'",p_year,"'");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET tmp_nextyear=@nextyear; 
	IF length(tmp_nextyear)>0 THEN
		
		BEGIN
		
		DECLARE cursor_down INT DEFAULT 0;
		declare tmp_pattern VARCHAR(10) DEFAULT'行政班';
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_down = 1; 
		open cur_currentCls;
		fetchSeq:loop
		FETCH cur_currentCls INTO tmp_clsname,tmp_clsgrade,tmp_type,tmp_classid;		
			IF cursor_down=1 THEn
				LEAVE fetchSeq;
			END IF;
			
			set tmp_sql=CONCAT("SELECT COUNT(class_id) INTO @clscount FROM class_info 
				where class_name='",tmp_clsname,"' AND class_grade='",tmp_clsgrade,"' 
				 AND isflag=1 AND type='",tmp_type,"' AND pattern='",tmp_pattern,"' AND year='",tmp_nextyear,"' AND dc_school_id=",p_dc_school_id);
			SET @tmp_sql1=tmp_sql;
			PREPARE stmt1 FROM @tmp_sql1  ;
			EXECUTE stmt1;
			DEALLOCATE PREPARE stmt1;
			SET tmp_clscount=@clscount;
			
			IF tmp_clscount<1 THEN
				  IF tmp_clsgrade='高二' THEN
					SET tmp_clsgrade='高三';
				   END IF;
				   IF tmp_clsgrade='高一' THEN
					SET tmp_clsgrade='高二';
				   END IF;
				  IF tmp_clsgrade='初二' THEN
					SET tmp_clsgrade='初三';
				   END IF;
				   IF tmp_clsgrade='初一' THEN
					SET tmp_clsgrade='初二';
				   END IF;
				   IF tmp_clsgrade='小学五年级' THEN
					SET tmp_clsgrade='小学六年级';
				   END IF;
				   IF tmp_clsgrade='小学四年级' THEN
					SET tmp_clsgrade='小学五年级';
				   END IF;
				    IF tmp_clsgrade='小学三年级' THEN
					SET tmp_clsgrade='小学四年级';
				   END IF;
				    IF tmp_clsgrade='小学二年级' THEN
					SET tmp_clsgrade='小学三年级';
				   END IF;
				   IF tmp_clsgrade='小学一年级' THEN
					SET tmp_clsgrade='小学二年级';
				   END IF;
				 
				   INSERT INTO class_info(class_name,class_grade,pattern,year,isflag,type,dc_school_id) VALUES(tmp_clsname,tmp_clsgrade,tmp_pattern,tmp_nextyear,1,tmp_type,p_dc_school_id);
				   SELECT LAST_INSERT_ID() INTO @tmp_clsid;
				   set tmp_clsid=@tmp_clsid;
				   BEGIN
					DECLARE cursor_done INT DEFAULT 0;
				   	DECLARE tmp_userid VARCHAR(100) DEFAULT '';
					DECLARE tmp_sportsex INT;
					DECLARE tmp_subject_id INT;
					DECLARE tmp_relationType VARCHAR(100) DEFAULT '';
		
	
				     
				DECLARE cur_clsuser CURSOR FOR
					SELECT user_id,sport_sex,relation_type,subject_id FROM j_class_user WHERE class_id=tmp_classid AND relation_type='学生';
					
				DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done = 1;  
				   OPEN cur_clsuser;
				   fetchCu:loop  
				    FETCH cur_clsuser INTO tmp_userid,tmp_sportsex,tmp_relationType,tmp_subject_id;					  
						 if cursor_done=1 THEN
							LEAVE fetchCu;
						 END IF;
						 INSERT INTO j_class_user(ref,user_id,class_id,relation_type,sport_sex,subject_id)
						 VALUES(UUID(),tmp_userid,tmp_clsid,tmp_relationType,tmp_sportsex,tmp_subject_id);					  
										   
				   end loop;
				   close cur_clsuser;
				   END;
				   
			   ELSE
				set afficeRows=-1;  
				LEAVE fetchSeq;
			END IF;
		
		end loop;
		CLOSE cur_currentCls;
		END;
		set afficeRows=1;
	  ELSE
		set afficeRows=-1;
	END IF;
END $$

DELIMITER ;
