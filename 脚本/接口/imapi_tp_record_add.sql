DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_tp_record_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_tp_record_add`(in_course_id bigint,in_user_id bigint,in_content varchar(5000),in_img_url varchar(50000)
,in_class_id bigint,in_dc_school_id BIGINT,OUT affect_row INT)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(100000000) DEFAULT '';
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_record (";
	
	IF in_course_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"course_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"",in_course_id,",");
	END IF;
	
	IF in_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"user_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"",in_user_id,",");
	END IF;
	IF in_dc_school_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"dc_school_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,in_dc_school_id,",");
	END IF;
	IF in_class_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"class_id,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"",in_class_id,",");
	END IF;
	
	IF in_content IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"content,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",in_content,"',");
	END IF;
	
	
	IF in_img_url IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"img_url,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",in_img_url,"',");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"CTIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
END $$

DELIMITER ;
