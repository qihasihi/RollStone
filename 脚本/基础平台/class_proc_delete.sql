DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_proc_delete`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `class_proc_delete`(
				            p_class_id INT,
				            p_lzxclassid VARCHAR(100),
				            p_subject_id INT,
				            p_isflag INT,
				            p_dcschoolid INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from j_class_user where 1=1 AND class_id IN (SELECT class_id FROM class_info where 1=1";
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_ID=",p_class_id);
	END IF;
	IF p_lzxclassid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and lzx_classid='",p_lzxclassid,"'");
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and subject_id=",p_subject_id);
	END IF;
	IF p_isflag IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and isflag=",p_isflag);
	END IF;
	IF p_dcschoolid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and dc_school_id=",p_dcschoolid);
	END IF;
	SET tmp_sql=CONCAT(tmp_sql,")");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
	SET tmp_sql="delete from class_info where 1=1";
	
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and CLASS_ID=",p_class_id);
	END IF;
	IF p_subject_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and subject_id=",p_subject_id);
	END IF;
	IF p_isflag IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and isflag=",p_isflag);
	END IF;
	IF p_lzxclassid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and lzx_classid='",p_lzxclassid,"'");
	END IF;
		IF p_dcschoolid IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and dc_school_id=",p_dcschoolid);
	END IF;
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
    END$$

DELIMITER ;