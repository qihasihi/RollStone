DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_im1_1_3_class_dynamic_count`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `cal_im1_1_3_class_dynamic_count`(
				        p_class_id VARCHAR(100),
				        p_user_id INT,
				        p_year VARCHAR(100),
				        OUT afficeRows  INT
				       )
BEGIN
DECLARE record_not_found INT DEFAULT 0; 
DECLARE t_cls_id INT;
DECLARE dcount INT DEFAULT 0;
DECLARE cur_cls_id CURSOR FOR
	SELECT DISTINCT c.class_id FROM class_info c WHERE c.c_user_id=p_user_id
		AND c.YEAR=p_year;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET record_not_found = 1;     
	SET afficeRows=0;
   IF p_class_id IS NOT NULL THEN
	SELECT MAX(c_time) INTO @freeCtime FROM j_myinfo_user_info WHERE class_id=p_class_id
		AND template_id=19
		AND user_ref=(SELECT ref FROM user_info WHERE user_id=p_user_id);
	IF @freeCtime IS NOT NULL THEN
		SELECT COUNT(*) INTO @dynCount FROM j_myinfo_user_info WHERE class_id=p_class_id AND template_id=18
		AND c_time >@freeCtime;
	   ELSE
		SELECT COUNT(*) INTO @dynCount FROM j_myinfo_user_info WHERE class_id=p_class_id AND template_id=18;
	 END IF;
	SET afficeRows=@dynCount;
   END IF;
   IF p_class_id IS NULL THEN
	-- 得到该所有
	OPEN cur_cls_id;
		tmpClsId:LOOP FETCH cur_cls_id INTO t_cls_id;
		     IF record_not_found=1 THEN 
                             LEAVE tmpClsId; 
                     END IF; 
		     -- 查询该班级的
			SELECT MAX(c_time) INTO @freeCtime FROM j_myinfo_user_info WHERE class_id=t_cls_id
			AND template_id=19
			AND user_ref=(SELECT ref FROM user_info WHERE user_id=p_user_id);
				SET dcount=0;
			IF @freeCtime IS NOT NULL THEN			
				SELECT COUNT(ref) INTO dcount FROM j_myinfo_user_info WHERE class_id=t_cls_id AND template_id=18
				AND c_time>@freeCtime;			
				SET afficeRows=afficeRows+dcount;
			ELSE
				SELECT COUNT(ref) INTO dcount FROM j_myinfo_user_info WHERE class_id=t_cls_id AND template_id=18;	
				SET afficeRows=afficeRows+dcount;		
			END IF;
		END LOOP tmpClsId;  
	CLOSE cur_cls_id;
   END IF;
END$$

DELIMITER ;