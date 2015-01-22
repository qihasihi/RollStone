DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dept_proc_notin_dept_user`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dept_proc_notin_dept_user`(
				          p_role_id int,
				          p_type_id int,
				          p_name varchar(500)
				          )
BEGIN
	DECLARE tmp_sql varchar(2000) default '';
	set tmp_sql='SELECT DISTINCT u.ref user_ref,u.user_id user_id,t.teacher_name real_name,u.user_name FROM user_info u
             LEFT JOIN teacher_info t ON u.ref=t.user_id
             INNER JOIN j_role_user ru ON ru.USER_ID=u.ref
             WHERE 1=1 AND u.ref NOT IN (
                   SELECT DISTINCT ud.USER_ID FROM j_user_dept_info ud,dept_info d WHERE ud.dept_id=d.dept_id ';
                   
        IF p_type_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql,' AND d.type_id=',p_type_id);
        END IF; 
        
        SET tmp_sql=CONCAT(tmp_sql,' ) ');
        
        if p_role_id is not null then
		set tmp_sql=concat(tmp_sql,' AND ru.role_id=',p_role_id);
        end if;
        
        SET tmp_sql=CONCAT(tmp_sql,' AND ru.role_id =2 ');
        
        IF p_name IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," AND (u.user_name like '%",p_name,"%'  or t.teacher_name like '%",p_name,"%' ) ");
        END IF;
             
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
END $$

DELIMITER ;
