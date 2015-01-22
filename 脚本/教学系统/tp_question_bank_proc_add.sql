DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_question_bank_proc_add`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_question_bank_proc_add`(
					    p_question_id VARCHAR(50),
				            p_parent_ques_id varchar(50),
				            p_ques_name VARCHAR(1000),
				            p_ques_type INT,
				            p_c_user_id varchar(50),
				            p_is_right varchar(2),
				            p_ques_answer VARCHAR(4000),
				            p_order_idx	int,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="INSERT INTO tp_question_bank (";
	
	IF p_parent_ques_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"PARENT_QUES_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_parent_ques_id,"',");
	END IF;
	
	IF p_is_right IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"IS_RIGHT,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_is_right,"',");
	END IF;
	
	IF p_ques_answer IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUES_ANSWER,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ques_answer,"',");
	END IF;
	
	
	IF p_ques_type IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUES_TYPE,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_ques_type,",");
	END IF;
	
	IF p_question_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUESTION_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_question_id,"',");
	END IF;
	
	IF p_ques_name IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"QUES_NAME,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_ques_name,"',");
	END IF;
	
	IF p_c_user_id IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"C_USER_ID,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,"'",p_c_user_id,"',");
	END IF;
	
	IF p_order_idx IS NOT NULL THEN
		SET tmp_column_sql=CONCAT(tmp_column_sql,"ORDER_IDX,");
		SET tmp_value_sql=CONCAT(tmp_value_sql,p_order_idx,",");
	END IF;
	
	
	SET tmp_sql=CONCAT(tmp_sql,tmp_column_sql,"CTIME)VALUES(",tmp_value_sql,"NOW())");
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
