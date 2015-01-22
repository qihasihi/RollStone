DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `imapi_query_stat_person_stu`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_query_stat_person_stu`(IN in_user_id INT, IN in_subject_id INT, IN in_class_id INT,out total int)
BEGIN
	DECLARE tmp_sql VARCHAR(50000) DEFAULT '';
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_condition_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_tbl_sql VARCHAR(10000) DEFAULT '';
 
 
               SELECT  ci.user_id,IFNULL (SUM(course_total_score),0)course_total_score ,IFNULL (SUM(task_score),0) task_score,
            IFNULL (SUM(attendance_num),0) attendance_num
            FROM tp_stu_score ci ,class_info cls
		WHERE ci.`class_id`=cls.`CLASS_ID`
		AND ci.CLASS_ID=in_class_id
		AND ((cls.dc_type=3 AND ci.submit_flag=1) OR (cls.dc_type<3 AND  ci.submit_flag IS NOT NULL))
		 AND ci.subject_id=in_subject_id and submit_flag=1 AND ci.user_id=in_user_id;
            
       
            set total=0;
            
END $$

DELIMITER ;
