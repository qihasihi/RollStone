DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `init_every_day`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `init_every_day`()
BEGIN
    
     DECLARE v_year INT;
     DECLARE v_month INT;
     DECLARE v_day INT;
     DECLARE v_num INT;
     SET v_year=2010;
     
     DELETE FROM  every_day;
     
     WHILE v_year<2081 DO  
           SET v_month=1;
          
          
          WHILE v_month<13 DO   
          
                   
                   SET v_day=1;
                   
                   
                  SELECT DATE_FORMAT(LAST_DAY(DATE_FORMAT(CONCAT(v_year,'-',v_month,'-',v_day),'%y-%m-%e')) ,'%e') INTO v_num;
                  WHILE v_day<v_num+1 DO              
             
			INSERT INTO every_day (e_date,e_year,e_month,e_day) VALUES (DATE_FORMAT(CONCAT(v_year,'-',v_month,'-',v_day),'%y-%m-%d'),v_year,v_month,v_day);
                        SET v_day=v_day+1;
               
                   END WHILE;     
          
                                                   
                  SET v_month=v_month+1;
          END WHILE;               
            
          SET v_year=v_year+1;
     END WHILE;
     
END $$

DELIMITER ;
