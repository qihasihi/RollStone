DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_score_addOrUpdate`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_score_addOrUpdate`(
				            p_course_id BIGINT,
				            p_group_id bigint,		
				            p_subject_id INT,		          
					    p_award_number bigint,
					    p_class_id BIGINT,
					    p_dc_school_id BIGINT,
					    p_classtype INT,
				            OUT affect_row INT)
BEGIN  
	DECLARE v_sql VARCHAR(10000) DEFAULT '';
	  SET v_sql=CONCAT('SELECT COUNT(REF) INTO @tmp_count FROM tp_group_score where 1=1 ');
	IF p_course_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND course_id=",p_course_id);
	END IF;  
	IF p_group_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND group_id=",p_group_id);
	END IF; 
	IF p_subject_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND subject_id=",p_subject_id);
	END IF; 
	IF p_class_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND class_id=",p_class_id);
	END IF; 
	IF p_classtype IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND class_type=",p_classtype);
	END IF; 
	SET v_sql=CONCAT(v_sql,' ORDER BY REF DESC LIMIT 0,1');
	
	SET @tmp_sql=v_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_count>0 THEN 
	
		SET v_sql=CONCAT('SELECT REF INTO @tmp_ref FROM tp_group_score where 1=1 ');		
		IF p_group_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND group_id=",p_group_id);
		END IF;  
		IF p_course_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND course_id=",p_course_id);
		END IF;  
		IF p_subject_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND subject_id=",p_subject_id);
		END IF; 
		IF p_class_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND class_id=",p_class_id);
		END IF; 
		IF p_classtype IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND class_type=",p_classtype);
		END IF; 
		SET v_sql=CONCAT(v_sql,' ORDER BY REF DESC LIMIT 0,1');
		
		SET @tmp_sql=v_sql;
		PREPARE stmt FROM @tmp_sql  ;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
		
		
		IF @tmp_ref IS NOT NULL THEN			
			CALL `tp_group_score_update`(
				           @tmp_ref ,
				            p_course_id ,
				            p_group_id ,	
				            p_subject_id,			           
					    p_award_number ,
					    p_class_id,
					    p_dc_school_id,
					    0,
					    p_classtype,
				            affect_row 
				          );
		END IF;
         ELSE
		CALL `tp_group_score_add`(
				            p_course_id ,
				            p_group_id ,
				            p_subject_id,				          
					    p_award_number ,
					       p_class_id,
					    p_dc_school_id,
					    p_classtype,
					    0,
				             affect_row );
	END IF;
	
	SET affect_row = 1;
	
END $$

DELIMITER ;
