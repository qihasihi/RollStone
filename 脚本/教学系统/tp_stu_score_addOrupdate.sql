DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_addOrupdate`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_stu_score_addOrupdate`(				         
				            p_user_id BIGINT,
				              p_group_id BIGINT,				         
				            p_course_id BIGINT,
				            p_subject_id INT,
				            p_attendanceNum INT,
				            p_smilingNum INT,
				            p_violationDisNum INT,	
				            p_classid INT,
				            p_dcschoolid BIGINT,	            				          				          
				            p_classtype INT,
				              p_task_score INT	,
				            p_comment_score   INT,
				            OUT affect_row INT
				          )
BEGIN
	declare v_sql VARCHAR(10000) default '';
	  SET v_sql=CONCAT('SELECT COUNT(REF) INTO @tmp_count FROM tp_stu_score where 1=1 ');
	IF p_user_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND user_id=",p_user_id);
	END IF;  
	
	IF p_course_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND course_id=",p_course_id);
	END IF;
	IF p_subject_id IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND subject_id=",p_subject_id);
	END IF;  
	IF p_classid IS NOT NULL THEN
	  SET v_sql=CONCAT(v_sql," AND class_id=",p_classid);
	END IF;  
	IF p_classtype IS NOT NULL THEN
		SET v_sql=CONCAT(v_sql," AND class_type=",p_classtype);
	END IF;
	SET v_sql=CONCAT(v_sql,' ORDER BY REF DESC LIMIT 0,1');
	
	SET @tmp_sql=v_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	if @tmp_count>0 THEN 
	
		set v_sql=CONCAT('SELECT REF INTO @tmp_ref FROM tp_stu_score where 1=1 ');
		IF p_user_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND user_id=",p_user_id);
		END IF;  
	
		IF p_course_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND course_id=",p_course_id);
		END IF;  
		IF p_subject_id IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND subject_id=",p_subject_id);
		END IF;  
		IF p_classid IS NOT NULL THEN
		  SET v_sql=CONCAT(v_sql," AND class_id=",p_classid);
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
			CALL tp_stu_score_update(@tmp_ref ,
						    p_user_id ,
						    p_attendanceNum ,
						    p_smilingNum ,
						    p_violationDisNum ,
						    p_group_id,				         
						    p_course_id ,
						    p_subject_id,
						    p_classid,
						    p_dcschoolid,	
						    p_classtype,	
						    p_task_score,
						    p_comment_score,		          
						     affect_row );
		END IF;
         ELSE
                     
		CALL `tp_stu_score_add`(
						    p_user_id ,
						    p_attendanceNum ,
						    p_smilingNum ,
						    p_violationDisNum ,
						    p_group_id ,				         
						    p_course_id ,
						    p_subject_id,
						    p_classid,
						    p_dcschoolid,
						    p_classtype,
						    p_task_score,
						    p_comment_score,	
						    NULL,		
						    null,       
						    affect_row );
		  
	END IF;
	
	SET affect_row = 1;
	
	
	
END $$

DELIMITER ;
