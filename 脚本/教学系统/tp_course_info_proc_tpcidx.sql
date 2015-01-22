DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_tpcidx`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_tpcidx`(
						p_user_ref VARCHAR(50),
						p_term_id varchar(50)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT 'SELECT * FROM tp_j_course_class cc
			 WHERE 1=1';
	if p_term_id IS NOT NULL THEN
	 set tmp_sql=CONCAT(tmp_sql," AND cc.term_id='",p_term_id,"'");
	END IF;
	
	set tmp_sql=concat(tmp_sql,' AND ((cc.class_type=1 AND EXISTS(
				SELECT DISTINCT ref FROM j_class_user WHERE class_id=cc.class_id ');
	if p_user_ref IS NOT NULL THEN
		set tmp_sql=CONCAT(tmp_sql," AND user_id='",p_user_ref,"'");
	END IF;
	SET tmp_sql=CONCAT(tmp_sql,")) ");
	
	
	set tmp_sql=concat(tmp_sql," OR (cc.class_type=2 AND EXISTS( 
				SELECT DISTINCT ref FROM tp_j_virtual_class_student WHERE VIRTUAL_CLASS_ID=cc.class_id ");
	IF p_user_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND user_id=(SELECT user_id from user_info where ref='",p_user_ref,"')");
	END IF;
	SET tmp_sql=CONCAT(tmp_sql,"))) ");	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
END $$

DELIMITER ;
