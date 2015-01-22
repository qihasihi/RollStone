DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_score_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_score_update`(
				           p_ref BIGINT,
				            p_course_id BIGINT,
				            p_group_id BIGINT,	
				            p_subject_id INT,			           
					    p_award_number BIGINT,
					    p_class_id BIGINT,
					    p_dc_school_id BIGINT,
					    p_submitflag INT,
					    p_class_type INT,
				            OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(100000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE tp_group_score set mtime=NOW()';
	
	
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",group_id=",p_group_id);
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",subject_id=",p_subject_id);
	END IF;
		
	IF p_award_number IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",award_number=",p_award_number);
	END IF;	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," , course_id=",p_course_id);
	END IF;
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," , class_id=",p_class_id);
	END IF;
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," , dc_school_id=",p_dc_school_id);
	END IF;
	IF p_submitflag IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," , submit_flag=",p_submitflag);
	END IF;
	IF p_class_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," , class_type=",p_class_type);
	END IF;
		
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1 ");  
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ref=",p_ref);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
