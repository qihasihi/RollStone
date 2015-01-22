DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_myinfo_proc_add_tpres_dynamic`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_myinfo_proc_add_tpres_dynamic`(
				            p_course_id bigint,
				            p_res_id	bigint,
						OUT affect_row INT
							)
BEGIN
			DECLARE tmp_user_id VARCHAR(50);	
			DECLARE tmp_realname VARCHAR(500);	
			DECLARE tmp_filename VARCHAR(500);	
			DECLARE tmp_courseid VARCHAR(500);	
			
			DECLARE cursor_done INT DEFAULT 0;
			DECLARE TEACHER_CURSOR CURSOR FOR SELECT t.USER_ID FROM tp_course_info tea,user_info u,teacher_info t WHERE u.user_id=tea.cuser_id and t.user_id=u.ref AND COURSE_ID=p_course_id;		
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done=1;
			
			       
			       SELECT IFNULL(tea.teacher_name,IFNULL(stu.stu_name,u.user_name)) realname,rd.res_name,rb.course_id
			       into tmp_realname,tmp_filename,tmp_courseid
			     FROM rs_resource_info rd 
			       INNER JOIN tp_j_course_resource_info rb ON rb.res_id=rd.res_id 
				LEFT JOIN user_info u ON u.user_id=rd.user_id
				 LEFT JOIN (SELECT u.user_id,tea.teacher_name FROM teacher_info tea,user_info u WHERE tea.user_id=u.ref)tea ON tea.user_id=rd.user_id
				   LEFT JOIN (SELECT u.user_id ,stu.stu_name FROM student_info stu,user_info u WHERE stu.user_id=u.ref)stu ON stu.user_id=rd.user_id
				   WHERE 1=1 AND  rd.user_type=1 AND rd.res_id=p_res_id;
			
			 OPEN TEACHER_CURSOR;
			   tmp_loop:LOOP FETCH TEACHER_CURSOR INTO tmp_user_id;	
			   IF cursor_done=1 THEN
				LEAVE tmp_loop;
			   END IF;					
			       CALL j_myinfo_user_info_add_tongy(15,tmp_user_id,3,'…Û∫À',NULL,CONCAT(tmp_realname,"|",tmp_filename,"#ETIANTIAN_SPLIT#",tmp_courseid),@officeRows);	
			       SET affect_row=@officeRows;      
			  END LOOP tmp_loop;
			 CLOSE TEACHER_CURSOR;
END $$

DELIMITER ;
