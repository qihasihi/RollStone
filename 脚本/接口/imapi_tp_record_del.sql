DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_tp_record_del`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_tp_record_del`(ref bigint,
								  OUT affect_row INT)
BEGIN
  DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
  	
  	IF ref IS NOT NULL THEN
  		SET tmp_sql = CONCAT("delete from tp_record where ref=",ref,"");
  		
  		SET @tmp_sql = tmp_sql;
  		PREPARE stmt FROM @tmp_sql;
  		EXECUTE stmt;
  		
  		SET affect_row = 1;
    ELSE
  		SET affect_row = 0;
  	END IF;
END $$

DELIMITER ;
