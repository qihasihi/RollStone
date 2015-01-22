DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `classuser_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `classuser_proc_delete`(
				            p_ref varchar(50),
					       p_user_ref varchar(50),
					       p_class_id int,
					       p_relation_type varchar(20),
					       p_sport_sex int,
					       
					       p_classgrade VARCHAR(20),
					       p_subject_id VARCHAR(50),
					       p_year varchar(20),
					       p_pattern VARCHAR(20),
					       p_type VARCHAR(10),
					       p_isflag VARCHAR(10),
					       p_after_currentyear varchar(30),
					       out affect_row int)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;  
	
	
	IF p_ref IS NULL and p_user_ref is null and p_class_id is null and p_relation_type is null THEN
		SET affect_row = 0;
	ELSE
		SET tmp_sql="delete from j_class_user where 1=1";
		IF p_classgrade IS NOT NULL OR p_subject_id IS NOT NULL OR p_after_currentyear is not null or p_year IS NOT NULL OR p_pattern IS NOT NULL OR p_type IS NOT NULL OR p_isflag IS NOT NULL THEN
			set tmp_sql=CONCAT(tmp_sql,' AND class_id IN (SELECT class_id from class_info where 1=1 ');
			IF p_classgrade IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND class_grade="',p_classgrade,'"');
			END IF;
			IF p_subject_id IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND subject_id=',p_subject_id);
			END IF;
			IF p_year IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND year="',p_year,'"');
			END IF;
			IF p_pattern IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND pattern="',p_pattern,'"');
			END IF;
			IF p_type IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND type="',p_type,'"');
			END IF;
			IF p_isflag IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND isflag="',p_isflag,'"');
			END IF;			
			IF p_after_currentyear IS NOT NULL THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND year>="',p_after_currentyear,'"');
			END IF;	
			
			set tmp_sql=CONCAT(tmp_sql,')');
		END IF;
		
		if p_ref is not null then
			SET tmp_sql=CONCAT(tmp_sql," and ref='",p_ref,"'");
		end if;
		
		IF p_user_ref IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and user_id='",p_user_ref,"'");
		END IF;
		
		IF p_class_id IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and class_id=",p_class_id);
		END IF;
		
		IF p_relation_type IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and relation_type='",p_relation_type,"'");
		END IF;
		
		IF p_sport_sex IS NOT NULL THEN
			SET tmp_sql=CONCAT(tmp_sql," and sport_sex=",p_sport_sex);
		END IF;
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		
		SET affect_row = 1;
	END IF;
END $$

DELIMITER ;
