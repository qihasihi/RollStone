DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_share_extendResource_proc_gongxiang`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_share_extendResource_proc_gongxiang`(
							p_current_page INT(10),
							p_page_size INT(10)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sortcolumn VARCHAR(1000) DEFAULT ' c_time asc ';
	
	SET tmp_sql=CONCAT("SELECT
				  rs.res_id,rs.FULL_NAME,rs.POSISIONS,rs.ROOT_EXTEND_ID,rs.VALUE_ID
				FROM rs_extend_resource rs
				WHERE res_id IN(SELECT
						  res_id
						FROM rs_share_info
						WHERE state = 0)");
	IF tmp_sortcolumn IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",tmp_sortcolumn);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;		
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
END $$

DELIMITER ;
