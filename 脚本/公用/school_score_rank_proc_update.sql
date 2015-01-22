DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_score_rank_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_score_rank_proc_update`(
				          p_school_name VARCHAR(1000),
				          p_ctime DATETIME,
				          p_type_id BIGINT,
				          p_school_id BIGINT,
				          p_rank BIGINT,
				          p_ref BIGINT,
				          p_operate_type INT,
				          p_score FLOAT,
				          p_model_id BIGINT,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE school_score_rank set m_time=NOW()';
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCHOOL_NAME='",p_school_name,"'");
	END IF;
	
	IF p_ctime IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CTIME=",p_ctime);
	END IF;
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TYPE_ID=",p_type_id);
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCHOOL_ID=",p_school_id);
	END IF;
	
	IF p_rank IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",RANK=",p_rank);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",REF=",p_ref);
	END IF;
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",OPERATE_TYPE=",p_operate_type);
	END IF;
	
	IF p_score IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",SCORE=",p_score);
	END IF;
	
	IF p_model_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",MODEL_ID=",p_model_id);
	END IF;
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE ",s_id);  
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
END $$

DELIMITER ;
