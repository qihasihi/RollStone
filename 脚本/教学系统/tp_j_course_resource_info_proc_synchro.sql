DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_course_resource_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_course_resource_info_proc_synchro`(
								p_ref BIGINT,
								p_res_id BIGINT,
								p_course_id BIGINT,
								p_resource_type int,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	set affect_row=1;
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_j_course_resource_info where course_id=",p_course_id," AND res_id=",p_res_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_sumCount <1 THEN	
		CALL tp_j_course_resource_info_proc_add(	
						null,
			p_res_id ,
			p_course_id ,
			p_resource_type ,
			 affect_row 
			);	  
	
	END IF;
END $$

DELIMITER ;
