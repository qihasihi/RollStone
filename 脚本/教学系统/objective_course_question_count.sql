DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `objective_course_question_count`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `objective_course_question_count`(
				          p_coures_id BIGINT,
				          OUT sumCount FLOAT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 AND cq.question_id=q.question_id AND q.question_type IN (3,4) AND cq.local_status=1';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_j_course_question_info cq,question_info q '; 
	
	IF p_coures_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and cq.course_id=",p_coures_id);
	END IF;
	
	
	
	
	SET tmp_sql=CONCAT("SELECT count(*) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
END $$

DELIMITER ;
