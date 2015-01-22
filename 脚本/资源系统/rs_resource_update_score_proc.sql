DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_update_score_proc`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_update_score_proc`(
				          p_res_id VARCHAR(50),
				          OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE avg_score float(2,1) DEFAULT '';
	DECLARE EXIT HANDLER FOR SQLWARNING,NOT FOUND,SQLEXCEPTION SET affect_row =0;
	
	if p_res_id is not null then 
		select round(AVG(s.SCORE),1) into avg_score from score_info s where SCORE_TYPE=1 and s.SCORE_OBJECT_ID=p_res_id;
		
		update rs_resource_info set RES_SCORE=avg_score where RES_ID=p_res_id;
	end if;
	
	SET affect_row = 1;
END $$

DELIMITER ;
