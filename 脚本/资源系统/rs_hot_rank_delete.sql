DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_hot_rank_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_hot_rank_delete`(
			p_type INT,
			 OUT affect_row INT)
BEGIN
	declare tmp_sql VARCHAR(10000) default '';
	DECLARE	EXIT HANDLER FOR NOT FOUND,SQLEXCEPTION SET affect_row =0;
        
	set tmp_sql='DELETE FROM rs_hot_rank where 1=1 ';
	if p_type IS NOT NULL THEN
		set tmp_sql=CONCAT(tmp_sql,' AND type=',p_type);
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	SET affect_row=1;
END $$

DELIMITER ;
