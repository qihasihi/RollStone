DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `term_info_first_init`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `term_info_first_init`(OUT flag INT)
BEGIN
          DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
        DECLARE tmp_count INT;	
        DECLARE tmp_year INT;	
        DECLARE max_id INT;	
        DECLARE tmp_month INT;	
        DECLARE in_sql_year VARCHAR(100);
	SET flag = 0;
	SELECT  YEAR(NOW()) INTO tmp_year;
        SELECT   MONTH(NOW())  INTO tmp_month;
	IF tmp_month>=9 THEN
	    SET in_sql_year = CONCAT(tmp_year,'~',(tmp_year+1));
	END IF;
	IF tmp_month<9 THEN
	    SET in_sql_year = CONCAT((tmp_year-1),'~',tmp_year);
	END IF;
	SELECT COUNT(*) INTO tmp_count FROM term_info WHERE YEAR=in_sql_year;
	 IF    tmp_count =0 THEN
	 
	  
		
             IF 	tmp_month>=9 THEN	
             	
	     INSERT INTO term_info (REF,TERM_NAME,SEMESTER_BEGIN_DATE,SEMESTER_END_DATE,C_TIME,YEAR) VALUES
	      (UUID(),   '第一学期', STR_TO_DATE(CONCAT(tmp_year,'-09-01 00:00:00'),'%Y-%m-%d %H:%i:%s') ,  STR_TO_DATE(CONCAT((tmp_year+1),'-01-15 00:00:00'),'%Y-%m-%d %H:%i:%s') , NOW(),in_sql_year);
	     INSERT INTO term_info (REF,TERM_NAME,SEMESTER_BEGIN_DATE,SEMESTER_END_DATE,C_TIME,YEAR) VALUES
	     (UUID(),   '第二学期',  STR_TO_DATE(CONCAT(tmp_year+1,'-01-15 00:00:01'),'%Y-%m-%d %H:%i:%s'), STR_TO_DATE(CONCAT((tmp_year+1),'-08-31 23:59:59'),'%Y-%m-%d %H:%i:%s') , NOW(),in_sql_year);
	     
	     
	     SELECT MAX(class_year_id)+1 INTO max_id  FROM class_year_info ;
		INSERT INTO class_year_info(CLASS_YEAR_ID,CLASS_YEAR_NAME,CLASS_YEAR_VALUE,B_TIME,E_TIME,C_TIME)
		VALUES(  max_id,
			CONCAT(tmp_year,"~",tmp_year+1,'学年'),
			CONCAT(tmp_year,"~",tmp_year+1),
			STR_TO_DATE(CONCAT(tmp_year,'-09-01 00:00:00'),'%Y-%m-%d %H:%i:%s'),
			STR_TO_DATE(CONCAT((tmp_year+1),'-08-31 23:59:59'),'%Y-%m-%d %H:%i:%s'),
			NOW()
		);
		
              END IF;		
              
            
	     
              IF 	tmp_month<9 THEN	
               	
             	   
	     INSERT INTO term_info (REF,TERM_NAME,SEMESTER_BEGIN_DATE,SEMESTER_END_DATE,C_TIME,YEAR) VALUES
	      (UUID(),   '第一学期', STR_TO_DATE(CONCAT(tmp_year-1,'-09-01 00:00:00'),'%Y-%m-%d %H:%i:%s') ,  STR_TO_DATE(CONCAT(tmp_year,'-01-14 23:59:59'),'%Y-%m-%d %H:%i:%s') , NOW(),in_sql_year);
	     INSERT INTO term_info (REF,TERM_NAME,SEMESTER_BEGIN_DATE,SEMESTER_END_DATE,C_TIME,YEAR) VALUES
	     (UUID(),   '第二学期',  STR_TO_DATE(CONCAT(tmp_year,'-01-15 00:00:00'),'%Y-%m-%d %H:%i:%s'), STR_TO_DATE(CONCAT(tmp_year,'-08-31 23:59:59'),'%Y-%m-%d %H:%i:%s') , NOW(),in_sql_year);
	       
	     SELECT MAX(class_year_id)+1 INTO max_id  FROM class_year_info ;
		INSERT INTO class_year_info(CLASS_YEAR_ID,CLASS_YEAR_NAME,CLASS_YEAR_VALUE,B_TIME,E_TIME,C_TIME)
		VALUES( max_id ,
			CONCAT((tmp_year-1),"~",tmp_year,'学年'),
			CONCAT((tmp_year-1),"~",tmp_year),
			STR_TO_DATE(CONCAT(tmp_year-1,'-09-01 00:00:00'),'%Y-%m-%d %H:%i:%s'),
			STR_TO_DATE(CONCAT(tmp_year,'-08-31 23:59:59'),'%Y-%m-%d %H:%i:%s'),
			NOW()
		);
	     	
              END IF;	 
	     	
	 END IF;
         SET flag = 1;
END $$

DELIMITER ;
