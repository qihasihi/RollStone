DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `ett_column_info_proc_synchro`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `ett_column_info_proc_synchro`(
					p_ett_column_id INT,
					p_ett_column_name VARCHAR(100),
					p_ett_column_url VARCHAR(500),
					p_status INT,
					p_style VARCHAR(50),
					p_roletype INT,
					p_isShow INT,
					p_school_id INT,
					OUT affect_row INT
							)
BEGIN
	
	DECLARE tmp_column_sql VARCHAR(1000) DEFAULT '';
	DECLARE tmp_value_sql VARCHAR(45000) DEFAULT '';
	DECLARE tmp_sql VARCHAR(65000) DEFAULT '';
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM ett_column_info where ett_column_id=",p_ett_column_id);
	IF p_school_id IS NOT NULL THEN
	  SET tmp_sql=CONCAT(tmp_sql," AND school_id=",p_school_id);
	END IF;
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF @tmp_sumCount >0 THEN  
		  SET tmp_sql=CONCAT('UPDATE ett_column_info set ett_column_name="',p_ett_column_name,'",ett_column_url="',p_ett_column_url
				,'",status=',p_status,',style="',p_style,'",roletype=',p_roletype,',is_show=',p_isShow,' WHERE ett_column_id=',p_ett_column_id);
			IF p_school_id IS NOT NULL THEN
			  SET tmp_sql=CONCAT(tmp_sql," AND school_id=",p_school_id);
			END IF;
		ELSE
		   SET tmp_sql=CONCAT('INSERT INTO ett_column_info(ett_column_id,ett_column_name,ett_column_url,status,style,roletype,is_show');
		   
		   IF p_school_id IS NOT NULL THEN
			  SET tmp_sql=CONCAT(tmp_sql,',school_id');
		   END IF;
		   
		   SET tmp_sql=CONCAT(tmp_sql,') values(',p_ett_column_id,',"',p_ett_column_name,'","',p_ett_column_url,'",',p_status,',"',p_style,'",',p_roletype,',',p_isShow);
		  IF p_school_id IS NOT NULL THEN
			  SET tmp_sql=CONCAT(tmp_sql,',',p_school_id);
		   END IF;
		   SET tmp_sql=CONCAT(tmp_sql,')');
		
	END IF;
	SET @tmp_sql = tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;	
	SET affect_row = 1;
	
END $$

DELIMITER ;
