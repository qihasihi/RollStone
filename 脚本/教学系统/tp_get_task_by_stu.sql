DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_get_task_by_stu`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_get_task_by_stu`(
					  p_course_id	BIGINT,
				          p_user_id INT,
				          p_task_id BIGINT,
				          		p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(100),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT ' t.* ';  
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT '  1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(4000) DEFAULT ' '; 
	DECLARE tmp_count_sql VARCHAR(20000) DEFAULT ' '; 
	
	
	
	SET tmp_sql = CONCAT("SELECT
  t.*
FROM (SELECT
        t.*,tc.course_name,
        ta.b_time,
        ta.e_time,
 (CASE ta.user_type WHEN 0 THEN ( SELECT COUNT(*) FROM j_class_user cu,user_info u WHERE u.ref=cu.user_id AND u.state_id=0 AND  cu.relation_type='学生' AND cu.class_id=ta.user_type_id)
 WHEN 2 THEN (SELECT COUNT(*) FROM tp_j_group_student ts WHERE ts.group_id=ta.user_type_id) END) total_count,
 ( CASE ta.user_type WHEN 0 THEN (SELECT COUNT(DISTINCT p.ref) FROM 
 tp_task_performance p,tp_task_allot_info tta,j_class_user cu,user_info u WHERE u.ref=cu.user_id AND u.state_id=0 AND p.user_id=u.ref AND  cu.relation_type='学生' AND cu.class_id=tta.user_type_id AND tta.user_type_id=ta.`user_type_id`  AND tta.task_id=p.task_id AND p.task_id=t.`TASK_ID`) 
   WHEN 2 THEN (
	SELECT COUNT(DISTINCT p.ref) FROM tp_task_performance p,tp_task_allot_info tta ,tp_j_group_student ts,user_info u WHERE u.user_id=ts.user_id AND p.user_id=u.ref AND  ts.group_id=tta.user_type_id 
	
	AND tta.task_id=p.task_id AND p.task_id=t.`TASK_ID` AND tta.user_type_id=ta.`user_type_id`
	
   ) END 
 )stucount,
 ta.user_type_id,
 (SELECT COUNT(*) FROM tp_task_performance p WHERE p.course_id=t.course_id AND p.task_id=t.task_id AND p.creteria_type=t.criteria  AND p.USER_ID=(SELECT ref FROM user_info WHERE user_id=",p_user_id,"))iscomplete,
 (CASE t.task_type 
	WHEN 1 AND t.remote_type IS NOT NULL  THEN  resource_name
	WHEN 1 AND t.remote_type IS NULL THEN  (SELECT CONCAT(res_name,file_suffixname) FROM rs_resource_info r WHERE r.res_id=t.TASK_VALUE_ID)
	WHEN 2 THEN (SELECT topic_title FROM tp_topic_info t WHERE t.topic_id=t.TASK_VALUE_ID)
	WHEN 3 THEN (SELECT content FROM question_info q WHERE q.question_id=t.TASK_VALUE_ID)
	WHEN 4 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=t.TASK_VALUE_ID)
	WHEN 5 THEN (SELECT paper_name FROM paper_info p WHERE p.paper_id=t.TASK_VALUE_ID)
	WHEN 6 THEN (SELECT res_name FROM rs_resource_info r  WHERE r.res_id=t.TASK_VALUE_ID)
	WHEN 7 THEN TASK_NAME
	WHEN 8 THEN TASK_NAME
	WHEN 9 THEN TASK_NAME
	WHEN 10 THEN TASK_NAME
	 END
 )taskobjname,
 (SELECT q.question_type FROM question_info q WHERE q.question_id=t.task_value_id)question_type   
      FROM tp_task_info t,tp_course_info tc,
      tp_task_allot_info ta,
      (SELECT ta.ref
FROM
  tp_task_allot_info ta 
WHERE ta.course_id=",p_course_id,"  and EXISTS 
  (SELECT 
    cu.CLASS_ID 
  FROM
    j_class_user cu,
    user_info u 
  WHERE cu.user_id = u.ref 
    AND cu.relation_type = '学生' 
    AND u.user_id = ",p_user_id," 
    AND u.state_id = 0 
    AND ta.user_type_id = cu.class_id 
    
  UNION
  SELECT 
    g.GROUP_ID 
  FROM
    tp_group_info g,
    tp_j_group_student gs,
    user_info u 
  WHERE u.user_id = gs.user_id 
    AND u.state_id = 0 
    AND g.group_id = gs.group_id 
    AND ta.user_type_id = gs.group_id 
    
    AND gs.user_id = ",p_user_id,")) tref
      WHERE t.`TASK_ID`=ta.task_id and t.course_id=tc.course_id and ta.ref=tref.ref
          AND t.status = 1) t
WHERE 1=1 ");
		
         
         
         
	
	IF p_course_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and t.COURSE_ID=",p_course_id);
	END IF;
	IF p_task_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and t.task_id=",p_task_id);
	END IF;
	
	SET tmp_count_sql=tmp_sql;
	
	
	
	
	IF p_sort_column IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",p_sort_column);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM (",tmp_count_sql,")t ");
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
	
END $$

DELIMITER ;
