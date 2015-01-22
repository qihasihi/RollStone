DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_access_info_proc_addorupdate`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_access_info_proc_addorupdate`(
				            p_ref VARCHAR(1000),
				            p_res_id bigint,
				            p_user_id INT,
				            p_enable INT,
				            p_isfromrank INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE acc_ref VARCHAR(50) DEFAULT '';
	
	SET affect_row = 0;
	
	IF p_user_id IS NOT  NULL 
		
		AND p_ref IS  NOT NULL 
		AND p_res_id IS  NOT NULL THEN
	
	SELECT r.REF INTO acc_ref FROM rs_access_info r WHERE r.USER_ID=p_user_id AND r.RES_ID=p_res_id AND r.isfromrank=IFNULL(p_isfromrank,0);
	
	IF acc_ref IS NULL OR acc_ref="" THEN
		SET tmp_sql="INSERT INTO rs_access_info (REF,USER_ID,RES_ID,C_TIME,M_TIME,isfromrank)VALUES('";
		SET tmp_sql=CONCAT(tmp_sql,p_ref,"',",p_user_id,",",p_res_id,",NOW(),NOW(),",IFNULL(p_isfromrank,0),")");
	ELSE
		SET tmp_sql=CONCAT("UPDATE rs_access_info set M_TIME=NOW(),ISFROMRANK=",IFNULL(p_isfromrank,0));
		SET tmp_sql=CONCAT(tmp_sql," where REF='",acc_ref,"'");
	END IF;
	
	END IF;
	
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
