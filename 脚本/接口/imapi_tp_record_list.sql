DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_tp_record_list`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_tp_record_list`(IN in_user_id BIGINT(19), IN in_course_id BIGINT(19), IN in_class_id BIGINT(19)
	,IN in_school_id BIGINT,p_ref INT
)
BEGIN
  DECLARE tmp_sql VARCHAR(1000) DEFAULT '';	
  	SET tmp_sql = CONCAT('SELECT * FROM tp_record WHERE 1=1 ');
  	IF in_course_id IS NOT NULL THEN
  	 SET tmp_sql=CONCAT(tmp_sql,' AND course_id=',in_course_id);
  	END IF;
  	IF p_ref IS NOT NULL THEN
  	 SET tmp_sql=CONCAT(tmp_sql,' AND ref=',p_ref);
  	END IF;
  	IF in_user_id IS NOT NULL THEN
  	 SET tmp_sql=CONCAT(tmp_sql,' AND user_id=',in_user_id);
  	END IF;
  	IF in_class_id IS NOT NULL THEN
  	 SET tmp_sql=CONCAT(tmp_sql,' AND class_id=',in_class_id);
  	END IF;
  	IF in_school_id IS NOT NULL THEN
  	 SET tmp_sql=CONCAT(tmp_sql,' AND dc_school_id=',in_school_id);
  	END IF;
	set tmp_sql=CONCAT(tmp_sql,' ORDER BY ref');
  	SET @tmp_sql = tmp_sql;
  	PREPARE stmt FROM @tmp_sql;	
  	EXECUTE stmt;
END $$

DELIMITER ;
