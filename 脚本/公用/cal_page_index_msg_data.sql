DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_page_index_msg_data`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `cal_page_index_msg_data`(
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
	SELECT * FROM (
	SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=1
		 AND u.USER_REF=p_user_ref         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID
		ORDER BY mu.C_TIME DESC  LIMIT 0,5
		) t
         UNION ALL
    SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=2
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t         
           UNION ALL
	SELECT * FROM (SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=3
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t         
           UNION ALL
           SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=4
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5 ) t       
           UNION ALL
            SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=5
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5 ) t        
           UNION ALL
            SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=6
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID   ORDER BY mu.C_TIME DESC LIMIT 0,5   ) t    
           UNION ALL
            SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=7
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t
           UNION ALL
            SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=8
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t
           UNION ALL
            SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=9
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t
           UNION ALL
            SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=10
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t
           UNION ALL
            SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=11
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t
            UNION ALL
             SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=12
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t
            UNION ALL
             SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=13
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t
            UNION ALL
             SELECT * FROM (
         SELECT 
         mu.*,t.template_name,t.template_searator,t.template_content,t.template_url
         FROM (SELECT u.* FROM j_myinfo_user_info u WHERE 1=1 
		 AND u.MSG_ID=14
		 AND u.USER_REF=p_user_ref 
         
         ) mu,myinfo_template_info t 
         WHERE t.template_id=mu.TEMPLATE_ID ORDER BY mu.C_TIME DESC LIMIT 0,5) t;         	
END $$

DELIMITER ;
