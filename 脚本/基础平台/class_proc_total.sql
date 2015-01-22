DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `class_proc_total`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `class_proc_total`(
	p_schoolid INT,
	p_class_year varchar(100),

	request_from INT,
	OUT totalclass INT)

BEGIN
  DECLARE tmp varchar(200) DEFAULT "";

  SET tmp=CONCAT("select count(*)into @total from class_info where YEAR='",p_class_year,"' and dc_school_id=",p_schoolid);
  if request_from <> 1 then set tmp=concat(tmp," and CLASS_GRADE not in ('¸ßÈý', '³õÈý')");
  end if;
  set @tmp=tmp;
  PREPARE stm FROM @tmp;
  EXECUTE stm;
  DEALLOCATE PREPARE stm;
  set totalclass=@total;

END$$

DELIMITER ;
