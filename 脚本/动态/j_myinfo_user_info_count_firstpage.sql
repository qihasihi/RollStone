DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_myinfo_user_info_count_firstpage`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_myinfo_user_info_count_firstpage`(
				          p_msg_id INT,
				          p_user_ref VARCHAR(1000),
				          p_b_time varchar(1000),
				          p_e_time VARCHAR(1000)
				         
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_condition VARCHAR(2000) DEFAULT ' 1=1 ';  
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT 'j_myinfo_user_info u'; 
	IF p_b_time IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c_time>STR_TO_DATE('",p_b_time,"', '%Y-%m-%d %h:%i:%s' )");
	END IF;
	
	IF p_e_time IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and c_time<STR_TO_DATE('",p_e_time,"', '%Y-%m-%d %h:%i:%s' )");
	END IF;
	
	IF p_msg_id IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.MSG_ID=",p_msg_id);
	END IF;
	
	
	IF p_user_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," and u.USER_REF='",p_user_ref,"'");
	END IF;
			
	SET tmp_sql=CONCAT("SELECT COUNT(u.ref) cunt FROM ",tmp_tbl_name," WHERE ",tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
END $$

DELIMITER ;
