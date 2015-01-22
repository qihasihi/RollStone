DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `student_proc_update`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `student_proc_update`(
				          p_ref VARCHAR(50),
					  p_stu_id int,
					  p_stu_no VARCHAR(30),
					  p_stu_name VARCHAR(200),
					  p_stu_sex VARCHAR(10),
					  p_stu_address VARCHAR(200),
					  p_linkman VARCHAR(200),
					  p_user_ref	varchar(50),
					  p_linkman_phone VARCHAR(200),
					  OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	IF p_ref IS NULL and  p_stu_id is null AND p_user_ref IS NULL  THEN
		SET affect_row = 0;
	ELSE
		SET tmp_sql ='UPDATE student_info set m_time=NOW() ';
		IF p_stu_no IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql,",stu_no='",p_stu_no,"'");
		END IF;
		
		IF p_stu_name IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql,",stu_name='",p_stu_name,"'");
		END IF;
		IF p_stu_sex IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql,",stu_sex='",p_stu_sex,"'");
		END IF;
		
		IF p_stu_address IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql,",stu_address='",p_stu_address,"'");
		END IF;
		
		IF p_linkman IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql,",link_man='",p_linkman,"'");
		END IF;
		
		IF p_linkman_phone IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql,",link_man_phone='",p_linkman_phone,"'");
		END IF;
		
		SET tmp_sql =CONCAT(tmp_sql," WHERE 1=1 ");
		
		IF p_ref IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql," and ref='",p_ref,"'");
		END IF;
		
		IF p_stu_id IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql," and stu_id=",p_stu_id);
		END IF;
		
		IF p_user_ref IS NOT NULL THEN
			SET tmp_sql =CONCAT(tmp_sql," and user_id='",p_user_ref,"'");
		END IF;
		
		SET @tmp_sql = tmp_sql;
		PREPARE stmt FROM @tmp_sql;
		EXECUTE stmt;
		SET affect_row = 1;
	END IF;
END $$

DELIMITER ;
