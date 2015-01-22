DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_course_paper_sumscore`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_course_paper_sumscore`(
				          p_paper_id BIGINT,
				          OUT sumCount Float
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' j_paper_question '; 
	
	IF p_paper_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and PAPER_ID=",p_paper_id);
	END IF;
	
	
	
	
	SET tmp_sql=CONCAT("SELECT round(SUM(score),1) INTO @tmp_sumCount FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
END $$

DELIMITER ;
