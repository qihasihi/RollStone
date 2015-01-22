DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_info_proc_split_bysubject`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_group_info_proc_split_bysubject`(p_termid VARCHAR(60),
								p_classid INT,
								p_subjectid INT,
								p_classtype INT
								)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT 'g.*';
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'tp_group_info g';
	IF p_classtype IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.class_type=",p_classtype);
	END IF;
	IF p_termid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.term_id='",p_termid,"'");
	END IF;
	IF p_classid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.class_id=",p_classid);
	END IF;
	IF p_subjectid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.SUBJECT_ID=",p_subjectid);
	END IF;
	SET tmp_search_condition=CONCAT(tmp_search_condition," order by g.c_time ");
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
    END$$

DELIMITER ;