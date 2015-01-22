DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `comment_info_proc_split_course_class_avg`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `comment_info_proc_split_course_class_avg`(p_course_id bigint,
				        p_classid INT,
				          p_classtype INT,
				          p_tch_id INT,
				          OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' round(avg(sc.SCORE),1) AVG_SCORE';  
	DECLARE tmp_search_condition1 VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_search_condition2 VARCHAR(2000) DEFAULT ' 1=1 ';
	DECLARE tmp_tbl_name1 VARCHAR(2000) DEFAULT 'comment_info cm 
							LEFT JOIN user_info u ON cm.COMMENT_USER_ID=u.USER_ID
							LEFT JOIN student_info stu ON u.ref=stu.USER_ID
							LEFT JOIN score_info sc ON sc.COMMENT_ID=cm.COMMENT_ID
							LEFT JOIN tp_j_course_class jc ON jc.course_id=cm.COMMENT_OBJECT_ID
							LEFT JOIN j_class_user ju ON jc.class_id=ju.CLASS_ID'; 
	DECLARE tmp_tbl_name2 VARCHAR(2000) DEFAULT 'comment_info cm 
							LEFT JOIN user_info u ON cm.COMMENT_USER_ID=u.USER_ID
							LEFT JOIN student_info stu ON u.ref=stu.USER_ID
							LEFT JOIN score_info sc ON sc.COMMENT_ID=cm.COMMENT_ID
							LEFT JOIN tp_j_course_class jc ON jc.course_id=cm.COMMENT_OBJECT_ID
							LEFT JOIN tp_j_virtual_class_student jv ON jc.class_id=jv.VIRTUAL_CLASS_ID';
	SET tmp_search_condition1=CONCAT(tmp_search_condition1," and ju.USER_ID = u.REF");
	SET tmp_search_condition1=CONCAT(tmp_search_condition1," and cm.COMMENT_TYPE=2");
	SET tmp_search_condition1=CONCAT(tmp_search_condition1," and cm.COMMENT_OBJECT_ID=",p_course_id);
	SET tmp_search_condition2=CONCAT(tmp_search_condition2," and jv.USER_ID = cm.COMMENT_USER_ID");
	SET tmp_search_condition2=CONCAT(tmp_search_condition2," and cm.COMMENT_TYPE=2");
	SET tmp_search_condition2=CONCAT(tmp_search_condition2," and cm.COMMENT_OBJECT_ID=",p_course_id);
	IF p_classid IS NOT NULL THEN
		IF p_classtype=1 THEN	
			SET tmp_search_condition1=CONCAT(tmp_search_condition1," AND ju.CLASS_ID = ",p_classid);
		END IF;	
		IF p_classtype=2 THEN	
			SET tmp_search_condition2=CONCAT(tmp_search_condition2," AND jv.VIRTUAL_CLASS_ID = ",p_classid);
		END IF;	
	END IF;
	
	IF p_classtype=1 THEN
		SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name1," WHERE ",tmp_search_condition1);
	ELSEIF p_classtype=2 THEN
	        SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name2," WHERE ",tmp_search_condition2);
	ELSE 
		SET tmp_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name1," WHERE ",tmp_search_condition1," UNION ","SELECT ",tmp_search_column," FROM ",tmp_tbl_name2," WHERE ",tmp_search_condition2);
	END IF;
	
	
	
	SET @tmp_sql =tmp_sql;   
	PREPARE s1 FROM  @tmp_sql;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1; 
END $$

DELIMITER ;
