DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `j_paper_question_zizhuzujuan`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `j_paper_question_zizhuzujuan`(
				          p_tptask_id BIGINT,
				          p_user_id INT,
							OUT affect_row INT
							)
BEGIN
	DECLARE tmp_sql VARCHAR(10000) DEFAULT '';
	DECLARE tmp_sql_2 VARCHAR(10000) DEFAULT '';
	DECLARE z_number INT DEFAULT 0;
	DECLARE z_paperid BIGINT;
	DECLARE tmp_num VARCHAR(500) DEFAULT '';
	
	DECLARE t_quesid_str VARCHAR(10000) DEFAULT ',';
	DECLARE t_quesSum BIGINT;
	DECLARE t_chanum BIGINT DEFAULT 10;
	DECLARE t_sort INT DEFAULT 1;	
	
	DECLARE cursor_done INT DEFAULT 0;
	DECLARE	EXIT HANDLER FOR SQLEXCEPTION SET affect_row =0;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done=1;
	
	SET affect_row=1;
	
	SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @tmp_sumCount FROM tp_task_info where task_id=",p_tptask_id);
	SET @tmp_sql=tmp_sql;
	PREPARE stmt FROM @tmp_sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	IF @tmp_sumCount >0 THEN
	     SET tmp_sql=CONCAT("SELECT task_value_id,course_id,ques_num INTO @paperid,@courseid,@quesnum FROM tp_task_info WHERE task_id=",p_tptask_id);
		SET @tmp_sq1l=tmp_sql;
		PREPARE stmt1 FROM @tmp_sq1l;
		EXECUTE stmt1;
		DEALLOCATE PREPARE stmt1;
		
		IF @paperid IS NOT NULL AND @courseid IS NOT NULL AND @quesnum IS NOT NULL AND @quesnum>0 THEN
		SET z_paperid=CONCAT('-',SUBSTR(UNIX_TIMESTAMP(NOW()),5),ROUND(ROUND(RAND(),4)*10000))+0;
		        INSERT INTO paper_info(paper_id,paper_name,score,paper_type,c_user_id,c_time,parent_paperid)
		        VALUES(z_paperid,CONCAT('×ÔÖ÷²âÊÔ',p_user_id),
			   100,4,p_user_id,NOW(),@paperid
		        );
		        
		        SET t_quesSum=0;	
		        SET t_chanum=@quesnum;  
		   	
		        
		        BEGIN
				DECLARE t_quesid BIGINT;
				DECLARE t_quesCount BIGINT;
				DECLARE t_istest INT;
				
				DECLARE yx_quesTeam_cur CURSOR FOR
				SELECT * FROM (
				SELECT cq.question_id,(SELECT COUNT(*) FROM j_ques_team_rela qtr,question_info q1 WHERE qtr.ques_id=q1.question_id AND qtr.team_id=q.`question_id`) qtCount,(
				 SELECT COUNT(*) FROM j_paper_question  WHERE paper_id IN (
					SELECT paper_id FROM `stu_paper_logs` WHERE user_id=p_user_id
					) AND question_id=cq.`question_id` LIMIT 0,1
				) isTest FROM tp_j_course_question_info cq,question_info q
				WHERE cq.question_id=q.question_id AND q.question_type=6 AND course_id=@courseid
				  AND EXISTS(
					SELECT 1 FROM j_ques_team_rela qt,question_info tq WHERE tq.`question_id`=qt.`ques_id`
						AND tq.question_type  NOT IN (3,4,7,8)  AND qt.team_id=q.question_id
				 )
				ORDER BY isTest,qtCount DESC
				) t WHERE t.qtCount<=@quesnum AND isTest<1;
				
				
				OPEN yx_quesTeam_cur;
				questeam_loop:LOOP FETCH yx_quesTeam_cur INTO t_quesid, t_quesCount,t_istest;
					IF cursor_done=1 THEN
						LEAVE questeam_loop;
					END IF;
				
				     IF t_istest<1 THEN
				     
				  
				     
					IF t_quesCount>0 THEN
						
						INSERT INTO j_paper_question(paper_id,question_id,order_idx,c_time)
						VALUES(z_paperid,t_quesid,t_sort,NOW());
						SET t_sort=t_sort+t_quesCount;	
						SET t_quesSum=t_quesSum+t_quesCount;
						SET t_chanum=t_chanum-t_quesCount;
						
						IF t_quesSum>=t_chanum THEN
							LEAVE questeam_loop;
						END IF;
					END IF;					       
				     END IF;	     
				     IF t_chanum<=0 THEN
				       LEAVE questeam_loop;
				     END IF;
				     		     
				  END LOOP questeam_loop;			    
				CLOSE yx_quesTeam_cur;	
				  
				  
		        END;
		         IF t_chanum=@quesnum AND @quesnum>0 THEN
			begin
						DECLARE t_quesid BIGINT;
						DECLARE t_quesCount BIGINT;
						DECLARE t_istest INT;
						DECLARE tmp_chanum BIGINT DEFAULT t_chanum;
						  
						DECLARE yx_ques_cur CURSOR FOR	
						SELECT * FROM (
						SELECT cq.question_id,
						(SELECT COUNT(*) FROM j_ques_team_rela qtr WHERE qtr.team_id=q.question_id) qtCount
						,(SELECT COUNT(*) FROM j_paper_question  WHERE paper_id IN 
							(SELECT paper_id FROM stu_paper_logs WHERE user_id=p_user_id) 
							AND question_id=cq.question_id LIMIT 0,1) isTest
						 FROM tp_j_course_question_info cq,question_info q
						WHERE cq.question_id=q.question_id AND q.question_type<>6
						AND (q.question_type=3 OR q.question_type=4 OR q.question_type=7 OR q.question_type=8)	
						 AND course_id=@courseid 
						AND NOT EXISTS (
							SELECT 1 FROM j_paper_question pq
							 LEFT JOIN j_ques_team_rela qtr ON qtr.team_id=pq.`question_id`
							 WHERE paper_id=z_paperid AND IFNULL(qtr.ques_id,question_id)=q.question_id				
							)
						
						) t WHERE 
						 isTest<1
						ORDER BY isTest,qtCount DESC
						LIMIT 0,100
						;
						
						
												
						
						OPEN yx_ques_cur;
						ques_loop:LOOP FETCH yx_ques_cur INTO t_quesid, t_quesCount,t_istest;
							IF cursor_done=1 OR t_chanum<=0 THEN
								LEAVE ques_loop;
							END IF;
						 
						
							INSERT INTO j_paper_question(paper_id,question_id,order_idx,c_time)
							VALUES(z_paperid,t_quesid,t_sort,NOW());
							SET t_chanum=t_chanum-1;		
								SET t_sort=t_sort+1;					
						 END LOOP ques_loop;
						CLOSE yx_ques_cur;		
						END;	
							 
				 
				 
			END IF;	 
				 
				  
		         
		         IF t_chanum=@quesnum AND @quesnum>0 THEN
				 WHILE t_chanum>0 DO
					 SET tmp_sql=CONCAT('		 
						SELECT t.question_id,qtCount,count(t.question_id) INTO @quesid,@qtCountVal,@pvCount FROM (
						SELECT cq.question_id,(SELECT COUNT(*) FROM j_ques_team_rela qtr WHERE qtr.team_id=q.`question_id`) qtCount,(
						 SELECT COUNT(*) FROM j_paper_question  WHERE paper_id IN (
							SELECT paper_id FROM `stu_paper_logs` WHERE user_id=',p_user_id,' AND paper_id<>',z_paperid,'
							) AND question_id=cq.`question_id` LIMIT 0,1
						) isTest FROM tp_j_course_question_info cq,question_info q
						WHERE cq.question_id=q.question_id AND q.question_type=6 AND course_id=',@courseid);
					IF LENGTH(t_quesid_str)>1 THEN
						SET tmp_sql=CONCAT(tmp_sql,' AND cq.question_id NOT IN (',SUBSTR(t_quesid_str,2,LENGTH(t_quesid_str)-2),')');
					END IF;					
					 SET tmp_sql=CONCAT(tmp_sql,'
						AND NOT EXISTS (
							SELECT 1 FROM j_paper_question pq
							 LEFT JOIN j_ques_team_rela qtr ON qtr.team_id=pq.`question_id`
							 WHERE paper_id=',z_paperid,' AND IFNULL(qtr.ques_id,question_id)=q.question_id				
							)
					 ');
					SET tmp_sql=CONCAT(tmp_sql,'ORDER BY RANd(),isTest) t  LIMIT 0,1	         
					 ');
					SET @tmp_sqlab=tmp_sql;
					PREPARE stmtab FROM @tmp_sqlab;
					EXECUTE stmtab;
					DEALLOCATE PREPARE stmtab;
					
						
						IF @quesid IS NOT NULL THEN		
						  SET tmp_sql=CONCAT("SELECT COUNT(*) INTO @paperqCount FROM j_paper_question WHERE paper_id=",z_paperid," AND question_id=",@quesid);
							SET @tmp_sq142=tmp_sql;
							PREPARE stmt141 FROM @tmp_sq142;
							EXECUTE stmt141;
							DEALLOCATE PREPARE stmt141;
						     
							IF @paperqCount<1 THEN
										
								SET t_chanum=t_chanum-@qtCountVal;
								INSERT INTO j_paper_question(paper_id,question_id,order_idx,c_time)
								VALUES(z_paperid,@quesid,t_sort,NOW());
								SET t_chanum=t_chanum-1;
								SET t_sort=t_sort+1;
								SET t_quesid_str=CONCAT(t_quesid_str,@quesid,",");
							END IF;
						END IF;	
					
					 SET tmp_sql=CONCAT('					
						SELECT COUNT(*) INTO @tmpQuesCount FROM tp_j_course_question_info cq,question_info q
						WHERE cq.question_id=q.question_id AND q.question_type<>6
						AND (q.question_type=3 OR q.question_type=4 OR q.question_type=7 OR q.question_type=8)							
						 AND course_id=',@courseid,'
						AND NOT EXISTS (
							SELECT 1 FROM j_paper_question pq
							 LEFT JOIN j_ques_team_rela qtr ON qtr.team_id=pq.question_id
							 WHERE paper_id=',z_paperid,' AND IFNULL(qtr.ques_id,question_id)=q.question_id				
							)
						
						
						');
						
					SET @tmp_bc=tmp_sql;
					PREPARE stmbc FROM @tmp_bc;
					EXECUTE stmbc;
					DEALLOCATE PREPARE stmbc;
					IF @tmpQuesCount>0 THEN				 
						 SET tmp_sql=CONCAT('
							INSERT INTO j_paper_question(paper_id,question_id,order_idx)
							SELECT "',z_paperid,'",t.question_id,(@rownum1:=@rownum1+1)  FROM (
							SELECT cq.question_id,(SELECT COUNT(*) FROM j_ques_team_rela qtr WHERE qtr.team_id=q.`question_id`) qtCount,(
							 SELECT COUNT(*) FROM j_paper_question  WHERE paper_id IN (
								SELECT paper_id FROM `stu_paper_logs` WHERE user_id=',p_user_id,' AND paper_id<>',z_paperid,'
								) AND question_id=cq.`question_id` LIMIT 0,1
							) isTest FROM tp_j_course_question_info cq,question_info q,(SELECT @rownum1:=1) b
							WHERE cq.question_id=q.question_id AND q.question_type<>6 
							AND (q.question_type=3 OR q.question_type=4 OR q.question_type=7 OR q.question_type=8) AND course_id=',@courseid,'
							AND NOT EXISTS (
								SELECT 1 FROM j_paper_question pq
								 LEFT JOIN j_ques_team_rela qtr ON qtr.team_id=pq.`question_id`
								 WHERE paper_id=',z_paperid,' AND IFNULL(qtr.ques_id,question_id)=q.question_id				
								)
							
							ORDER BY RANd()
							) t LIMIT 0,',t_chanum);
							
						SET @tmp_sqlabc=tmp_sql;
						PREPARE stmtabc FROM @tmp_sqlabc;
						EXECUTE stmtabc;
						DEALLOCATE PREPARE stmtabc;
						SET t_chanum=t_chanum-@tmpQuesCount;
						set t_sort=t_sort+@tmpQuesCount;
					 END IF;
					 
					IF (@quesid IS null OR @pvCount<1) AND @tmpQuesCount<1 THEN
						set t_chanum=0;
					END IF;
					 
				END WHILE;				 
			END IF;				 
		         COMMIT;
			    
		END IF;
	END IF;
	
END $$

DELIMITER ;
