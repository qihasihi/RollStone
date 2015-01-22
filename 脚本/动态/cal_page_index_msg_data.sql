DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `cal_page_index_msg_data`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `cal_page_index_msg_data`(
					p_user_ref VARCHAR(100)
				          )
BEGIN
  /**
         * 资源审核通过
         *   1:公告(ggtd)
         *2:申请(sqtd)
         *3:审核(shtd)
         *4:报名(bmtd)
         *5:录取(lqtd)
         *6:发帖(fttd)
         *7:任命(rmtd)
         *8:任务(rwtd)
         *9:成绩(cjtd)
         *10:活动(hdtd)
         *11:用户修改(yhxgtd)
         *12:调班(tbtd)
         *13:校风提醒(xftd)
         *14:通知(tztd)
         */
	-- 查询首页动态情况
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
    END$$

DELIMITER ;