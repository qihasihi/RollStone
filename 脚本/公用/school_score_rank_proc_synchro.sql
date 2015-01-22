DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `school_score_rank_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `school_score_rank_proc_synchro`(
				            p_school_name VARCHAR(1000),
				            p_type_id BIGINT,
				            p_school_id BIGINT,
				            p_rank BIGINT,
				            p_operate_type INT,
				            p_score FLOAT,
				            p_model_id BIGINT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET affect_row = 0;
	
	SET tmp_sql="INSERT INTO school_score_rank (";
	
	IF p_school_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCHOOL_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_school_name,"',");
	END IF;
	
	
	IF p_type_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"TYPE_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_type_id,",");
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCHOOL_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_school_id,",");
	END IF;
	
	IF p_rank IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"RANK,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_rank,",");
	END IF;
	
	
	IF p_operate_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"OPERATE_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_operate_type,",");
	END IF;
	
	IF p_score IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"SCORE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_score,",");
	END IF;
	
	IF p_model_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"MODEL_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_model_id,",");
	END IF;
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"CTIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
