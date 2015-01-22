DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_score_rank_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_score_rank_proc_delete`(			         
				            p_type_id BIGINT,
				            p_school_id BIGINT,
				            p_ref BIGINT,
				            p_operate_type INT,
				            p_model_id BIGINT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	
	SET affect_row = 0;
	SET tmp_sql="delete from school_score_rank where 1=1";
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and TYPE_ID=",p_type_id);
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and SCHOOL_ID=",p_school_id);
	END IF;
	
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and OPERATE_TYPE=",p_operate_type);
	END IF;	
	
	
	IF p_model_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and MODEL_ID=",p_model_id);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
