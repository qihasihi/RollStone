DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_proc_addOrUpdate`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `class_proc_addOrUpdate`(
					    p_lzx_class_id VARCHAR(100),
				             p_class_grade VARCHAR(1000),
				             p_class_name VARCHAR(1000),
				             p_year VARCHAR(1000),
				            p_type VARCHAR(1000),
				            p_pattern VARCHAR(1000),
				            p_subject_id INT,
				            p_dctype INT,
				            p_isflag INT, 
				            p_dcschoolid INT,
				            p_c_user_id INT,
				            p_im_valdate_code VARCHAR(6),
				            OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000);
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM class_info where lzx_classid='",p_lzx_class_id,"'");
	IF p_dcschoolid IS NOT NULL THEN
	  SET tmp_sql=CONCAT(tmp_sql," AND dc_school_id=",p_dcschoolid);
	END IF;
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET affect_row=1;
	IF @tmp_sumCount>0 THEN
	SET tmp_sql=CONCAT("SELECT class_id INTO @tmp_clsid FROM class_info where lzx_classid='",p_lzx_class_id,"'");
	IF p_dcschoolid IS NOT NULL THEN
	  SET tmp_sql=CONCAT(tmp_sql," AND dc_school_id=",p_dcschoolid);
	END IF;
		SET @tmp_sql=tmp_sql;
		PREPARE stmt FROM @tmp_sql  ;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
		CALL class_proc_update(@tmp_clsid ,
				          p_class_grade,
				           p_class_name ,
				            p_year,
				          p_type,
				          p_pattern,
				          p_subject_id,
				          p_dctype,
				          p_isflag,	
				          NULL,
				          NULL,
				          NULL,
				          NULL, 
				          NULL,
				          NULL,		          
				          affect_row 
				          );	
		          
				          
	ELSE	
	     CALL  class_proc_add(
					     p_lzx_class_id ,
					     p_dcschoolid,
				             p_class_grade ,
				             p_class_name,
				             p_year,
				            p_type ,
				            p_pattern,
				            p_subject_id ,
				            p_dctype,
				            p_isflag, 
				            NULL,
				            NULL,
				            NULL,
				            NULL,
				            p_c_user_id,
				            p_im_valdate_code,
				            NULL,
				            NULL,
				            NULL,
				            affect_row);
	END IF;
	
	
    END$$

DELIMITER ;