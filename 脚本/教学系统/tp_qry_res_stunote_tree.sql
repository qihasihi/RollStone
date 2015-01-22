DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_res_stunote_tree`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_res_stunote_tree`(
				          p_res_id	VARCHAR(500),
				          p_current_page INT,
					  p_page_size	INT,
					  p_sort_column VARCHAR(50)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	SET tmp_sql=' SELECT  t.*,IFNULL(IFNULL(sr.stu_name,tr.teacher_name),r.user_name)rrealname,
     IFNULL(IFNULL(sc.stu_name,tc.teacher_name),c.user_name)crealname,IFNULL(IFNULL(tc.img_heard_src,c.head_image),''images/defaultheadsrc.jpg'')headimage 
     from tp_ques_answer_record t 
	LEFT JOIN user_info r ON r.ref=t.REPLY_USER_ID LEFT JOIN student_info sr ON sr.user_id=t.REPLY_USER_ID
	LEFT JOIN  teacher_info tr ON tr.user_id=t.REPLY_USER_ID
	INNER JOIN user_info c ON c.ref=t.USER_ID
	LEFT JOIN student_info sc ON sc.user_id =t.USER_ID
	LEFT JOIN teacher_info tc ON tc.user_id= t.USER_ID and  t.ques_id<>0
    WHERE 1=1 
        AND FIND_IN_SET( 
     t.ref,getChildList_resstunote(
		     (
    SELECT GROUP_CONCAT(t.ref) FROM ( SELECT 
			DISTINCT t.ref
			FROM tp_ques_answer_record t WHERE t.TASK_TYPE=1 ';
			
        IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and t.QUES_PARENT_ID='",p_res_id,"'");
	END IF;
		
				
       
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	
      SET tmp_sql=CONCAT(tmp_sql,' ) t)	)) >0'); 
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and t.QUES_PARENT_ID='",p_res_id,"'");
	END IF;
	
     
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	
	
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	
	
	
END $$

DELIMITER ;
