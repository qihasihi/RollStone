DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_proc_excellent_resource_copy`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_resource_proc_excellent_resource_copy`(
				        p_values VARCHAR(4000),
				        p_type VARCHAR(1000),
				        p_current_login_ref VARCHAR(100),
				        p_reverse BOOLEAN,
					p_current_page INT(10),
					p_page_size INT(10),
					p_sort_column INT(1),					
				        OUT sumCount INT)
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_search_column VARCHAR(10000) DEFAULT " r.*,rf.FILE_NAME,rf.REF FILEREF,rf.PATH";
	
	DECLARE tmp_search_condition VARCHAR(10000) DEFAULT '';
	DECLARE tmp_tbl_name VARCHAR(2000) DEFAULT ''; 
	DECLARE tmp_ass_tbl_name VARCHAR(2000) DEFAULT '';
	DECLARE tmp_page_sql VARCHAR(20000) DEFAULT ''; 
	DECLARE rec_total INT DEFAULT 0;
	DECLARE score_total INT DEFAULT 0;
	DECLARE numAND INT DEFAULT 0;
	DECLARE numOR INT DEFAULT 0;
	DECLARE idx_i INT DEFAULT 0;
	DECLARE idx_j INT DEFAULT 0;
	DECLARE type_num INT DEFAULT 0;
	DECLARE type_i INT DEFAULT 0;
	DECLARE n INT DEFAULT 0;
	DECLARE values_AND VARCHAR(2000) DEFAULT '';
	DECLARE value_temp VARCHAR(2000) DEFAULT '';
	DECLARE valueschild_condition VARCHAR(5000) DEFAULT '1=1';
	
	SET tmp_tbl_name=" rs_resource_info r 
	INNER JOIN rs_resource_file_info rf ON rf.RES_ID=r.RES_ID ";
	
	IF p_values IS NOT NULL THEN
		SET tmp_ass_tbl_name=" INNER JOIN(SELECT er0.RES_ID FROM ";
		SET numAND = get_split_string_total(p_values,'|');	
		WHILE idx_i<numAND DO
			
			IF LENGTH(TRIM(get_split_string(p_values,'|',idx_i+1)))>0 THEN
			
				SET idx_j=0;
				IF n>0 THEN
					SET tmp_search_condition=CONCAT(tmp_search_condition,",");
				END IF;
				
				SET tmp_search_condition=CONCAT(tmp_search_condition," rs_extend_resource er",n);
				
				IF n>0 THEN
					SET valueschild_condition=CONCAT(valueschild_condition," AND er",n-1,".RES_ID=er",n,".RES_ID");
				END IF;
				SET valueschild_condition=CONCAT(valueschild_condition," AND er",n,".VALUE_ID IN(");
				SET values_AND=get_split_string(p_values,'|',idx_i+1);
				SET numOR = get_split_string_total(p_values,',');	
				WHILE idx_j<numOR DO
					SET value_temp=get_split_string(values_AND,',',idx_j+1);
					IF idx_j>0 THEN
					SET valueschild_condition=CONCAT(valueschild_condition,",");
					END IF;
					SET valueschild_condition=CONCAT(valueschild_condition,getChildValueList(value_temp));
					SET idx_j=idx_j+1;
				END WHILE;
				SET valueschild_condition=CONCAT(valueschild_condition,")");
				SET n=n+1;
			END IF;
			SET idx_i=idx_i+1;
		END WHILE;
		
		SET tmp_search_condition=CONCAT(tmp_search_condition," WHERE ",valueschild_condition,") rl on rl.RES_ID=r.RES_ID");
	END IF;
	
	SET tmp_search_condition=CONCAT(tmp_search_condition," WHERE 1=1 AND r.RES_STATE=1");	
	
	
	IF p_current_login_ref IS NOT NULL THEN
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND (");
		
		SET tmp_search_condition=CONCAT(tmp_search_condition,"(r.RIGHT_VIEW_ROLETYPE=1 AND EXISTS(SELECT USER_ID FROM j_role_user WHERE ROLE_ID<>1 AND USER_ID='",p_current_login_ref,"'))");
		
		SET tmp_search_condition=CONCAT(tmp_search_condition," OR r.RIGHT_VIEW_ROLETYPE=2 AND EXISTS(	");
		SET tmp_search_condition=CONCAT(tmp_search_condition," SELECT DISTINCT RIGHT_SUBJECT FROM rs_resource_right_info rr,j_user_subject us,subject_info s ");
		SET tmp_search_condition=CONCAT(tmp_search_condition," WHERE s.SUBJECT_ID=us.SUBJECT_ID AND rr.right_user_ref=us.user_id AND rr.right_subject=s.SUBJECT_NAME ");
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND right_user_ref='",p_current_login_ref,"'");
		SET tmp_search_condition=CONCAT(tmp_search_condition," AND right_roletype=2 AND right_type=1 AND res_id=r.RES_ID )");	
					
		SET tmp_search_condition=CONCAT(tmp_search_condition," OR (r.right_view_roletype=3 AND FIND_IN_SET('",p_current_login_ref,"',");
		SET tmp_search_condition=CONCAT(tmp_search_condition,"(SELECT GROUP_CONCAT(right_user_ref) FROM rs_resource_right_info rr WHERE rr.res_id=r.RES_ID))>0)) ");	
	END IF;
		
	IF p_type IS NOT NULL THEN
		SET type_num = get_split_string_total(p_type,'|');	
		IF type_num>1 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition," AND REVERSE(LEFT(REVERSE(rf.FILE_NAME),INSTR(REVERSE(rf.FILE_NAME),'.'))) ");
		END IF;
		
		IF p_reverse THEN 
			SET tmp_search_condition=CONCAT(tmp_search_condition,"NOT IN(");
		ELSE
			SET tmp_search_condition=CONCAT(tmp_search_condition,"IN(");
		END IF;
		
		WHILE type_i<type_num DO
			SET value_temp=get_split_string(p_type,'|',type_i+1);
			IF type_i>0 THEN
			SET tmp_search_condition=CONCAT(tmp_search_condition,", ");
			END IF;
			SET tmp_search_condition=CONCAT(tmp_search_condition,"'",value_temp,"'");
			SET type_i=type_i+1;
		END WHILE;
		
		SET tmp_search_condition=CONCAT(tmp_search_condition,")");
		
	END IF;
	
	SET tmp_tbl_name=CONCAT(tmp_tbl_name,tmp_ass_tbl_name);
	
	SET tmp_sql=CONCAT("SELECT COUNT(distinct r.RES_ID) into @exce_total FROM ",tmp_tbl_name,tmp_search_condition);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET rec_total=@exce_total;
	
	SET tmp_page_sql=CONCAT("SELECT ",tmp_search_column," FROM ",tmp_tbl_name,tmp_search_condition);
	
	SET tmp_page_sql=CONCAT(tmp_page_sql," ORDER BY r.RECOMENDNUM DESC,r.PRAISENUM DESC");
	
	IF p_current_page IS NOT NULL AND p_page_size IS NOT NULL THEN	
	    SET tmp_page_sql=CONCAT(tmp_page_sql," LIMIT ",(p_current_page-1)*p_page_size,',',p_page_size);
	END IF;
	
	SET tmp_sql=CONCAT("SELECT a.*,IFNULL(IFNULL(si.STU_NAME,ti.TEACHER_NAME),u.USER_NAME) USERNAME,u.ref USERREF FROM (",tmp_page_sql,")",
	"a INNER JOIN user_info u ON u.USER_ID = a.USER_ID 
        LEFT JOIN student_info si ON si.USER_ID = u.REF 
	LEFT JOIN teacher_info ti ON ti.USER_ID = u.REF");
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	SET sumCount=rec_total;
END $$

DELIMITER ;
