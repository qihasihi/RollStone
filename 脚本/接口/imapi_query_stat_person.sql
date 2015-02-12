DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `imapi_query_stat_person`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `imapi_query_stat_person`(IN in_subject_id INT, IN in_class_id INT,OUT total INT)
BEGIN
	DECLARE tmp_sql VARCHAR(50000) DEFAULT '';
	DECLARE tmp_column_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_condition_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_tbl_sql VARCHAR(10000) DEFAULT '';
 
SELECT ss.*,u.ett_user_id,IFNULL(stu.stu_name,tea.teacher_name)realname FROM
 (
 SELECT ss.*,@row:=@row+1 AS rank FROM (
SELECT ci.user_id,IFNULL (SUM(course_total_score),0)course_total_score,
            IFNULL (SUM(attendance_num),0) attendance_num,IFNULL (SUM(task_score),0) task_score,ci.CLASS_ID,
            (SELECT ti.group_name FROM tp_group_info ti ,tp_j_group_student ts,class_info cls
             WHERE ts.group_id=ti.group_id AND cls.`CLASS_ID`=ci.`class_id` 
               AND ts.user_id=ci.user_id
              AND ti.subject_id=ci.subject_id)group_name FROM 
          tp_stu_score ci,class_info cls  WHERE cls.class_id = ci.class_id AND ci.CLASS_ID=in_class_id AND ci.subject_id=in_subject_id 
          AND ((cls.dc_type=3 AND ci.submit_flag=1) OR (cls.dc_type<3 AND  ci.submit_flag IS NOT NULL))
           GROUP BY ci.user_id,CLASS_ID
            ORDER BY ci.course_total_score DESC ,group_name )ss,(SELECT @row:=0)b ORDER BY ss.course_total_score DESC)ss INNER JOIN user_info u ON u.user_id=ss.user_id
            LEFT JOIN student_info stu ON stu.`USER_ID`=u.`REF`
            LEFT JOIN teacher_info tea ON tea.`USER_ID`=u.`REF`
              ORDER BY rank; 
           
	SET total=0;
END$$

DELIMITER ;