DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_resource_info_proc_synchro`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `rs_resource_info_proc_synchro`(
				            p_res_id BIGINT,
				            p_res_name VARCHAR(10000),
				            p_res_keyword VARCHAR(10000),
				            p_res_introduce VARCHAR(400000),
				            p_file_suffixname VARCHAR(1000),
				            p_file_size BIGINT,
				            p_user_id INT,
				            p_user_name VARCHAR(1000),
				            p_grade INT,
				            p_subject INT,
				            p_file_type INT,
				            p_res_type INT,
				            p_res_state INT,
				            p_share_status INT,
				            p_school_name VARCHAR(1000),
				            p_use_object VARCHAR(1000),
				            p_res_degree INT,
				            p_user_type INT,
				            p_file_name VARCHAR(100),	
				            p_difftype INT,
				            p_ismicopiece INT,		           
				            OUT affect_row INT
							)
BEGIN
	
	DECLARE tmp_sql VARCHAR(1000) DEFAULT '';
	DECLARE v_schoolName VARCHAR(100);
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM rs_resource_info where res_id=",p_res_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql  ;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF @tmp_sumCount >0 THEN
	    CALL rs_resource_info_proc_update(			          
					p_res_id,
				          p_res_name,
				          p_res_keyword,
				          p_res_introduce,
				          
				          p_user_id ,
				          p_user_type ,
				          p_user_name,
				          p_school_name ,
				          
				          p_subject ,
				          p_grade ,
				          p_res_type ,
				          p_file_type ,
				         
				          p_file_size ,
				          
				          p_res_state ,
				          p_res_degree ,
				          p_share_status ,
				          p_use_object,
				          
				          NULL,
				          NULL ,
				          NULL ,
				          NULL ,
				          NULL ,
				          NULL ,
				          NULL,
				          NULL ,
				          p_file_suffixname,
				          NULL,
				          p_difftype,
				          p_ismicopiece,
				           affect_row 
				          );	          
				          
				          
				          
				          
	  ELSE
		IF p_school_name IS NULL THEN
			SET v_schoolName="北京四中网校";
		  ELSE
			SET v_schoolName=p_school_name;
		END IF;
	  
	    CALL `rs_resource_info_proc_add`(
				            p_res_id ,
				            p_res_name ,
				            p_res_keyword ,
				            p_res_introduce ,
				            p_file_suffixname ,
				            p_file_size ,
				            p_user_id ,
				            p_user_name ,
				            p_grade ,
				            p_subject ,
				            p_file_type ,
				            p_res_type ,
				            p_res_state ,
				            p_share_status ,
				            v_schoolName ,
				            p_use_object ,
				            p_res_degree ,
				            p_user_type ,
				            p_file_name ,
				            NULL,
				            p_difftype,
				            p_ismicopiece,
					    affect_row 
				);
	END IF;
	
    END$$

DELIMITER ;