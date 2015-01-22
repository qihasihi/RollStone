DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_tp_total_score`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `cal_tp_total_score`(IN in_dc_school_id INT, IN in_class_id BIGINT, IN in_course_id BIGINT, IN in_subject_id INT ,OUT out_flag INT)
BEGIN
        DECLARE  v_num1 INT; -- ���ڳ�Աȫ���������޳ٵ����� ��1 �� 0��
	DECLARE	v_num2 INT; -- ����Ц������
	DECLARE	v_num3 INT; -- ���������
	DECLARE	v_num4 INT; -- ����Υ�����ɴ���
  DECLARE	v_num5 FLOAT; -- С�����������
	DECLARE	v_group_id BIGINT;  -- С��id
  DECLARE	v_user_id BIGINT;  -- ѧԱid
	
	DECLARE	v_taskcount INT;  -- С�鷢��������*С������
  DECLARE	v_real_taskcount INT;-- С��ʵ�����������
	DECLARE	v_group_total INT; -- С������
	DECLARE	v_g_item_total INT;-- ͳ���������
	DECLARE  v_subject_id INT;-- С��ѧ��
	DECLARE	v_sql VARCHAR(2000) DEFAULT '';
	DECLARE	v_count_1 INT;-- ������
	DECLARE	v_group_score_num INT DEFAULT 0;-- С��÷ּ�¼����	
	  -- ��Ҫ��������α����ݵı��� 
	  -- �������ݽ�����־
	  DECLARE done INT DEFAULT 0;
	  -- �α� --  �ҵ��˿γ̣��˰༶�༶��Ӧ��N��С��
	 DECLARE  curA CURSOR FOR	    SELECT group_id ,subject_id FROM tp_group_info  WHERE class_id=in_class_id AND subject_id=in_subject_id;
    -- �α�
 -- 3 A ���ڳ�Աȫ���������޳ٵ�����
   DECLARE  cur3A CURSOR FOR	  SELECT a.*  FROM (
    SELECT SCORE1_FLAG ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY SCORE1_FLAG)a ORDER BY a.score1_flag DESC   ;
    -- 3 B ����Ц��������ȫ���һ
    DECLARE cur3B CURSOR FOR	SELECT a.* FROM (
    SELECT score2_total ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY score2_total) a ORDER BY a.score2_total DESC ;
  
    -- 3 C ����С����������ȫ���һ
      
    DECLARE cur3C CURSOR FOR SELECT a.* FROM (
    SELECT award_number ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY award_number) a  ORDER BY award_number DESC ;
    -- 3 D ����Υ�����ɴ�����ȫ���һ
        -- �ͷ��� --��ֵ  ORDER BY  ���� desc
     DECLARE cur3D CURSOR FOR SELECT a.* FROM (
    SELECT score4_total ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY score4_total)a ORDER BY score4_total  ;
   
   
   
  -- 4 �����Ϲ��˿γ̵�����ѧԱ
 DECLARE curB CURSOR FOR SELECT group_id,user_id FROM tp_stu_score WHERE course_id=in_course_id;
	  -- ��������־�󶨵��α�
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	  
   SET out_flag=0;         
  -- ��ѯС������
    SELECT COUNT(*) INTO v_group_total FROM tp_group_info WHERE class_id=in_class_id AND subject_id=in_subject_id;
	
	 
	  
	  -- ���α�
	  OPEN curA;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH curA INTO v_group_id,v_subject_id;
		    -- ����������ʱ��
		    IF done=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		       -- 1 ��ѯС���Ƿ���С��÷ּ�¼��û�����һ����
		      SELECT COUNT(*) INTO v_group_score_num FROM tp_group_score WHERE course_id=in_course_id AND group_id=v_group_id ;
		      IF v_group_score_num=0 THEN 
			 INSERT INTO tp_group_score (class_id,group_id,course_id,dc_school_id,subject_id) VALUES 
			 (in_class_id,v_group_id,in_course_id,in_dc_school_id,v_subject_id	);
		      END IF;
		    
	  END LOOP;
	  -- �ر��α�
	  CLOSE curA;
	  -- ----------------------------------------------------------------------------------------------------------
	  
	  -- ���α�
     SET done=0;
	  OPEN curA;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH curA INTO v_group_id,v_subject_id;
        
        -- ����������ʱ��
		    IF done=1 THEN
		      LEAVE read_loop;
		    END IF;
		    -- 2 ���㵥��÷� ���޸�С��÷ֱ�Ķ�Ӧ��ͳ�Ƽ�¼
		    -- 2 A ���ڳ�Աȫ���������޳ٵ�����
      SELECT ((SELECT COUNT(*) FROM tp_stu_score WHERE class_id=in_class_id AND group_id=v_group_id
      AND course_id=in_course_id AND attendance_num=2)-
      (SELECT COUNT(*) FROM tp_stu_score WHERE class_id=in_class_id AND course_id=in_course_id  AND group_id=v_group_id))
      INTO v_num1 FROM DUAL;
      IF v_num1=0 THEN
         --  0�����ڳ�Աȫ���������޳ٵ�����
         UPDATE tp_group_score SET SCORE1_FLAG=1 WHERE
         group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
      END IF;
  -- 2 B ����Ц������
         SELECT IFNULL (SUM(smiling_num),0) INTO v_num2 FROM  tp_stu_score
         WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ;
         UPDATE tp_group_score tgs SET SCORE2_TOTAL =v_num2  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
  -- 2 C ����С�������� --��ʦ��С���鳤��ӣ��������� award_number
      -- 2 D ����Υ�����ɴ���
         SELECT IFNULL(SUM(violation_dis_num),0) INTO v_num4 FROM  tp_stu_score
         WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ;
         UPDATE tp_group_score SET SCORE4_TOTAL =v_num4  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
     -- 2 E ��������������������
    -- ������            (����ֻ�ָܷ��༶?)
      SELECT (SELECT  COUNT(*) FROM tp_task_info t,tp_task_allot_info ttai
      WHERE t.TASK_ID=ttai.task_id
      AND (
	(
		ttai.user_type=0 AND ttai.user_type_id=in_class_id
	) OR(
		ttai.user_type=2 AND ttai.user_type_id=v_group_id
	)
      )
      AND t.COURSE_ID=in_course_id
      AND ttai.e_time<NOW())
      *
      (SELECT COUNT(*) FROM tp_j_group_student tjgs WHERE tjgs.GROUP_ID=v_group_id)
       INTO v_taskcount  FROM   DUAL   ;
       
       
       
       
                
      IF v_taskcount=0 THEN
               
           UPDATE tp_group_score SET SCORE5_AVG =0  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
        ELSE  
           SELECT  COUNT(DISTINCT(ttp.ref))  INTO v_real_taskcount FROM tp_task_performance ttp,tp_task_info tti,
            tp_task_allot_info ttai,tp_j_group_student tjgs,user_info ui WHERE tjgs.GROUP_ID=v_group_id
            AND ui.USER_ID=tjgs.user_id
            AND ui.ref=ttp.USER_ID
            AND ttai.task_id=tti.TASK_ID
            AND ttp.TASK_ID=tti.TASK_ID
            AND tti.TASK_ID IN (
            SELECT  t.TASK_ID FROM tp_task_info t,tp_task_allot_info ttai
            WHERE t.TASK_ID=ttai.task_id
            AND ((ttai.user_type=0 
            AND ttai.user_type_id=in_class_id
		    ) OR (
			ttai.user_type=2 AND ttai.user_type_id=v_group_id
		    )
            )
            
            AND t.COURSE_ID=in_course_id
            AND ttai.e_time<NOW()) ;
            UPDATE tp_group_score SET SCORE5_AVG =v_real_taskcount/v_taskcount  WHERE  group_id=v_group_id AND course_id=in_course_id AND class_id=in_class_id;
        END IF;
      
	  END LOOP;
	  -- �ر��α�
    CLOSE curA;
-- ----------------------------------------------------------------------------
   -- 3 ���㵥��÷� ���޸�С��÷ֱ�Ķ�Ӧ�������÷�
    -- 3 A ���ڳ�Աȫ���������޳ٵ�����
    SET v_num1=0;
    SET v_g_item_total=0;
    SET done=0;
 -- ���α�
   SET  v_count_1=0;
	  OPEN cur3A;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH cur3A INTO v_num1,v_g_item_total;
		    -- ����������ʱ��
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- �������С��ķ���һ�£��򲻸���
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 THEN
                 -- �޸�����������һ�ĸ���������
                  UPDATE tp_group_score   SET SCORE1=1 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND SCORE1_FLAG=v_num1 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- �ر��α�
	  CLOSE cur3A;
 -- 3 B ����Ц��������ȫ���һ
       SET v_num2:=0;
       SET v_g_item_total:=0;
       SET done=0;
       SET v_count_1:=0;
    
          OPEN cur3B;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH cur3B INTO v_num2,v_g_item_total;
		    -- ����������ʱ��
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- �������С��ķ���һ�£��򲻸���
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 THEN
                 -- �޸�����������һ�ĸ���������
                  UPDATE tp_group_score   SET SCORE2=3 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND SCORE2_TOTAL=v_num2 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- �ر��α�
	  CLOSE cur3B;
   -- 3 C ����С����������ȫ���һ
   SET v_num3:=0;
   SET   v_g_item_total:=0;
   SET done=0;
   SET v_count_1:=0;
        OPEN cur3C;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH cur3C INTO v_num3,v_g_item_total;
		    -- ����������ʱ��
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- �������С��ķ���һ�£��򲻸���
               LEAVE read_loop;
           ELSE
              IF v_count_1=0  THEN
                   -- �޸�����������һ�ĸ���������
                  UPDATE tp_group_score   SET SCORE3=3 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND award_number=v_num3 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- �ر��α�
	  CLOSE cur3C;
    
   -- 3 D ����Υ�����ɴ�����ȫ���һ
        -- �ͷ���
   SET v_num4:=0;
   SET  v_g_item_total:=0;
   SET  v_count_1:=0;
   SET done=0;
      OPEN cur3D;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH cur3D INTO v_num4,v_g_item_total;
		    -- ����������ʱ��
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- �������С��ķ���һ�£��򲻸���
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 AND v_g_item_total=1 THEN
                   -- �޸�������һ��һ��С����гͷ�
                  UPDATE tp_group_score   SET SCORE4=-1 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND score4_total=v_num4 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- �ر��α�
	  CLOSE cur3D;
     
   -- 3 E ����������������������ȫ���һ
   BEGIN
       -- 3 E ���������������ƽ������ȫ���һ
    DECLARE cur3E CURSOR FOR  SELECT a.* FROM (
    SELECT SCORE5_AVG ,COUNT(*) num  FROM tp_group_score t
    WHERE t.class_id=in_class_id AND t.course_id=in_course_id
     GROUP BY SCORE5_AVG)a ORDER BY SCORE5_AVG DESC ;
   
 SET v_num5:=0;
   SET  v_g_item_total:=0;
   SET  v_count_1:=0;
   SET done=0;
      OPEN cur3E;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH cur3E INTO v_num5,v_g_item_total;
		    -- ����������ʱ��
		    IF done=1 OR v_count_1=1 THEN
		      LEAVE read_loop;
		    END IF;
		    
		        IF v_g_item_total=v_group_total THEN -- �������С��ķ���һ�£��򲻸���
               LEAVE read_loop;
           ELSE
              IF v_count_1=0 THEN
                   
                   -- �޸�����������һ�ĸ���������
                  UPDATE tp_group_score   SET SCORE5=3 WHERE course_id=in_course_id
                  AND class_id=in_class_id AND SCORE5_AVG=v_num5 ;
              END IF;
           END IF;
           SET v_count_1=1;
		    
	  END LOOP;
	  -- �ر��α�
	  CLOSE cur3E;
	END;  
          -- -------------------------------------------------------------------------------------------------------
  -- 4 �����Ϲ��˿γ̵�����ѧԱ������ѧԱ��С��÷֣��γ��ܷ�=������+����+С�飩
     SET done=0;
     OPEN curB;
	  -- ��ʼѭ��
	  read_loop: LOOP
		    -- ��ȡ�α��������
		    FETCH curB INTO v_group_id,v_user_id;
		    -- ����������ʱ��
		    IF done=1  THEN
		      LEAVE read_loop;
		    END IF;
		     -- �޸�ѧԱ�γ̷����� С��÷�
       UPDATE tp_stu_score SET GROUP_SCORE=(
       SELECT SUM(IFNULL(SCORE1,0)+IFNULL(SCORE2,0)+IFNULL(SCORE3,0)+IFNULL(SCORE4,0)+IFNULL(SCORE5,0))  FROM tp_group_score WHERE
       class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score1=(SELECT IFNULL(SCORE1,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score2=(SELECT IFNULL(SCORE2,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score3=(SELECT IFNULL(SCORE3,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score4=(SELECT IFNULL(SCORE4,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id ),
       score5=(SELECT IFNULL(SCORE5,0)  FROM tp_group_score WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id )
       WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id  AND user_id=v_user_id;
       -- �޸��ܵ÷� ���������ϵ÷֣�������������������ۣ����ý��飩
       UPDATE tp_stu_score SET COURSE_TOTAL_SCORE=(IFNULL(GROUP_SCORE,0)+IFNULL(attendance_num,0)+
        IFNULL(smiling_num,0)+IFNULL(violation_dis_num,0)+IFNULL(task_score,0)+IFNULL(comment_score,0)
       )WHERE class_id=in_class_id AND group_id=v_group_id AND course_id=in_course_id  AND user_id=v_user_id;
		        
		    
	  END LOOP;
	  -- �ر��α�
	  CLOSE curB;
  -- -------------------------------------------------------------------------------------------------------
    	SET out_flag=1;  
END $$

DELIMITER ;
