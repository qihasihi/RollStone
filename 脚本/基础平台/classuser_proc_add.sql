DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `classuser_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `classuser_proc_add`(p_ref varchar(50),
					    p_user_ref varchar(50),
					    p_class_id int,
					    p_relation_type varchar(30),
					    p_sport_sex int,
					    p_subject_id int,
					    out affect_row int)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(2000) DEFAULT '';
	DECLARE tmp_class VARCHAR(2000) DEFAULT '';
	DECLARE tmp_sql1 VARCHAR(1000);
	
	IF  p_ref IS NOT NULL 
		AND p_user_ref IS NOT NULL  
		AND p_class_id IS NOT NULL 
		AND p_relation_type IS NOT NULL THEN
		
		SET tmp_column_sql = 'ref,user_id,class_id,relation_type,c_time,sport_sex';
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ref,"'",",'",p_user_ref,"'",",",p_class_id,",'",p_relation_type,"',NOW()");
		IF p_sport_sex IS NOT NULL THEN
			SET tmp_value_sql=CONCAT(tmp_value_sql,",'",p_sport_sex,"'");	
		ELSE
			SET tmp_value_sql=CONCAT(tmp_value_sql,",0");	
		END IF;	
		
		IF p_subject_id IS NOT NULL THEN
			SET tmp_column_sql=CONCAT(tmp_column_sql,",subject_id");	
			SET tmp_value_sql=CONCAT(tmp_value_sql,",",p_subject_id);	
		END IF;	
		
		SET tmp_sql =CONCAT("INSERT INTO j_class_user (",tmp_column_sql,") VALUES (",tmp_value_sql,")");
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
	    	
	    SET affect_row = 1;
	ELSE
	    
	    SET affect_row = 0;
	END IF;
	
	
	
	
	
	
	
	
END $$

DELIMITER ;
