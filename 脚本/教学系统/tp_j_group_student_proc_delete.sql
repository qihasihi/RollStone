DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_j_group_student_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_j_group_student_proc_delete`(
				            p_ref INT,
				            p_group_id BIGINT,
				            p_user_id INT,
				            p_isleader INT,
				            p_class_id INT,
							OUT affect_row INT)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	SET affect_row = 0;
	SET tmp_sql="delete from tp_j_group_student where 1=1";
	
	IF p_group_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and GROUP_ID=",p_group_id);
	END IF;
	
	IF p_ref IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and REF=",p_ref);
	END IF;
	
	IF p_user_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and USER_ID=",p_user_id);
	END IF;
	
	IF p_isleader IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and ISLEADER=",p_isleader);
	END IF;
	
	IF p_class_id IS NOT NULL THEN
		SET tmp_sql=CONCAT(tmp_sql," and group_id in (select group_id from tp_group_info where class_id=",p_class_id,")");
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
