DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_proc_qry_clsgroup`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_proc_qry_clsgroup`(p_user_id VARCHAR(50),
				          p_term_id VARCHAR(50))
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' cl.class_id,concat(cl.class_grade,cl.class_name) as FULLNAME,cl.class_grade,
	sum(case when g.group_id is not null then 1 else 0 end) as GROUP_SUM ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1  and  cu.relation_type = ''»ŒøŒ¿œ ¶'' ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT '  
	class_info cl inner join term_info te on cl.year = te.year
	inner join j_class_user cu on cl.class_id = cu.class_id 
	inner join teacher_info t on cu.user_id = t.user_id 
	left join tp_group_info g on g.class_id = cl.class_id and g.user_id = t.user_id'; 
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and t.USER_ID='",p_user_id,"'");
	END IF;
	
	IF p_term_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and te.REF='",p_term_id,"'");
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	SET tmp_sql=CONCAT(tmp_sql," group by cl.class_id,CONCAT(cl.class_grade , cl.class_name),cl.class_grade order by cl.class_id ");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
