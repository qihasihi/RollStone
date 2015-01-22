DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `myinfo_user_thpjtimesteup`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `myinfo_user_thpjtimesteup`(p_ref int,p_type int,OUT affect_row INT)
BEGIN
    
		
		DECLARE tmp_ref int;	
		DECLARE tmp_starttime VARCHAR(100);	
		DECLARE tmp_endtime VARCHAR(100);	
		DECLARE tmp_classyear VARCHAR(100);
		DECLARE tmp_sql VARCHAR(1000);		
		
		DECLARE template_id INT;
		DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
		DECLARE  EXIT HANDLER FOR NOT FOUND SET affect_row=0;
			
		SET tmp_sql=CONCAT(" SELECT ref,pj_start_time,pj_end_time,(SELECT class_year_value FROM class_year_info WHERE class_year_id=pj.YEAR_ID) INTO @ref,@starttime,@endtime,@classyear FROM pj_time_step_info pj WHERE ref=",p_ref);	
		SET @tmp_sql1=tmp_sql;
		PREPARE stmt1 FROM @tmp_sql1 ;
		EXECUTE stmt1;
		DEALLOCATE PREPARE stmt1;
		SET tmp_ref=@ref;
		SET tmp_starttime=@starttime;
		SET tmp_endtime=@endtime;
		SET tmp_classyear=@classyear;	
		
		
	
			
			
			if p_type IS NULL OR p_type=0 THEN
				set  template_id=6;
			  else if p_type=1 THEN
				SET template_id=7;
			  END IF;
			END IF;		
			BEGIN	
				DECLARE tmp_user_id VARCHAR(100);		
				
				DECLARE tmp_cursor CURSOR FOR SELECT DISTINCT USER_ID FROM j_class_user cu,class_info c
					WHERE c.YEAR=(SELECT class_year_value FROM class_year_info WHERE class_year_id=(SELECT year_id FROM pj_time_step_info WHERE ref=p_ref));
				OPEN tmp_cursor;
				tmp_noticloop:LOOP FETCH tmp_cursor INTO tmp_user_id;					
				       CALL j_myinfo_user_info_add_tongy(template_id,tmp_user_id,14,'֪ͨ',null,CONCAT(tmp_classyear,'#ETIANTIAN_SPLIT#',tmp_ref,'|',tmp_starttime,'|',tmp_endtime),@officeRows);	
				       SET affect_row=@officeRows;      
				  END LOOP tmp_noticloop;
				 CLOSE tmp_cursor;
			END;
		
	
END $$

DELIMITER ;
