DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_task_myinfo_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_task_myinfo_add`(
				            p_task_id   bigint,
				            p_course_id bigint,
				            p_user_id varchar(50),
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql1 VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql2 VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql3 VARCHAR(1000) DEFAULT '';
	DECLARE tmp_crealname VARCHAR(100);
	DECLARE tmp_task_type_name VARCHAR(100);
	DECLARE tmp_c_user_id VARCHAR(100);
	DECLARE tmp_courseid VARCHAR(100);
	DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	
		
	SET tmp_sql2=CONCAT("SELECT c_user_id INTO @tmp_c_user_id FROM tp_task_info  WHERE task_id= '",p_task_id,"'");	
	SET @tmp_sql2=tmp_sql2;
	PREPARE stmt2 FROM @tmp_sql2  ;
	EXECUTE stmt2;
	DEALLOCATE PREPARE stmt2;
	set tmp_c_user_id=@tmp_c_user_id;
		
	SET tmp_sql1=CONCAT("SELECT IFNULL(IFNULL(tea.teacher_name,stu.stu_name),u.USER_NAME) INTO @tmp_crealname FROM user_info u LEFT JOIN teacher_info tea ON tea.user_id=u.ref LEFT JOIN student_info stu ON stu.user_id=u.ref WHERE u.REF= '",tmp_c_user_id,"'");		
	SET @tmp_sql1=tmp_sql1;
	PREPARE stmt1 FROM @tmp_sql1  ;
	EXECUTE stmt1;
	DEALLOCATE PREPARE stmt1;
	SET tmp_crealname=@tmp_crealname;	
	
		
	SET tmp_sql=CONCAT("SELECT CASE task_type WHEN 1 THEN '资源学习' WHEN 2 THEN '互动交流' WHEN 3 THEN '课后作业' ELSE '' END  INTO @tmp_task_type_name FROM tp_task_info where task_id= ",p_task_id);		
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET tmp_task_type_name=@tmp_task_type_name;	
	
		
	
	CALL j_myinfo_user_info_add_tongy(16,p_user_id,14,'通知',tmp_c_user_id,CONCAT(tmp_crealname,'|',tmp_task_type_name,'#ETIANTIAN_SPLIT#',p_course_id),@officeRows);	
	SET affect_row=@officeRows;     
		
	SET affect_row =1;
	
END $$

DELIMITER ;
