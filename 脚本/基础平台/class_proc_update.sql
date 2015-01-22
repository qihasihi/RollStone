DELIMITER $$
CREATE DEFINER=`mytest`@`%` PROCEDURE `class_proc_update`(
				          p_class_id INT,
				          p_class_grade VARCHAR(1000),
				           p_class_name VARCHAR(1000),
				            p_year VARCHAR(1000),
				          p_type VARCHAR(100),
				          p_pattern VARCHAR(1000),
				          p_subject_id INT,
				          p_dctype INT,
				          p_isflag INT,
				            p_allow_join INT,
				            p_verify_time VARCHAR(50),
				            p_cls_num INT,
				            p_invite_code varchar(6),
							p_activity_type int,
							p_term_id int,
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql ='UPDATE class_info set m_time=NOW()';
	
	IF p_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",TYPE='",p_type,"'");
	END IF;
	
	
	IF p_year IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",YEAR='",p_year,"'");
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",subject_id=",p_subject_id);
	END IF;
	IF p_subject_id IS NOT NULL AND p_subject_id=-999 THEN
		SET tmp_sql=CONCAT(tmp_sql,",subject_id=NULL");
	END IF;
	IF p_dctype IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",dc_type=",p_dctype);
	END IF;
	
	IF p_isflag IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",isflag=",p_isflag);
	END IF;
	
	IF p_pattern IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",PATTERN='",p_pattern,"'");
	END IF;
	
	
	IF p_class_grade IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_GRADE='",p_class_grade,"'");
	END IF;
	
	IF p_class_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",CLASS_NAME='",p_class_name,"'");
	END IF;
	
	IF p_allow_join IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",allow_join=",p_allow_join);
	END IF;
	
	IF p_verify_time IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",verify_time=str_to_date('",p_verify_time,"','%Y-%m-%d %H:%i:%s')");
	END IF;
	
	IF p_cls_num IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",cls_num=",p_cls_num);
	END IF;
	
	IF p_invite_code IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",invite_code='",p_invite_code,"'");
	END IF;
	
	IF p_activity_type IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",activity_type='",p_activity_type,"'");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,",term_id='",p_term_id,"'");
	END IF;
	
	
	SET tmp_sql =CONCAT(tmp_sql, " WHERE 1=1");  
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_ID=",p_class_id);
	END IF;
	
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row = 1;
    END$$
DELIMITER ;
