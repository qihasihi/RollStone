DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_share_info_proc_gongxiang`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_share_info_proc_gongxiang`(
							p_current_page INT(10),
							p_page_size INT(10)
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_sortcolumn VARCHAR(1000) DEFAULT ' c_time asc ';
	
	SET tmp_sql=CONCAT("SELECT
			  tmp.*,
			  IFNULL(t.TEACHER_NAME,u.USER_NAME)    realname
			FROM (SELECT
				r.res_id,
				res_name,
				res_keyword,
				res_introduce,
				right_view_roletype,
				right_down_roletype,
				res_state,
				school_id,
				sh.user_id
			      FROM (SELECT
				      res_id,
				      user_id
				    FROM rs_share_info
				    WHERE state = 0 ");
	IF tmp_sortcolumn IS NOT NULL THEN
	    SET tmp_sql=CONCAT(tmp_sql," ORDER BY  ",tmp_sortcolumn);
	END IF;	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_sql=CONCAT(tmp_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;		    
	SET tmp_sql=CONCAT(tmp_sql,") sh,
				rs_resource_info r
			      WHERE r.res_id = sh.res_id) tmp
			  INNER JOIN user_info u
			    ON u.user_id = tmp.user_id
			  LEFT JOIN teacher_info t
			    ON u.ref = t.USER_ID");	
	
	
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
END $$

DELIMITER ;
