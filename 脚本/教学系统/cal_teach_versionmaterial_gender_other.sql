DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_teach_versionmaterial_gender_other`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `cal_teach_versionmaterial_gender_other`(OUT afficeRows INT)
BEGIN
		
	
	DECLARE c_grade_id INT;
	DECLARE c_gradeValue VARCHAR(100);	
	
	DECLARE c_subject_id INT;
	DECLARE c_subjectName VARCHAR(100);
	
	DECLARE tmp_version_id INT default -1;
	
	
	DECLARE tmp_teaching_material_id INT;
	  
	DECLARE done INT;
	
	
	
	
	DECLARE cur_allGrade CURSOR FOR SELECT grade_id,grade_value FROM grade_info;	
	
	DECLARE cur_allSubject CURSOR FOR SELECT subject_id,subject_name FROM subject_info;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION SET afficeRows = 0;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	SET afficeRows=1;
	
	SELECT COUNT(*) INTO @otherVersionCount FROM teach_version_info where version_id=tmp_version_id;
	IF @otherVersionCount<1 THEN
	  
	  INSERT INTO teach_version_info(version_id,version_name,remark,c_user_id,c_time)
	  VALUES(tmp_version_id,'其它','系统自动成生，版本其它!',0,now());	  
	END IF;
	
	OPEN cur_allGrade;
	loop_allGrade:LOOP FETCH cur_allGrade INTO c_grade_id,c_gradeValue;
			  IF done=1 THEN
				LEAVE loop_allGrade;
			  END IF;
		OPEN cur_allSubject;		
		loop_subject:LOOP FETCH cur_allSubject INTO c_subject_id,c_subjectName;
			 IF done=1 THEN
				LEAVE loop_subject;
			  END IF;
			 
			 SELECT COUNT(tm.material_id) INTO @teachMaterialCount FROM teaching_material_info  tm WHERE tm.version_id=tmp_version_id AND subject_id=c_subject_id AND grade_id=c_grade_id;
			 
			 IF @teachMaterialCount<1 THEN
			   
			   set tmp_teaching_material_id=CONVERT(CONCAT(tmp_version_id,c_grade_id,c_subject_id),SIGNED);
			   
			   IF tmp_teaching_material_id>0 THEN
				set tmp_teaching_material_id=tmp_teaching_material_id*-1;
			   END IF;
			   
			   INSERT INTO teaching_material_info(material_id,version_id,grade_id,subject_id,material_name,remark,c_user_id,c_time)
			   VALUES(tmp_teaching_material_id,tmp_version_id,c_grade_id,c_subject_id,'其它',
				CONCAT('系统自动生成！',c_gradeValue,'年级',c_subjectName,'学科 其它'),
				0,now());
			 END IF;  
		END LOOP;
		CLOSE cur_allSubject;
		set done=0;
	END LOOP;
	CLOSE cur_allGrade;	
END $$

DELIMITER ;
