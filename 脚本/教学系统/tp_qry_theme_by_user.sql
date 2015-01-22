DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_theme_by_user`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_theme_by_user`(
					 p_user_id VARCHAR(50),
				          p_course_id VARCHAR(50),
				          p_clsid_str VARCHAR(500)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE t_insql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT 'DISTINCT tp.*  ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '   tp.state=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' interactive_theme_info tp,interactive_columns_info ci,tp_teacher_course_info tc '; 
	
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tp.C_USER_ID='",p_user_id,"'");
	END IF;
	
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tp.courseteacher_id='",p_course_id,"'");
	END IF;
	
	IF p_clsid_str IS NOT NULL THEN
		
		SET tmp_search_condition=CONCAT(tmp_search_condition," and tp.COLUMNS_ID IN (
				SELECT DISTINCT COLUMNS_ID FROM interactive_columns_info c WHERE  c.class_id= ",p_clsid_str," and c.state<>2)");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET tmp_sql=CONCAT(tmp_sql," order by tp.c_time");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
