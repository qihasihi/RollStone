DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `tp_qry_stu_performance`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_stu_performance`(
					  p_task_id	VARCHAR(50),
				          p_class_id INT,
				          p_class_type INT,
				          p_order_str VARCHAR(200))
BEGIN
	DECLARE tmp_sql VARCHAR(30000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(30000) DEFAULT '';
	DECLARE tmp_sql1 VARCHAR(30000) DEFAULT '';
	DECLARE tmp_order VARCHAR(30000) DEFAULT ' t.c_time desc  ';
        DECLARE tmp_search_sql VARCHAR(20000) DEFAULT 'SELECT DISTINCT u.user_id uid,t.task_id,t.task_type,s.user_id,s.stu_no,
	s.stu_name,ttp.c_time,(SELECT COUNT(*) FROM tp_task_performance per where per.task_id=t.task_id and per.user_id=s.user_id)STATUS,
			(SELECT is_right FROM tp_task_performance per WHERE per.user_id=s.user_id AND per.task_id=t.task_id) isright,
			            ttp.creteria_type,
                                     CASE t.task_type WHEN 2 THEN (SELECT GROUP_CONCAT(theme.theme_title) FROM tp_topic_theme_info theme left join user_info u on theme.c_user_id=u.user_id WHERE theme.topic_id=t.task_value_id and u.ref=ttp.user_id) 
	WHEN 1 THEN (SELECT tp.answer_content FROM tp_ques_answer_record tp WHERE tp.ques_id=0 AND tp.USER_ID=ttp.user_id and tp.task_id=t.task_id limit 0,1)
	ELSE  (SELECT GROUP_CONCAT(tp.ANSWER_CONTENT) FROM tp_ques_answer_record tp WHERE tp.task_id=t.task_id and tp.user_id=s.user_id) END answercontent, 
	(SELECT GROUP_CONCAT(tp.REPLY_ATTACH) FROM tp_ques_answer_record tp WHERE tp.task_id=t.task_id and tp.user_id=s.user_id) replyattach,
	CASE t.task_type WHEN 4 THEN (
		    SELECT pl.score  FROM stu_paper_logs pl,user_info u
		    WHERE pl.paper_id=t.task_value_id 
		    AND pl.user_id=u.user_id AND u.ref=s.user_id
		    AND pl.is_marking=0 )
		    WHEN 5 THEN (
		    SELECT pl.score  FROM stu_paper_logs pl,user_info u,paper_info p
		    WHERE pl.paper_id=p.paper_id AND  t.task_value_id =p.parent_paperid
		    AND pl.user_id=u.user_id AND u.ref=s.user_id
		    AND pl.is_marking=0 ) END score';	
		    
		    
	
	SET tmp_sql=' FROM tp_task_info t INNER JOIN tp_task_allot_info allot ON t.task_id=allot.task_id		      
		     INNER JOIN tp_task_performance ttp ON allot.task_id=ttp.task_id
		     LEFT JOIN tp_ques_answer_record ar ON ttp.task_id = ar.task_id
		     INNER JOIN student_info s ON ttp.user_id=s.user_id
		     INNER JOIN user_info u on u.ref=s.user_id ';
	
	IF p_class_type=0 THEN
		SET tmp_sql2=CONCAT(tmp_search_sql,",
		    (SELECT CONCAT(c.class_grade,c.class_name) FROM class_info c INNER JOIN j_class_user cu ON c.class_id=cu.class_id LEFT JOIN tp_group_info g ON g.class_id=c.class_id
 WHERE cu.user_id=ttp.user_id AND  (c.class_id=allot.user_type_id OR g.group_id=allot.user_type_id) limit 1)clsname ",tmp_sql," INNER JOIN j_class_user jc ON ttp.USER_ID=jc.USER_ID");
		SET tmp_sql2=CONCAT(tmp_sql2," WHERE 1=1 AND t.task_id=",p_task_id);
		SET tmp_sql2=CONCAT(tmp_sql2," union ",tmp_search_sql,", null clsname ",tmp_sql," LEFT JOIN tp_j_virtual_class_student jv ON u.user_id=jv.USER_ID");
		SET tmp_sql2=CONCAT(tmp_sql2," WHERE 1=1 AND t.task_id=",p_task_id);
	ELSEIF p_class_type=1 OR p_class_type=8 THEN
		SET tmp_sql=CONCAT(tmp_search_sql,tmp_sql);		
		SET tmp_sql2=CONCAT(tmp_sql," INNER JOIN j_class_user jc ON ttp.USER_ID=jc.USER_ID");
		SET tmp_sql2=CONCAT(tmp_sql2," WHERE 1=1 AND t.task_id=",p_task_id," AND jc.CLASS_ID=",p_class_id);
	ELSEIF p_class_type=2 OR p_class_type=9 THEN
		SET tmp_sql=CONCAT(tmp_search_sql,tmp_sql);		
		SET tmp_sql2=CONCAT(tmp_sql," LEFT JOIN tp_j_virtual_class_student jv ON u.user_id=jv.USER_ID");
		SET tmp_sql2=CONCAT(tmp_sql2," WHERE 1=1 AND t.task_id=",p_task_id," AND jv.virtual_class_id=",p_class_id);
	ELSE
		
	        SET tmp_sql2=CONCAT(tmp_sql," INNER JOIN j_class_user jc ON ttp.USER_ID=jc.USER_ID");
		SET tmp_sql2=CONCAT(tmp_sql2," WHERE 1=1 AND t.task_id=",p_task_id);
		SET tmp_sql2=CONCAT(tmp_sql2," union ",tmp_sql," LEFT JOIN tp_j_virtual_class_student jv ON ttp.USER_ID=jv.USER_ID");
		SET tmp_sql2=CONCAT(tmp_sql2," WHERE 1=1 AND t.task_id=",p_task_id);
	END IF;
	
	
	SET tmp_sql1=CONCAT("SELECT * FROM (SELECT t.*,@row:=@row+1 AS ROW,@rank := IF(@prev_score=score,@rank ,@row) AS rank,
	  @prev_score := score
	  FROM (");
	  
	  IF p_class_type=0 THEN
		SET tmp_sql2=CONCAT(tmp_sql1,"SELECT * FROM (",tmp_sql2," order by score desc)t where clsname is not null )t ");
	  ELSE 
		SET tmp_sql2=CONCAT(tmp_sql1,tmp_sql2,"  order by score desc)t ");
	  END IF;	
	  
	  SET tmp_sql2=CONCAT(tmp_sql2,",(SELECT @rank:=0,@ROW:=0,@prev_score:=NULL)a )t "); 
	  
	  IF p_order_str IS NOT NULL THEN 
		SET tmp_sql2=CONCAT(tmp_sql2," order by ",p_order_str,""); 
	  END IF;
		  
	 
	 
	 
	 
	 
     
	SET @sql1 =tmp_sql2;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
    END$$

DELIMITER ;