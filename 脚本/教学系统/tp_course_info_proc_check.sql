DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `tp_course_info_proc_check`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_course_info_proc_check`(p_user_id INT,p_quote_id BIGINT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	IF p_user_id IS NOT NULL AND p_quote_id IS NOT NULL THEN /*tc.local_status=2*/
		SET tmp_sql = CONCAT(tmp_sql,"select * from tp_course_info tc where 1=1  and   tc.cuser_id=",p_user_id," and tc.quote_id=",p_quote_id);
	END IF;
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
    END$$

DELIMITER ;