DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_proc_getmygroup`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_group_proc_getmygroup`(
				          classid INT,
				          classtype INT,
				          termid VARCHAR(50),
				          tchid INT,
				          stuid INT,
				          subjectid INT,
				          OUT flag INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " distinct g.*,ifnull(c.CLASS_NAME,tvc.VIRTUAL_CLASS_NAME) CLASSNAME,(SELECT t.teacher_name FROM teacher_info t,user_info u WHERE u.ref=t.user_id AND u.user_id=g.c_user_id)teacher_name";  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ' tp_group_info g
			INNER JOIN tp_j_group_student gs ON gs.GROUP_ID=g.GROUP_ID
			left join class_info c on c.class_id=g.class_id and g.class_type=1
			LEFT JOIN j_class_user cu ON c.CLASS_ID=cu.CLASS_ID AND cu.RELATION_TYPE=''任课老师'' AND g.CLASS_ID=cu.CLASS_ID AND g.CLASS_TYPE=1
			left join tp_virtual_class_info tvc on tvc.virtual_class_id=g.class_id and g.class_type=2'; 
	SET flag=0;
	
	IF classid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.CLASS_ID=",classid);
	END IF;
	
	IF classtype IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.CLASS_TYPE=",classtype);
	END IF;
	
	IF termid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.TERM_ID='",termid,"'");
	END IF;
	
	IF tchid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.C_USER_ID=",tchid);
	END IF;
	
	IF stuid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and gs.USER_ID=",stuid);
	END IF;
	
	IF subjectid IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and g.subject_id=",subjectid);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1; 
	
	SET flag=1;
	
    END$$

DELIMITER ;