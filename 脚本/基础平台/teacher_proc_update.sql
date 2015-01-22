DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teacher_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teacher_proc_update`(
					    p_teacher_id   int,
					  p_name 	 VARCHAR(250),
					  p_sex  	 VARCHAR(3),
					  p_address 	 VARCHAR(300),
					  p_phone	 VARCHAR(250),
					  p_cart_id	 VARCHAR(200),
					  p_post	 VARCHAR(300),
					  p_user_ref	 VARCHAR(50),
					  p_password	 VARCHAR(100),
					  p_level	 VARCHAR(100),
					  p_birth	 VARCHAR(100),
					  p_entry_time	 VARCHAR(100),
					  p_img_src	 VARCHAR(1000),
					  OUT affect_row INT
    )
BEGIN
	  DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	  DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row=0;
         
         IF p_teacher_id IS  NULL AND p_user_ref IS NULL AND p_name IS NULL THEN 
			
		SET affect_row=0;
		
         ELSE   
		SET tmp_sql="update teacher_info set m_time=NOW()";
		
		IF p_name IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_name='",p_name,"'");
		END IF;
		
		IF p_sex IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_sex='",p_sex,"'");
		END IF;
		IF p_address  IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_address='",p_address,"'");
		END IF;
		
		IF p_phone IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_phone='",p_phone,"'");
		END IF;
		IF p_cart_id  IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_card_id='",p_cart_id,"'");
		END IF;
		IF p_post   IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_post='",p_post,"'");
		END IF;
		IF p_password   IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",password='",p_password ,"'");
		END IF;
		IF p_level    IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_level='",p_level,"'");
		END IF;
		IF p_birth  IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",teacher_birth=str_to_date('",p_birth,"','%Y-%m-%d %H:%i:%s')");
		END IF;
		IF p_entry_time IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",entry_time=str_to_date('",p_entry_time,"','%Y-%m-%d %H:%i:%s')");
		END IF;
		
		IF p_img_src IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql,",imgheardsrc='",p_img_src,"'");
		END IF;
		
		SET tmp_sql =CONCAT(tmp_sql," where 1=1");
		
		IF p_teacher_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and teacher_id=",p_teacher_id);
		END IF;
		
		IF p_user_ref IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and user_id='",p_user_ref,"'");
		END IF;
		
		
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
			
		SET affect_row=1;
         END IF;
         
END $$

DELIMITER ;
