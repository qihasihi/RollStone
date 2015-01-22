DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `teacher_course_student_split`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `teacher_course_student_split`(p_page_size  INT,
                                          p_page_index  INT,
                                          p_user_id  VARCHAR(100),
                                          p_term_id    VARCHAR(100),
                                          OUT rowNum        INT
                                         )
BEGIN
   DECLARE  t_psize     INT;
   DECLARE t_pidx      INT;
   DECLARE t_order_str VARCHAR(500);
   DECLARE v_sql       VARCHAR(4000);
   DECLARE tmp_condition VARCHAR(1000) DEFAULT ' 1=1 AND tc.available<>2 ';
   DECLARE tmp_tblname VARCHAR(1000) DEFAULT ' FROM tp_teacher_course_info tc
		INNER JOIN teacher_info t ON t.user_id=tc.user_id
		LEFT JOIN tp_course_class cc ON cc.course_id=tc.course_id
		LEFT JOIN j_class_user cu ON cu.class_id=cc.class_id AND cu.relation_type="Ñ§Éú"
		LEFT JOIN class_info cla ON cla.class_id=cu.class_id ';
	  
  
    SET t_order_str = ' tc.course_id desc';
    
    IF p_page_size IS NOT NULL THEN
     SET  t_psize = p_page_size;
    ELSE
      SET t_psize = 999999999;
    END IF;
    IF p_page_index IS NOT NULL THEN
      SET t_pidx = p_page_index;
    ELSE
      SET t_pidx = 1;
    END IF;
  
   
    SET v_sql = 'SELECT tc.available,tc.course_id,tc.course_name,t.teacher_name,
	tc.term_id,DATE_FORMAT(cc.classtime,''yy-mm-dd hh24:mi:ss'') classtimes,tc.subject_id ';
		
  
    IF p_user_id IS NOT NULL THEN
      SET tmp_condition = CONCAT(tmp_condition,' AND cu.user_id=',"'", p_user_id,"'");
    END IF;
  
    IF p_term_id IS NOT NULL THEN
	SET tmp_condition = CONCAT(tmp_condition,' AND tc.term_id=',"'", p_term_id,"'");
    END IF;
    SET v_sql=CONCAT(v_sql,tmp_tblname," WHERE ",tmp_condition);
    
    SET v_sql =  CONCAT(v_sql,' ORDER BY ' , t_order_str);
  
    IF t_psize IS NOT NULL AND p_page_index IS NOT NULL THEN
	SET v_sql=CONCAT(v_sql," limit ",(p_page_index-1)*t_psize,",",t_psize);
    END IF;
    SET @tmp_sql = v_sql;
    PREPARE stmt FROM @tmp_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  
     
	SET v_sql=CONCAT(" select count(tc.course_id) into @totalcount ",tmp_tblname," where ",tmp_condition);
	SET @tmp_sql2 = v_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	SET rowNum = @totalcount;
  
  
END $$

DELIMITER ;
