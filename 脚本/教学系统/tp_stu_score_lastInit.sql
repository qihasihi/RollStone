DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_stu_score_lastInit`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_stu_score_lastInit`(				        
				            p_course_id BIGINT,
				            p_group_idArray VARCHAR(1000),	
				            p_subject_id INT,		
					    p_class_id BIGINT,
					    p_clstype INT,
					    p_dc_school_id BIGINT,
				            OUT affect_row INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(100000) DEFAULT '';
	DECLARE t_groupid BIGINT;
	DECLARE done INT DEFAULT 0;
		-- 小组分数
	DECLARE  cur_goupScore CURSOR FOR 
	SELECT DISTINCT g.group_id FROM tp_group_info g
	 LEFT JOIN (SELECT gs.ref,gs.`group_id` FROM tp_group_score gs WHERE gs.`course_id`=p_course_id AND gs.class_id=p_class_id  AND gs.subject_id=p_subject_id) gs ON g.group_id=gs.group_id 
	 WHERE g.`CLASS_ID`=p_class_id AND g.subject_id=p_subject_id
	GROUP BY g.GROUP_ID  HAVING COUNT(gs.ref)<1;	
	
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	SET affect_row =0;
	    IF p_clstype IS NULL OR p_clstype=1 THEN
		    
			 SET tmp_sql=CONCAT('
			      INSERT INTO tp_stu_score(user_id,course_id,group_id,subject_id,class_id,class_type,dc_school_id,attendance_num,smiling_num,violation_dis_num,submit_flag,ctime)
			SELECT distinct t1.user_id,',p_course_id,',t1.group_id,',p_subject_id,',',p_class_id,',1,',p_dc_school_id,'
			,IFNULL(tcp.attendance_num,2),IFNULL(tcp.smiling_num,0),IFNULL(tcp.violation_dis_num,0),1,now()  FROM(
		       SELECT s.STU_NAME,s.stu_no,g.group_name,g.group_id,u.user_id  FROM j_class_user cu 
			INNER JOIN user_info u ON u.ref=cu.user_id
			INNER JOIN student_info s ON s.USER_ID=u.ref
			LEFT JOIN (
			SELECT gs.user_id,gs.GROUP_ID,g.GROUP_NAME,g.`CLASS_ID` FROM 
				tp_j_group_student gs,tp_group_info g 
				WHERE gs.GROUP_ID=g.GROUP_ID AND g.class_id=',p_class_id,' AND class_type=1
					AND g.subject_id=',p_subject_id,'
			) g ON g.user_id=u.user_id 
		       WHERE  cu.class_id=',p_class_id,' AND relation_type="学生"
		       ) t1
			LEFT JOIN tp_stu_score tcp ON t1.user_id=tcp.user_id AND tcp.subject_id=',p_subject_id,' AND tcp.course_id=',p_course_id,'
			LEFT JOIN tp_group_score cpad ON IF(t1.group_id IS NULL,FALSE,(t1.group_id=cpad.group_id))  AND cpad.course_id=',p_course_id,' AND cpad.subject_id=',p_subject_id,'
			INNER JOIN user_info u ON u.user_id=t1.user_id 
			LEFT JOIN student_info s ON s.user_id=u.ref WHERE 1=1 AND tcp.ref IS NULL ');
			
			IF LENGTH(p_group_idArray)>0 THEN
			SET tmp_sql=CONCAT(tmp_sql,' AND t1.group_id IN(',p_group_idArray,')');
			END IF;	
			
			SET @tmp_sql = tmp_sql;
			PREPARE stmt FROM @tmp_sql;
			EXECUTE stmt;
		ELSE
			SET tmp_sql=CONCAT('
				INSERT INTO tp_stu_score(user_id,course_id,group_id,subject_id,class_id,class_type,dc_school_id,attendance_num,smiling_num,violation_dis_num,submit_flag,ctime)
				SELECT distinct t1.user_id,',p_course_id,',t1.group_id,',p_subject_id,',',p_class_id,',2,',p_dc_school_id,'
				,IFNULL(tcp.attendance_num,2),IFNULL(tcp.smiling_num,0),IFNULL(tcp.violation_dis_num,0),1,now()  
				FROM(			
				 SELECT s.STU_NAME,s.stu_no,vcs.USER_ID,g.group_id,g.group_name FROM tp_j_virtual_class_student vcs
				 INNER JOIN user_info u ON vcs.user_id=u.user_id
				INNER JOIN student_info s ON s.user_id=u.ref
				LEFT JOIN (
				SELECT gs.user_id,gs.GROUP_ID,g.GROUP_NAME,g.`CLASS_ID` FROM 
					tp_j_group_student gs,tp_group_info g 
					WHERE gs.GROUP_ID=g.GROUP_ID AND g.class_id=',p_class_id,' AND class_type=2	
					AND g.subject_id=',p_subject_id,'		
				) g ON g.user_id=u.user_id
				WHERE vcs.VIRTUAL_CLASS_ID=',p_class_id,'
			       ) t1
				LEFT JOIN tp_stu_score tcp ON t1.user_id=tcp.user_id AND  IF(t1.group_id IS NULL,TRUE,(t1.group_id=tcp.group_id)) AND tcp.subject_id=',p_subject_id,'  AND tcp.course_id=',p_course_id,'
				LEFT JOIN tp_group_score cpad ON IF(t1.group_id IS NULL,TRUE,(t1.group_id=cpad.group_id))  AND cpad.course_id=',p_course_id,' AND cpad.subject_id=',p_subject_id,' 
				INNER JOIN user_info u ON u.user_id=t1.user_id 
				LEFT JOIN student_info s ON s.user_id=u.ref  WHERE 1=1 AND tcp.ref IS NULL ');
				IF LENGTH(p_group_idArray)>0 THEN
				SET tmp_sql=CONCAT(tmp_sql,' AND t1.group_id IN(',p_group_idArray,')');
				END IF;	
			SET @tmp_sql = tmp_sql;
			PREPARE stmt FROM @tmp_sql;
			EXECUTE stmt;		
                
		
	END IF;
	
	  SET tmp_sql=CONCAT('
	   UPDATE tp_stu_score SET submit_flag=1 WHERE 
                   class_id=',p_class_id,' AND subject_id=',p_subject_id,' AND class_type=',p_clstype);
         IF LENGTH(p_group_idArray)>0 THEN
	     SET tmp_sql=CONCAT(tmp_sql,' AND group_id IN(',p_group_idArray,')');
	END IF;	
	SET @tmp_sql1 = tmp_sql;
	PREPARE stmt1 FROM @tmp_sql1;
	EXECUTE stmt1;
	-- 查询没有录入的小组得分
	OPEN cur_goupScore;
		read_group_cur:LOOP  FETCH cur_goupScore INTO t_groupid;
		  IF done=1 THEN
		      LEAVE read_group_cur;
		    END IF;
			INSERT INTO tp_group_score(group_id,award_number,ctime,course_id,subject_id,class_id,dc_school_id,class_type) 
			VALUES(t_groupid,0,NOW(),p_course_id,p_subject_id,p_class_id,p_dc_school_id,p_clstype);
		END LOOP;
		
	CLOSE cur_goupScore;	
	
	
	-- 更新数据
	  SET tmp_sql=CONCAT('
	   UPDATE tp_group_score SET submit_flag=1 WHERE 
                   class_id=',p_class_id,' AND subject_id=',p_subject_id,' AND class_type=',p_clstype);
         IF LENGTH(p_group_idArray)>0 THEN
	     SET tmp_sql=CONCAT(tmp_sql,' AND group_id IN(',p_group_idArray,')');
	END IF;	
	SET @tmp_sql2 = tmp_sql;
	PREPARE stmt2 FROM @tmp_sql2;
	EXECUTE stmt2;
	
	SET affect_row = 1;
    END$$

DELIMITER ;