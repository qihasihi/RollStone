DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `myinfo_cloud_info_proc_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `myinfo_cloud_info_proc_split`(
				        p_target_id LONG,
					    p_type INT,
					    p_user_id LONG,		
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column varchar(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	declare tmp_condition VARCHAR(10000) default ' 1=1 ';
	DECLARE tmp_order VARCHAR(500) DEFAULT ' ctime DESC ';
	IF p_target_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition,' AND mc.target_id=',p_target_id);
	END IF;	
	IF p_user_id IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition,' AND mc.user_id=',p_user_id);
	END IF;
	
	IF p_type IS NOT NULL THEN
		SET tmp_condition=CONCAT(tmp_condition,' AND mc.type=',p_type);
	END IF;
	IF p_sort_column IS NOT NULL AND LENGTH(p_sort_column)>0 THEN
		set tmp_order=p_sort_column;
	END IF;
	
	SET tmp_sql=CONCAT("
		SELECT mc1.*,
		IF(mc1.type=1
			,(SELECT res_name FROM rs_resource_info WHERE res_id=mc1.target_id)
			,(SELECT c.course_name FROM tp_course_info c WHERE c.course_id=mc1.target_id)
		  ) targetName
		 FROM (
		SELECT * FROM myinfo_cloud_info mc WHERE ",tmp_condition," ORDER BY ",tmp_order,") mc1	
	");	
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM myinfo_cloud_info mc WHERE ",tmp_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
