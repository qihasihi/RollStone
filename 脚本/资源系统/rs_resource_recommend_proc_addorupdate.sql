DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_recommend_proc_addorupdate`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_recommend_proc_addorupdate`(
				            p_ref VARCHAR(1000),
				            p_res_id bigint,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	SET affect_row = 0;
	
	update rs_resource_info set RECOMENDNUM=RECOMENDNUM+1 where RES_ID=p_res_id;
	
	delete from rs_resource_recommend where RES_ID=p_res_id;
	
	INSERT INTO rs_resource_recommend (REF,RES_ID,END_TIME,M_TIME)VALUES(p_ref,p_res_id,date_add(NOW(), interval 1 month),NOW());		
	
	SET affect_row = 1;
	
END $$

DELIMITER ;
