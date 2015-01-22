DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_group_info_proc_check_groupname`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_group_info_proc_check_groupname`(
						  p_term_id VARCHAR (50),
						  p_user_id INT,
						  p_group_id BIGINT,
						  p_group_name VARCHAR (1000),
						  p_class_id INT,
						  p_class_type INT,
						  p_subject_id INT,
						  OUT sumCount INT
						)
BEGIN
  SET sumCount = - 1 ;
  IF p_group_id IS NOT NULL THEN 
	SELECT 
		COUNT(*) INTO @temp 
	FROM
		tp_group_info g,
		(SELECT * FROM tp_group_info WHERE GROUP_ID = p_group_id) t 
	WHERE g.class_id = t.class_id 
	AND g.class_type=p_class_type
	AND g.c_user_id = t.c_user_id 
	AND g.term_id = t.term_id 
	AND g.group_name = p_group_name ;
	IF @temp > 0 THEN 
		SET sumCount = 1 ;
	END IF ;
  END IF ;
  
  IF p_term_id IS NOT NULL AND p_user_id IS NOT NULL AND p_class_id IS NOT NULL AND p_subject_id IS NOT NULL THEN 
	SET sumCount = - 1 ;
	SELECT COUNT(*) INTO @temp FROM tp_group_info g 
	WHERE g.class_id = p_class_id 
	AND g.class_type=p_class_type
	AND g.c_user_id = p_user_id 
	AND g.term_id = p_term_id 
	AND g.subject_id=p_subject_id
	AND g.group_name = p_group_name;
	IF @temp > 0 THEN 
		SET sumCount = 1 ;
	END IF ;
  END IF ;
END $$

DELIMITER ;
