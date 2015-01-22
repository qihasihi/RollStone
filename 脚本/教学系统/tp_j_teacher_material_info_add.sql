DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_teacher_material_info_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_teacher_material_info_add`(
							p_user_id INT,
							p_subject_id INT ,
							p_material_id INT,
							p_grade_id INT,
							p_term_id VARCHAR(100),
							OUT affect_row INT
							)
BEGIN	
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_column VARCHAR(2000) DEFAULT " ";  
	DECLARE tmp_value VARCHAR(2000) DEFAULT ' ';  
	declare tmp_count INT default 0;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET affect_row = 0;
	SET affect_row=1;
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_j_teacher_material_info WHERE 1=1 AND user_id=",p_user_id
				," AND subject_id=",p_subject_id," AND grade_id=",p_grade_id," AND term_id='",p_term_id,"'");	
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET tmp_count=@tmp_sumCount;
	
	IF tmp_count>0 THEN 
		DELETE FROM tp_j_teacher_material_info WHERE 1=1 AND user_id=p_user_id AND subject_id=p_subject_id AND grade_id=p_grade_id AND term_id=p_term_id;
	END IF;
	INSERT INTO tp_j_teacher_material_info(user_id,subject_id,material_id,grade_id,term_id,c_time)
		VALUES(p_user_id,p_subject_id,p_material_id,p_grade_id,p_term_id,NOW());
    
END $$

DELIMITER ;
