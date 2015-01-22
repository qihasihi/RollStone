DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi115_proc_validate_grade`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `imapi115_proc_validate_grade`(p_user_id VARCHAR(60))
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	IF p_user_id IS NOT NULL THEN 
		SET tmp_sql=CONCAT("SELECT c.`CLASS_GRADE` grade,c.`CLASS_ID` classid
					FROM class_info c,class_year_info cy,j_class_user ju
					WHERE c.`YEAR` = cy.`CLASS_YEAR_VALUE`
					AND c.`CLASS_ID`=ju.`CLASS_ID`
					AND ju.`USER_ID`='",p_user_id,"'
					AND NOW() BETWEEN cy.`B_TIME` AND cy.`E_TIME` group by classid");
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
    END$$

DELIMITER ;