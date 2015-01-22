DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_virtual_class_info_proc_split_bytch`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `tp_virtual_class_info_proc_split_bytch`(
				          p_user_id VARCHAR(50),
				          p_year VARCHAR(50),
				          OUT sumCount INT
				          )
BEGIN
	DECLARE tmp_sql VARCHAR(20000) DEFAULT '';
	DECLARE tmp_class_sql VARCHAR(2000) DEFAULT '';  
	DECLARE tmp_virtual_class_sql VARCHAR(2000) DEFAULT '';  
	
	SET tmp_class_sql=CONCAT("SELECT aa.CLASS_ID,aa.CLASS_NAME,1 CLASS_TYPE,
	  (SELECT COUNT(*) FROM j_class_user scu left join user_info u on scu.user_id=u.ref WHERE CLASS_ID = aa.CLASS_ID AND scu.relation_type = '学生' and u.state_id=0) STU_NUM ,0 STATUS, 
	  IF(EXISTS(SELECT jtc.ref FROM j_trusteeship_class jtc,user_info u WHERE jtc.trust_class_id=aa.class_id AND jtc.trust_teacher_id=u.user_id
	  AND u.REF='",p_user_id,"' AND jtc.trust_class_type = 1 and jtc.is_accept <>2),1,0) TRUSTEESHIP_TYPE
	  FROM (SELECT DISTINCT tcu.CLASS_ID,CONCAT(c.CLASS_GRADE,c.CLASS_NAME) CLASS_NAME FROM j_class_user tcu INNER JOIN class_info c ON tcu.CLASS_ID=c.CLASS_ID
	  WHERE tcu.relation_type = '任课老师' AND tcu.USER_ID = '",p_user_id,"' AND c.YEAR='",p_year,"' ORDER BY tcu.CLASS_ID) aa");
	
	
	SET tmp_virtual_class_sql=CONCAT("SELECT tvc.VIRTUAL_CLASS_ID CLASS_ID,tvc.VIRTUAL_CLASS_NAME CLASS_NAME,2 CLASS_TYPE,
	  (SELECT COUNT(*) FROM tp_j_virtual_class_student tv left join user_info u on tv.user_id=u.user_id  WHERE tv.VIRTUAL_CLASS_ID = tvc.VIRTUAL_CLASS_ID and u.state_id=0) STU_NUM,tvc.STATUS,
	  IF(EXISTS(SELECT jtc.ref FROM j_trusteeship_class jtc,user_info ui WHERE jtc.trust_class_id=tvc.VIRTUAL_CLASS_ID AND jtc.trust_teacher_id=ui.user_id
	  AND ui.REF='",p_user_id,"' AND jtc.trust_class_type = 2 and jtc.is_accept <>2),1,0) TRUSTEESHIP_TYPE
	  FROM tp_virtual_class_info tvc
	  INNER JOIN user_info u ON u.user_id=tvc.c_user_id 
	  WHERE u.ref='",p_user_id,"' and tvc.status=1");	
	
	SET tmp_sql=CONCAT(tmp_class_sql," UNION ",tmp_virtual_class_sql);
	
	SET @sql1 =tmp_sql;   
	PREPARE s1 FROM  @sql1;   
	EXECUTE s1;   
	DEALLOCATE PREPARE s1;  
	
	
END $$

DELIMITER ;
