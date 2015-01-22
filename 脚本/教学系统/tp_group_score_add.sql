DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_score_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_score_add`(
				            p_course_id BIGINT,
				            p_group_id bigint,	
				            p_subject_id INT,			          
					    p_award_number bigint,
					    p_class_id BIGINT,
					    p_dc_school_id BIGINT,
					    p_classtype INT,
					    p_submitflag int,
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_group_score(";
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"group_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_group_id,",");
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"subject_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_subject_id,",");
	END IF;
	IF p_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"class_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_class_id,",");
	END IF;
	IF p_dc_school_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_dc_school_id,",");
	END IF;
	
	IF p_award_number IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"award_number,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_award_number,",");
	END IF;
	IF p_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"course_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_course_id,",");
	END IF;
	IF p_classtype IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"class_type,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_classtype,",");
	END IF;
	IF p_submitflag IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"submit_flag,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_submitflag,",");
	END IF;
		
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"CTIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
