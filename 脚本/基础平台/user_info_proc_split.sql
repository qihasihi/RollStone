DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `user_info_proc_split`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `user_info_proc_split`(
				          p_ref VARCHAR(1000),
				          p_user_id INT,
				          p_user_name VARCHAR(1000),
				          p_state_id INT,
				          p_password VARCHAR(1000),
				          p_mail_address VARCHAR(1000),
				          p_lzx_user_id VARCHAR(32),
				          p_ett_user_id INT,
				          p_school_id VARCHAR(32),
				          p_dc_school_id INT,				          
							p_current_page INT(10),
							p_page_size INT(10),
							p_sort_column VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE order_tmp VARCHAR(2000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(2000) DEFAULT " u.ref,u.user_id,u.user_name,u.password,u.state_id,u.identity_number,u.address,u.dc_school_id,u.ett_user_id,
	u.birth_date,u.c_time,u.m_time,u.gender,u.head_image,u.mail_address,u.pass_question,u.question_answer,u.is_modify,u.is_activity,ui.identity_name,
	ifnull(ifnull(t.teacher_name,s.stu_name),u.user_name) real_name,
	ifnull(ifnull(t.teacher_sex,s.stu_sex),' ') user_sex ,s.stu_no,s.stu_name ";
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT " 1=1 ";
	DECLARE tmp_tbl_name_column VARCHAR(2000) DEFAULT '';
	DECLARE tmp_tbl_name VARCHAR(500) DEFAULT '';
	
	
	SET tmp_tbl_name_column='SELECT u.ref,u.user_id,u.user_name,u.password,u.state_id,u.identity_number,u.address,u.dc_school_id,u.ett_user_id,
	u.birth_date,u.c_time,u.m_time,u.gender,u.head_image,u.mail_address,u.pass_question,u.question_answer,u.is_modify,u.is_activity';
	
	 SET tmp_tbl_name=" FROM user_info u WHERE 1=1";
	
	IF p_ref IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.REF='",p_ref,"'");
	END IF;
	IF p_user_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.user_id=",p_user_id);
	END IF;
	IF p_user_name IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.user_name='",p_user_name,"'");
	END IF;
	IF p_state_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," and u.STATE_ID=",p_state_id);
	END IF;
	IF p_password IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND u.password='",p_password,"'");
	END IF;
	
	IF p_mail_address IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND u.mail_address='",p_mail_address,"'");
	END IF;
	
	IF p_lzx_user_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND u.lzx_user_id='",p_lzx_user_id,"'");
	END IF;
	
	IF p_ett_user_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND u.ett_user_id=",p_ett_user_id);
	END IF;
	
	IF p_school_id IS NOT NULL THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND u.school_id='",p_school_id,"'");
	END IF;
	
	IF p_dc_school_id>0 THEN
		SET tmp_tbl_name=CONCAT(tmp_tbl_name," AND u.dc_school_id=",p_dc_school_id);
	END IF;
	
	IF p_sort_column IS NOT NULL THEN
	    SET order_tmp=CONCAT(order_tmp," ORDER BY  ",p_sort_column);
	END IF;
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN
	    SET order_tmp=CONCAT(order_tmp," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	
	SET tmp_sql=CONCAT('SELECT ',tmp_search_column,' FROM (',tmp_tbl_name_column,tmp_tbl_name,order_tmp,') u 
		left join teacher_info t on t.user_id=u.ref
		left join student_info s on s.user_id=u.ref
		LEFT join j_user_identity_info ui ON ui.user_id=u.ref
		 WHERE ',tmp_search_condition);
	
	
	SET @sql1 =tmp_sql;
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;
	DEALLOCATE PREPARE s1;
	SET tmp_sql=CONCAT("SELECT COUNT(ref) INTO @tmp_sumCount ",tmp_tbl_name);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sumCount=@tmp_sumCount;
    END$$

DELIMITER ;