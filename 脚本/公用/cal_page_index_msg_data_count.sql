DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_page_index_msg_data_count`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `cal_page_index_msg_data_count`(
				        p_user_ref VARCHAR(100)
				          )
BEGIN
 /**
         * ��Դ���ͨ��
         *   1:����(ggtd)
         *2:����(sqtd)
         *3:���(shtd)
         *4:����(bmtd)
         *5:¼ȡ(lqtd)
         *6:����(fttd)
         *7:����(rmtd)
         *8:����(rwtd)
         *9:�ɼ�(cjtd)
         *10:�(hdtd)
         *11:�û��޸�(yhxgtd)
         *12:����(tbtd)
         *13:У������(xftd)
         *14:֪ͨ(tztd)
         */
	-- ��ѯ��ҳ��̬���	  
      SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,1) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=1 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,2) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=2 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,3) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=3 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,4) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=4 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,5) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=5 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,6) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=6 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,7) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=7 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,8) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=8 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,9) msg_id  FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=9 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,10) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=10 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,11) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=11 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,12) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=12 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,13) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=13 	AND u.USER_REF=p_user_ref    
	UNION ALL
	SELECT COUNT(u.ref) msgCount,IFNULL(`msg_id`,14) msg_id FROM j_myinfo_user_info  u
      WHERE c_time>DATE_ADD(C_TIME,INTERVAL -2 DAY) AND c_time<=NOW()	
	AND u.MSG_ID=14  AND u.USER_REF=p_user_ref;
      
END $$

DELIMITER ;
