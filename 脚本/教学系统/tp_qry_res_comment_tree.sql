DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_qry_res_comment_tree`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_qry_res_comment_tree`(
				          p_res_id	varchar(500),
				          p_current_page INT,
					  p_page_size	INT,
					  p_sort_column varchar(50)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	
	set tmp_sql=' SELECT  t.*,IFNULL(IFNULL(sr.stu_name,tr.teacher_name),r.user_name)rrealname,
     IFNULL(IFNULL(sc.stu_name,tc.teacher_name),c.user_name)crealname,IFNULL(IFNULL(tc.img_heard_src,c.head_image),''images/defaultheadsrc.jpg'')headimage 
     FROM comment_info t INNER JOIN rs_resource_info rs ON t.COMMENT_OBJECT_ID=rs.res_id 
	LEFT JOIN user_info r ON r.user_id=t.REPORT_USER_ID LEFT JOIN (SELECT u.user_id uid,t.* FROM student_info t,user_info u WHERE t.USER_ID=u.ref ) sr ON sr.uid=t.REPORT_USER_ID
	LEFT JOIN (SELECT u.user_id uid,t.* FROM teacher_info t,user_info u WHERE t.USER_ID=u.ref )tr ON tr.uid=t.REPORT_USER_ID
	INNER JOIN user_info c ON c.user_id=t.COMMENT_USER_ID
	LEFT JOIN (SELECT u.user_id uid,t.* FROM student_info t,user_info u WHERE t.USER_ID=u.ref ) sc ON sc.uid =t.COMMENT_USER_ID 
	LEFT JOIN (SELECT u.user_id uid,t.* FROM teacher_info t,user_info u WHERE t.USER_ID=u.ref )tc ON tc.uid= t.COMMENT_USER_ID 
     WHERE 1=1 AND t.COMMENT_TYPE=1 AND t.COMMENT_PARENT_ID<>''0''
     
        AND FIND_IN_SET( 
     t.comment_id,getChildList_rescomment(
		     (
    SELECT GROUP_CONCAT(t.comment_id) FROM ( SELECT 
			DISTINCT t.comment_id
			FROM comment_info t WHERE t.COMMENT_TYPE=1 ';
			
        IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and t.COMMENT_OBJECT_ID='",p_res_id,"'");
	END IF;
		
				
       
	IF p_sort_column IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," order by ",p_sort_column);
	END IF;
	
	IF p_page_size IS NOT NULL AND p_current_page IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," limit ",(p_current_page-1)*p_page_size,",",p_page_size);
	END IF;
	
      set tmp_sql=concat(tmp_sql,' ) t)	)) >0'); 
	
	IF p_res_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and t.COMMENT_OBJECT_ID='",p_res_id,"'");
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
